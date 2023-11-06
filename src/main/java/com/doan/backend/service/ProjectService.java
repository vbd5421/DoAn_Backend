package com.doan.backend.service;

import com.doan.backend.dto.ProductDTO;
import com.doan.backend.dto.ProjectDTO;
import com.doan.backend.model.Product;
import com.doan.backend.model.Project;
import com.doan.backend.repository.MemberRepository;
import com.doan.backend.repository.ProductRepository;
import com.doan.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberRepository memberRepository;
    public List<ProjectDTO> getAllProject(Pageable pageable) {
        List<Project> projects = projectRepository.getAllProject(pageable).toList();
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
}
