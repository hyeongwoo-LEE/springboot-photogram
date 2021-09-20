package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDTO;
import com.cos.photogramstart.web.dto.comment.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDTO commentDTO, BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails){

        Comment comment = commentService.댓글쓰기(commentDTO.getContent(), commentDTO.getImageId(), principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDTO<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){

        commentService.댓글삭제(id);

        return new ResponseEntity<>(new CMRespDTO<>(1, "댓글삭제성공", null), HttpStatus.OK);

    }
}
