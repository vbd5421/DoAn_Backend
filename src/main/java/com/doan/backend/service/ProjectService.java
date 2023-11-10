package com.doan.backend.service;

import com.doan.backend.dto.ProjectDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Member;
import com.doan.backend.model.Product;
import com.doan.backend.model.Project;
import com.doan.backend.repository.MemberRepository;
import com.doan.backend.repository.ProductRepository;
import com.doan.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberRepository memberRepository;
    public List<ProjectDTO> getAllProject(Long id,String name,Pageable pageable) {
        List<Project> projects = projectRepository.getAllProject(id,name,pageable).toList();
        List<ProjectDTO> projectList = projects.stream().map(
                project -> new ProjectDTO(
                        project.getName(),
                        project.getContent(),
                        project.getDescription(),
                        project.getCreateDate(),
                        project.getStatus(),
                        memberRepository.getListMemberByProduct(project.getId())
                )
        ).collect(Collectors.toList());

        return projectList;
    }
    public Project createOrUpdate(Project project,List<Long> member) {
        Project newProject = new Project();
        if(project.getId() != null) {
            newProject = projectRepository.findById(newProject.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy project"));
        }else {
            newProject.setCreateDate(new Date());
        }
        newProject.setName(project.getName());
        newProject.setUpdateDate(new Date());
        newProject.setContent(project.getContent());
        newProject.setDescription(project.getDescription());
        newProject.setStatus(0L);
        List<Member> members = new ArrayList<>();
        for(Long id : member) {
            Member mem = memberRepository.findById(id)
                    .orElseThrow(() -> new ResourceException("không tìm thấy nhân viên"));
            members.add(mem);
        }
        newProject.setMembers(new HashSet<>(members));
        return projectRepository.save(newProject);
    }
}
