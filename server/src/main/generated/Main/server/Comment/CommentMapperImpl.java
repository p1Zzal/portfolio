package Main.server.comment;

import Main.server.comment.CommentDto.Patch;
import Main.server.comment.CommentDto.Post;
import Main.server.comment.CommentDto.Response;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-29T11:17:27+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(Post postDto) {
        if ( postDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( postDto.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(Patch patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( patchDto.getId() );
        comment.setContent( patchDto.getContent() );

        return comment;
    }

    @Override
    public Response commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        Long id = null;
        String content = null;
        String category = null;

        id = comment.getId();
        content = comment.getContent();
        category = comment.getCategory();

        Long userId = null;
        Long boardId = null;
        String username = null;

        Response response = new Response( id, userId, boardId, content, username, category );

        return response;
    }
}
