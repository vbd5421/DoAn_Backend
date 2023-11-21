package com.doan.backend.service;

import com.doan.backend.dto.MemberDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Member;
import com.doan.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FileService fileService;
    public Page<MemberDTO> getListMember(Pageable pageable,String name,String degree,String position){
        Page<Member> members = memberRepository.getListMember(pageable,name,degree,position);
        return members.map(member -> new MemberDTO(
                member.getId(), member.getFullName(),
                member.getDescription(),
                member.getImage() != null ? member.getImage() : null,
                member.getBirthDate(),
                member.getTimeJoin(),
                member.getPhone(),
                member.getEmail(),
                member.getPosition(),
                member.getDegree(),
                memberRepository.getListProjectName(member.getId()),
                memberRepository.getListProductName(member.getId())
        ));

    }

    public MemberDTO getMemberbyId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy thành viên"));
        List<String> listProjectName = memberRepository.getListProjectName(id);
        List<String> listProductName = memberRepository.getListProductName(id);
        return new MemberDTO(
                member.getId(),
                member.getFullName(),
                member.getDescription(),
                member.getImage() != null ? member.getImage():null,
                member.getBirthDate(),
                member.getTimeJoin(),
                member.getPhone(),
                member.getEmail(),
                member.getPosition(),
                member.getDegree(),
                listProductName,
                listProjectName
        );
    }
    public Member AddOrUpdate(MemberDTO memberDTO, MultipartFile file) throws IOException {
        Member member = new Member();
        if(memberDTO.getId() != null) {
            member = memberRepository.findById(memberDTO.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy thành viên"));
        }
        member.setFullName(memberDTO.getFullName());
        member.setDescription(memberDTO.getDescription());
        member.setBirthDate(memberDTO.getBirthDate());
        member.setTimeJoin(memberDTO.getTimeJoin());
        member.setPhone(memberDTO.getPhone());
        member.setEmail(memberDTO.getEmail());
        member.setPosition(memberDTO.getPosition());
        member.setDegree(memberDTO.getDegree());
        if(file != null) {
            member.setImage(fileService.uploadImage(file));
        }
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy member"));
        memberRepository.delete(member);
    }


}
