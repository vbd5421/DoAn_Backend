package com.doan.backend.service;

import com.doan.backend.dto.MemberDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Member;
import com.doan.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FileService fileService;
    public Page<MemberDTO> getListMember(Pageable pageable,String searchInput){
        Page<Member> members = memberRepository.getListMember(pageable,searchInput);
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
    public List<Member> getAllMember(Sort sort) {
        return memberRepository.findAll(sort);
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
    public Resource getImageByMemberId(Long id) throws MalformedURLException {

        String imageName = memberRepository.getImageByMemberId(id);
        String pathFile  = memberRepository.getPathFileByMember(id);
        Path imagePath = Paths.get(pathFile + "/" +imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if(imageResource.exists() && imageResource.isReadable()){
            return imageResource;
        }else{
//            throw new NoPathFileException("Không tìm thấy đường dẫn ảnh");
            return null;
        }

    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy member"));
        memberRepository.delete(member);
    }


}
