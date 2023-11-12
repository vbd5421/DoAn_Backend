package com.doan.backend.service;

import com.doan.backend.dto.MemberDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Member;
import com.doan.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    public Page<Member> getListMember(Pageable pageable){
        return memberRepository.getListMember(pageable);
    }

    public MemberDTO getMemberbyId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy thành viên"));
        List<String> listProjectName = memberRepository.getListProjectName(id);
        List<String> listProductName = memberRepository.getListProductName(id);
        return new MemberDTO(
                member.getFullName(),
                member.getDescription(),
                member.getPosition(),
                member.getDegree(),
                listProductName,
                listProductName
        );
    }
}
