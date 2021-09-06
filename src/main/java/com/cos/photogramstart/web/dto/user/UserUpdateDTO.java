package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String name; //필수

    private String password; //필수

    private String website;

    private String bio;

    private String phone;

    private String gender;

    public User toEntity(){

        User user = User.builder()
                .name(name) //name 필수 Validation 체크
                .password(password) //패스워드를 기재 안했으면 문제!! Validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();

        return user;

    }
}
