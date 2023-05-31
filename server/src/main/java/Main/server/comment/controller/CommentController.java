//package Main.server.comment.controller;
//
//import Main.server.comment.dto.CommentPatchDto;
//import Main.server.comment.dto.CommentPostDto;
//import Main.server.comment.dto.CommentResponseDto;
//import Main.server.comment.entity.Comment;
//import Main.server.comment.mapper.CommentMapper;
//import Main.server.comment.service.CommentService;
//import Main.server.notification.service.SseService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/board/integrated")
//public class CommentController {
//    private final CommentService commentService;
//    private final CommentMapper commentMapper;
//    private final SseService sseService;
//
//    public CommentController(CommentService commentService, CommentMapper commentMapper, SseService sseService) {
//        this.commentService = commentService;
//        this.commentMapper = commentMapper;
//        this.sseService = sseService;
//    }
//
//    @PostMapping("/{post-id}")
//    public ResponseEntity postComment(@PathVariable("post-id") Long boardId,
//                                      @RequestBody CommentPostDto postDto) {
//
//        Comment createComment = commentService.createComment(commentMapper.commentPostDtoToComment(postDto), postDto.getUserId(), boardId);
//        CommentResponseDto result = commentMapper.commentToCommentResponseDto(createComment);
//        result.setUserId(createComment.getUser().getUserId());
//        result.setUsername(createComment.getUser().getNickName());
//        result.setBoardId(createComment.getBoard().getId());
//
//        sseService.AddCommentEvent(boardId);
//
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{post-id}/comment/{comment-id}")
//    public ResponseEntity getComment(@PathVariable("post-id") long boardId,
//                                     @PathVariable("comment-id") long commentId) {
//
//        Comment comment = commentService.findVerifiedComment(commentId);
//        CommentResponseDto result = commentMapper.commentToCommentResponseDto(comment);
//        result.setUserId(comment.getUser().getUserId());
//        result.setUsername(comment.getUser().getNickName());
//        result.setBoardId(comment.getBoard().getId());
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/{post-id}/comment")
//    public ResponseEntity getComments() {
//        List<Comment> comments = commentService.findComments();
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }
//
//    @PatchMapping("/{post-id}/comment/{comment-id}")
//    public ResponseEntity patchComment(@PathVariable("post-id") Long boardId,
//                                       @PathVariable("comment-id") Long commentId,
//                                       @RequestBody CommentPatchDto patchDto) {
//        Comment comment = commentMapper.commentPatchDtoToComment(patchDto);
//        comment.setCommentId(commentId);
//
//        Comment patchComment = commentService.updateComment(comment);
//        CommentResponseDto result = commentMapper.commentToCommentResponseDto(patchComment);
//        result.setUserId(patchComment.getUser().getUserId());
//        result.setUsername(patchComment.getUser().getNickName());
//        result.setBoardId(boardId);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{post-id}/comment/{comment-id}")
//    public ResponseEntity deleteComment(@PathVariable("post-id") long boardId,
//                                        @PathVariable("comment-id") long commentId) {
//
//        commentService.deleteComment(commentId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
