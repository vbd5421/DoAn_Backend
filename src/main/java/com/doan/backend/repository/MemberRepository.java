package com.doan.backend.repository;

import com.doan.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query(value = "select m.name from member m inner join product_member pm " +
            "on m.id = pm.member_id where pm.product_id = :productId",nativeQuery = true)
    List<String> getListMemberByProduct(Long productId);

    @Query(value = "select m.name from member m inner join project_member pm " +
            "on m.id = pm.member_id where pm.project_id = :projectId",nativeQuery = true)
    List<String> getListMemberByProject(Long projectId);
}
