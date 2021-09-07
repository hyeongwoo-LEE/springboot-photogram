package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="subscribe_uk", //유니크 제약조건 이름
                        columnNames = {"fromUserId","toUserId"}
                )
        }
)
@Entity
public class Subscribe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist //디비에 insert 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
