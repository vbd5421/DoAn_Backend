package com.doan.backend.dto.Comment;

import com.doan.backend.model.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    public CommentDTO(Long id, String userName, String content, LocalDateTime date, Long parentId, Boolean status) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.date = date;
        this.parentId = parentId;
        this.status = status;
    }

    private Long id;
    private String userName;
    @NotNull(message = "Hãy nhập nội dung bình luận")
    @NotBlank(message = "Hãy nhập nội dung bình luận")
    @NotEmpty(message = "Vui lòng viết bình luận dưới 8000 kí tự")
    @Size(max = 8000, message = "Bình luận quá dài, vui lòng viết bình luận dưới 8000 kí tự")
    private String content;
    //    private String date;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
    private Long parentId;
    private Boolean status;
    private Project project;
}
