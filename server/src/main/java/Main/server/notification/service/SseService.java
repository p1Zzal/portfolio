package Main.server.notification.service;

import Main.server.advice.BusinessLogicalException;
import Main.server.advice.ExceptionCode;
import Main.server.board_integrated.BoardIntegrated;
import Main.server.board_integrated.BoardIntegratedRepository;
import Main.server.user.entity.Users;
import Main.server.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    private final BoardIntegratedRepository boardRepository;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private static final Long DEFAULT_TIMEOUT = 60 * 1000L;

    public SseService(BoardIntegratedRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public SseEmitter connectSse(long userId){

        Optional<Users> optionalUsers =
                userRepository.findById(userId);
        Users findUsers = optionalUsers.orElseThrow(() ->
                new BusinessLogicalException(ExceptionCode.MEMBER_NOT_FOUND));

        Long findUserId = findUsers.getUserId();

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        try{
            emitter.send(SseEmitter.event()
                    .name("Connect")
                    .data("Connect Success!")); // 503 에러 방지
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        sseEmitters.put(findUserId, emitter);

        log.info("에미터 : {}",sseEmitters);

        emitter.onCompletion(() -> {
            sseEmitters.remove(findUserId);
        });
        emitter.onTimeout(() -> {
            sseEmitters.remove(findUserId);
        });

        return emitter;
    }

    public void AddCommentEvent(Long boardId){
        Optional<BoardIntegrated> board = boardRepository.findById(boardId);

        System.out.println(board);

        Long notiUserId = board.get().getUsers().getUserId();

        System.out.println(notiUserId);

        if(sseEmitters.containsKey(notiUserId)){
            SseEmitter sseEmitter = sseEmitters.get(notiUserId);
            log.info("aa : {}", sseEmitter);
            try{
                log.info("정상적으로 감");
                sseEmitter.send(SseEmitter.event()
                        .name("addComment")
                        .data("댓글이 달렸습니다."));
            } catch (Exception e){
                log.info("뭔가 이상함");
                sseEmitters.remove(notiUserId);
            }
        }
    }


//    private enum Category{
//        Integrated,
//        INFJ,
//        INTP,
//
//    }
}