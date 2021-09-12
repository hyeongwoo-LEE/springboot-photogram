package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying //insert, update, delete
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserId= :toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);

    @Query("select count(s.id) from Subscribe s where s.toUser.id=:pageUserId and s.fromUser.id=:principalId")
    int mSubscribeState(int principalId, int pageUserId);

    @Query("select count(s.id) from Subscribe s where s.fromUser.id = :pageUserId")
    int mSubscribeCount(int pageUserId);

}
