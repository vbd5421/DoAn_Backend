package com.doan.backend.service;

import com.doan.backend.dto.Comment.CommentDTO;
import com.doan.backend.dto.Comment.CommentParentDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Comment;
import com.doan.backend.model.Project;
import com.doan.backend.model.Users;
import com.doan.backend.repository.CommentRepository;
import com.doan.backend.repository.ProjectRepository;
import com.doan.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    /***
     * Create a new comment
     * @param commentDTO payload
     * @return comment
     */
    public Comment createComment(CommentDTO commentDTO) {
        // get user id from DTO
        Optional<Users> userOpt = userRepository.findByUserName(commentDTO.getUserName());
        if (userOpt.isEmpty()) { // check if user existed
            throw new ResourceException("Không tìm thấy người dùng theo id " + commentDTO.getUserName());
        }
        Users user = userOpt.get();

        // get comment's content from DTO
        LocalDateTime createDate = LocalDateTime.now();
        // get parent id
        Long parentId = commentDTO.getParentId();
        if (parentId != null && !commentRepository.existsById(parentId)) { //check if comment is not existed
            throw new ResourceException("Không tìm thấy bình luận");
        }
        // get project for the comment from DTO
        Optional<Project> projectOpt = projectRepository.findById(commentDTO.getProject().getId());
        if(projectOpt.isEmpty()) {
            throw new ResourceException("Không tìm thấy bài viết");
        }
        Project project = projectOpt.get();
        // set comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());
        comment.setDate(createDate);
        comment.setParentId(parentId);
        comment.setProject(project);
        comment.setStatus(true);
        // save comment
        commentRepository.save(comment);
        return comment;
    }

    /***
     * Update content of a comment
     * @param commentDTO payload
     * @return comment
     */
    public Comment updateComment(Long id,CommentDTO commentDTO) {
        // find comment by id
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) { // if comment not existed
            throw new ResourceException("Không tìm thấy bình luận");
        }
        // get user id from DTO
//        Long curUserId = commentDTO.getUser().getId(); // get current user's id
        String curUserName = commentDTO.getUserName();
//        Long commentUserName = commentOpt.get().getUser().getId(); //get comment's user id
        String commentUserName = commentOpt.get().getUser().getUserName();
        if(!curUserName.equals(commentUserName)) {
            throw new ResourceException("Không thể sửa bình luận của người khác");
        }
        // get comment's content from DTO
        String content = commentDTO.getContent().trim();
        // get create date as current date time
        LocalDateTime createDate = LocalDateTime.now();
        // get status from DTO
        Boolean status = commentDTO.getStatus();
        if(status == null) { // check if status is null
            throw new ResourceException("Trạng thái không được để trống");
        }
        // set comment
        Comment comment = commentOpt.get();
        comment.setContent(content);
        comment.setDate(createDate);
        comment.setStatus(true);
        // save comment
        commentRepository.save(comment);
        return comment;
    }

    /***
     * Disable an existed comment for ADMIN
     * @param id comment id
     * @return comment
     */
    public Comment disableComment(Long id) {
        // find comment by id
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) { // if comment not exist
            throw new ResourceException("Không tìm thấy bình luận id " + id);
        } else {
            Comment comment = commentOpt.get();
            comment.setStatus(false);
            commentRepository.save(comment);
            return comment;
        }
    }

    public Comment enableComment(Long id) {
        // find comment by id
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) { // if comment not exist
            throw new ResourceException("Không tìm thấy bình luận id " + id);
        } else {
            Comment comment = commentOpt.get();
            comment.setStatus(true);
            commentRepository.save(comment);
            return comment;
        }
    }


    /***
     * Delete an existed comment for ADMIN
     * @param id comment id
     * @return comment
     */
    public ResponseEntity<?> deleteComment(Long id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if(!commentOpt.isPresent()) {
            throw new ResourceException("Không tìm thấy bình luận");
        } else {
            Comment comment = commentOpt.get();
            // delete comment from DB
//            comment.setStatus(false);
            commentRepository.delete(comment);
            return new ResponseEntity<>("Xóa bình luận thành công", HttpStatus.OK);
        }
    }

    //? can display, but inactive child comment still display

    /***
     * Get list of all comments for ADMIN
     * @return list comment
     */
    public List<?> getAllCommentWithChild() {
        // create new list
        List<CommentParentDTO> displayList = new ArrayList<>();
        // get list of comment with no parent id
        List<Comment> commentList = commentRepository.getParentCommentActive();
        for (Comment comment : commentList) {
            String date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(comment.getDate());
            // get list of comment with parent id
            List<Comment> childComments = commentRepository.getCommentChildById(comment.getId());
            displayList.add(new CommentParentDTO(
                    comment.getId(),
                    comment.getUser(),
                    comment.getContent(),
                    date,
                    comment.getParentId(),
                    comment.getStatus(),
                    childComments,
                    comment.getProject().getId()
            ));
        }
        return displayList;
    }


    public List<?> getCommentChild(){
        return commentRepository.getCommentChildByParent
                ();
    }
    /***
     * Get a comment by id and its child's comment for ADMIN
     * @return comment list
     */
    public List<?> getCommentChildByParent() {
//        // get comment by id
//
//        // get list of child comment by comment id
//        List<CommentDTO> commentList = commentRepository.getCommentChildByParent();
        return commentRepository.getCommentChildByParent();
    }

    /***
     * Get a project by id
     * @param id project id
     * @return an existed Project
     */
    public List<CommentDTO> getCommentByProjectId(Long id) {
        // get list of comment with project id
        // project id should be checked before method is called
        return commentRepository.getParentCommentByProjectActive(id);
    }

    /***
     * Display list of comment regardless status
     * @return list comment
     */
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

//    public List<?> getParentCommentByProject(){
//        List<Comment> listComment = commentRepository
//        return listComment;
//    }

    /***
     * Display list of comment waiting for approve
     * @return list comment
     */
    public List<?> getPendingComment() {
//        List<Comment> list = commentRepository.findAllByStatusIsNull();
        return commentRepository.findAllByStatusIsNull();
    }

    public List<Comment> getParentCommentActive(){
        return commentRepository.getParentCommentActive();
    }
    public List<Comment> getParentComment(){
        return commentRepository.getParentComment();
    }

    public Page<Comment> searchComment(Pageable pageable, String searchInput) {
//    Page<Comment> comments = commentRepository.searchComment(pageable, searchInput);
        return commentRepository.searchComment(pageable, searchInput);
    }
}
