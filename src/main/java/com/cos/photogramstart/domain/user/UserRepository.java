package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    //JPA query method 사용
    User findByUsername(String username);

}
