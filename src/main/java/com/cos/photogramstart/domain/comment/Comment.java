package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Comment{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String content;

    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name="imageId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

    private LocalDateTime createDate;

    @PrePersist //디비에 insert 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }


}



