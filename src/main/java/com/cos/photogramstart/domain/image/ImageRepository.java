package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT i.* FROM image i JOIN likes l ON i.id = l.ImageId GROUP BY i.id ORDER BY COUNT(i.id) desc", nativeQuery = true)
    List<Image> mPopular();

    @Query(value = "select * from image where userId in(select toUserId from subscribe where fromUserId = :principalId) order by id DESC ", nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

}
