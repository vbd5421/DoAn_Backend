package com.doan.backend.repository;

import com.doan.backend.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT m from Member m")
    Page<Member> getListMember(Pageable pageable);

    @Query(value = "SELECT p.title From member m " +
            "inner join product_member pm on m.id = pm.member_id " +
            "inner join product p on pm.product_id = p.id",nativeQuery = true)
    List<String> getListProductName(Long memberId);
    @Query(value = "SELECT p.name From member m " +
            "inner join project_member pm on m.id = pm.member_id " +
            "inner join project p on pm.project_id = p.id",nativeQuery = true)
    List<String> getListProjectName(Long memberId);
}
