package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//NotNull = Null 값 체크
//NotEmpty = 빈값이거나 null 체크
//NotBlank = 빈값이거나 null 체크 그리고 빈 공백 (스페이스)까지

@Data
public class CommentDTO {

    @NotBlank
    private String content;

    @NotNull
    private Integer imageId;

}
