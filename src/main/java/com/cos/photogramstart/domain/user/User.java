package com.cos.photogramstart.domain.user;


import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"images"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100, unique = true) //OAuth2 로그인을 위해 칼럼 길이 늘리기
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website; //웹사이트

    private String bio; //자기 소개

    @Column(nullable = false)
    private String email;

    private String phone;

    private String gender;

    private String profileImageUrl; //사진

    private String role; //권한

    //나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼 만들지마!
    //User 를 select 할때 해당 User id로 등록된 image 를 모두 가져와!
    //Lazy(default) = User 를 select 할 때 해당 User id로 등록된 image 들을 가져오지마. - 대신 .getImages() 함수의 image 들이 호출될 때 가져와
    //Eager = User 를 select 를 할 때 해당 User id로 등록된 image 들을 전부 Join 해서 가져와!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images; //양방향 매핑

    private LocalDateTime createDate;

    @PrePersist //디비에 insert 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

}
