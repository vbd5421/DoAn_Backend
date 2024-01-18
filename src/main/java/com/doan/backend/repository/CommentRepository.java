package com.doan.backend.repository;

import com.doan.backend.dto.Comment.CommentDTO;
import com.doan.backend.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM comment c WHERE c.status = true " +
            "AND c.parent_id IS NULL " +
            "ORDER BY c.comment_id DESC", nativeQuery = true)
    List<Comment> getParentCommentActive();

    @Query(value = "SELECT * FROM comment c WHERE c.parent_id IS NULL " +
            "ORDER BY c.comment_id DESC", nativeQuery = true)
    List<Comment> getParentComment();

    @Query(value = "SELECT * FROM comment c WHERE c.status = true " +
            "AND c.parent_id = ?1 " +
            "ORDER BY c.comment_id DESC", nativeQuery = true)
    List<Comment> getCommentChildById(Long id);

//    @Query(value = "SELECT * FROM comment c WHERE c.parent_id IS NOT NULL  " +
//            "ORDER BY c.comment_id ASC", nativeQuery = true)
//    @Query("select new com.doan.backend.dto.Comment.CommentDTO(c.id,u.username,c.content,c.date,c.parentId,c.status) " +
//            "from Comment c inner join User u on c.user.id = u.id " +
//            "where c.status = true and c.parentId is not null " +
//            "order by c.id ASC ")
//    List<Comment> getCommentChild();

    //    @Query(value = "SELECT * FROM comment c WHERE c.status = true " +
//            "AND c.parent_id IS NOT NULL  " +
//            "ORDER BY c.comment_id ASC", nativeQuery = true)
    @Query("select new com.doan.backend.dto.Comment.CommentDTO(c.id,u.userName,c.content,c.date,c.parentId,c.status)" +
            "from Comment c inner join Users u on c.user.id = u.id " +
            "where c.status = true and c.parentId is not null " +
            "order by c.id ASC ")
    List<CommentDTO> getCommentChildByParent();

//    @Query(value = "SELECT * FROM comment c WHERE c.status = true " +
//            "AND c.comment_id = ?1 " +
//            "ORDER BY c.comment_id ASC", nativeQuery = true)
//    List<Comment> getCommentById(Long id);

    //    @Query(value = "SELECT * FROM comment c WHERE c.status = true " +
//            "AND c.post_id = ?1 AND c.parent_id IS NULL ORDER BY c.comment_id ASC", nativeQuery = true)
    @Query("select new com.doan.backend.dto.Comment.CommentDTO(c.id,u.userName,c.content,c.date,c.parentId,c.status)" +
            "from Comment c inner join Users u on c.user.id = u.id " +
            "where c.status = true and c.project.id = :projectId and c.parentId is null " +
            "order by c.id ASC ")
    List<CommentDTO> getParentCommentByProjectActive(@Param("projectId") Long projectId);

    List<Comment> findAllByStatusIsNull();

    //    @Query("SELECT c FROM Comment c " +
//            "WHERE c.parentId IS NULL AND ((:si is null) OR " +
//            "( LOWER(c.content) LIKE LOWER(concat('%',:si,'%') ) OR " +
//            "LOWER(c.user.username) LIKE LOWER(concat('%',:si,'%') ) OR " +
//            "LOWER(c.post.title) LIKE LOWER(concat('%',:si,'%') )))")
    @Query("SELECT new com.doan.backend.dto.Comment.CommentDTO(c.id,c.user.userName,c.content,c.date,c.parentId,c.status,c.project) " +
            "FROM Comment c " +
            "WHERE c.parentId IS NULL AND ((:si is null) OR " +
            "( LOWER(c.content) LIKE LOWER(concat('%',:si,'%') ) OR " +
            "LOWER(c.user.userName) LIKE LOWER(concat('%',:si,'%') ) OR " +
            "LOWER(c.project.name) LIKE LOWER(concat('%',:si,'%') )))")
    Page<Comment> searchComment(Pageable pageable, @Param("si")String searchInput);

}
