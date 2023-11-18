package com.doan.backend.repository;

import com.doan.backend.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query(value = "select m.* from member m inner join product_member pm " +
            "on m.id = pm.member_id where pm.product_id = :productId",nativeQuery = true)
    List<Member> getListMemberByProduct(Long productId);

    @Query(value = "select m.* from member m inner join project_member pm " +
            "on m.id = pm.member_id where pm.project_id = :projectId",nativeQuery = true)
    List<Member> getListMemberByProject(Long projectId);
    @Query("SELECT m from Member m " +
            "where (:name is null or lower(m.fullName) like ('%'||:name||'%')) " +
            "and (:degree is null or m.degree = :degree) " +
            "and(:position is null or m.position = :position)")
    Page<Member> getListMember(Pageable pageable, @Param("name")String name,
                               @Param("degree")String degree,
                               @Param("position")String posittion);

    @Query(value = "SELECT p.title From member m " +
            "inner join product_member pm on m.id = pm.member_id " +
            "inner join product p on pm.product_id = p.id " +
            "where pm.member_id = :member_id",nativeQuery = true)
    List<String> getListProductName(@Param("member_id") Long memberId);
    @Query(value = "SELECT p.name From member m " +
            "inner join project_member pm on m.id = pm.member_id " +
            "inner join project p on pm.project_id = p.id " +
            "where pm.member_id = :member_id",nativeQuery = true)
    List<String> getListProjectName(@Param("member_id") Long memberId);
}
