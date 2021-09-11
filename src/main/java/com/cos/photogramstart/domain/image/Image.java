package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user"})
@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; //오늘 나 너무 피곤해!

    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 저장된 경로를 insert

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요

    // 댓글

    private LocalDateTime createDate;


    @PrePersist //디비에 insert 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }



}
