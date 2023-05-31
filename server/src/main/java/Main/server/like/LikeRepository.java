package Main.server.like;

import Main.server.board_infj.BoardInfj;
import Main.server.board_integrated.BoardIntegrated;
import Main.server.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 통합 게시판에서 게시글 찾기
    Optional<Like> findByUsersAndBoardIntegrated(Users users, BoardIntegrated post);

    // INFJ 게시판에서 게시글 찾기
    Optional<Like> findByUsersAndBoardInfj(Users users, BoardInfj post);
}

