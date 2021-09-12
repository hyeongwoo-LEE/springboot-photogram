package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDTO;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //Repository 는 EntityManager 를 구현해서 만들어져 있는 구현체

    @Transactional(readOnly = true)
    public List<SubscribeDTO> 구독리스트(int principalId, int pageUserId){

        //쿼리 준비
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribes, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); //절대 세미클론 첨부하면 안됨.

        //1.물음표 principalId
        //2.물음표 principalId
        //3.마지막 물음표 pageUserId

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //쿼리 실행 (qlrm 라이브러리 필요 = DTO 에 DB 결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDTO> subscribeDTOs = result.list(query, SubscribeDTO.class);

        return subscribeDTOs;
    }

    @Transactional
    public void 구독하기(int fromUserId, int toUserId){

        try{
            subscribeRepository.mSubscribe(fromUserId, toUserId);

        }catch (Exception e){
            throw new CustomApiException("이미 구독중 입니다.");
        }

    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){

        subscribeRepository.mUnSubscribe(fromUserId, toUserId);

    }
}
