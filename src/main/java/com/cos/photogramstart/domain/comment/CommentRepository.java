package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query(value = "inset into commant(content, imageId, userId, createDate) values (:content, :imageId, :userId, now())", nativeQuery = true)
    Comment mSave(String content, int imageId, int userId);

}
