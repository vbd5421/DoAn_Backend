package com.doan.backend.controller;

import com.doan.backend.dto.Comment.CommentDTO;
import com.doan.backend.model.Comment;
import com.doan.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")

public class CommentController {
    @Autowired
    private CommentService commentService;

    //* Worked

    /***
     * Create a new comment
     * @param commentDTO payload
     * @return new comment
     */
    @PostMapping("/create")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    //* Worked
    /***
     * Update content of a comment
     * @param commentDTO payload
     * @return comment
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(id,commentDTO));
    }

    //? Can perform with updateComment method
    //* Worked
    /***
     * Disable an existed comment
     * @param id comment id
     * @return comment
     */
    @GetMapping("/disable/{id}")
    public ResponseEntity<?> disableComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.disableComment(id));
    }

    @GetMapping("/enable/{id}")
    public ResponseEntity<?> enableComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.enableComment(id));
    }

    //* Worked
    /***
     * Delete an existed comment
     * @param id comment id
     * @return comment
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }

    //? can display, but inactive child comment still display
    /***
     * Get list of all comments with active status
     * @return list comment
     */
    @GetMapping("/home")
    public ResponseEntity<?> getAllCommentWithChild() {
        return ResponseEntity.ok(commentService.getAllCommentWithChild());
    }

    //? can display, but inactive child comment still display
    /***
     * Get a comment by id and its child's comment
     * @return comment list
     */
    @GetMapping("/get")
    public ResponseEntity<?> getCommentChildById() {
        return ResponseEntity.ok(commentService.getCommentChildByParent());
    }

    //* Worked
    /***
     * Display list of comment regardless status
     * @return list comment
     */
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComment() {
        return ResponseEntity.ok().body(commentService.getAllComment());
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingComment() {
        return ResponseEntity.ok(commentService.getPendingComment());
    }

    @GetMapping("/test")
    public List<CommentDTO> getCommentByProjectId(@RequestParam Long projectId){
        return commentService.getCommentByProjectId(projectId);
    }

    @GetMapping("/parent")
    public List<Comment> getParentCommentActive(){
        return commentService.getParentCommentActive();
    }


    @GetMapping("/child")
    public ResponseEntity<?> getCommentChild() {
        return ResponseEntity.ok(commentService.getCommentChild());
    }
    @GetMapping("/show")
    public List<Comment> getParentComment(){
        return commentService.getParentComment();

    }

    @GetMapping("/search")
    public Page<Comment> searchComment(
            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "5")int size,
            @RequestParam(name = "search",required = false)String searchInput)
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return commentService.searchComment(pageable,searchInput);
    }
}