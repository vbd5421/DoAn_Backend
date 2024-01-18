package com.doan.backend.dto.Comment;

import com.doan.backend.model.Comment;
import com.doan.backend.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentParentDTO {
    private Long id;
    private Users user;
    private String content;
    private String date;
    private Long parentId;
    private Boolean status;
    private List<Comment> childComment;
    private Long projectId;
}
