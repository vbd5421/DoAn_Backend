package com.doan.backend.service;

import com.doan.backend.dto.ProjectDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.CateProject;
import com.doan.backend.model.Member;
import com.doan.backend.model.Product;
import com.doan.backend.model.Project;
import com.doan.backend.repository.CateprojectRepository;
import com.doan.backend.repository.MemberRepository;
import com.doan.backend.repository.ProductRepository;
import com.doan.backend.repository.ProjectRepository;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.doan.backend.service.StringUtils.getSearchableString;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CateprojectRepository cateprojectRepository;

    @Autowired
    private FileService fileService;

    public Page<Project> getAllProject(String name,Long cateId, Pageable pageable) {
       return projectRepository.getAllProject(name,cateId,pageable);
    }
    public List<Project> getListAllProject(Sort sort) {
        return projectRepository.findAll(sort);
    }
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy"));
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getContent(),
                project.getImage(),
                project.getCreateDate(),
                project.getCateProject(),
                project.getStatus(),
                memberRepository.getListMemberByProject(project.getId())
        );
    }

    public Project createOrUpdate(ProjectDTO projectDTO, MultipartFile file) throws IOException {
        System.out.println(projectDTO.getCateProject().toString());
        Project newProject = new Project();
        if (projectDTO.getId() != null) {
            newProject = projectRepository.findById(projectDTO.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy project"));
        } else {
            newProject.setCreateDate(new Date());
        }

        newProject.setName(projectDTO.getName());
        newProject.setUpdateDate(new Date());
        newProject.setContent(projectDTO.getContent());
        newProject.setDescription(projectDTO.getDescription());
        newProject.setUrl(getSearchableString(projectDTO.getName()));
        newProject.setCateProject(projectDTO.getCateProject());
        newProject.setStatus(0L);
        if(file!=null){
            newProject.setImage(fileService.uploadImage(file));
        }
        if(projectDTO.getMembers() != null) {
            newProject.setMembers(new HashSet<>(projectDTO.getMembers()));
        }


        return projectRepository.save(newProject);
    }

    public Resource getImageByProjectId(Long id) throws MalformedURLException {

        String imageName = projectRepository.getImageByProjectId(id);
        String pathFile  = projectRepository.getPathFileByProjectId(id);
        Path imagePath = Paths.get(pathFile + "/" +imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if(imageResource.exists() && imageResource.isReadable()){
            return imageResource;
        }else{
//            throw new NoPathFileException("Không tìm thấy đường dẫn ảnh");
            return null;
        }

    }
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy project"));
        projectRepository.delete(project);
    }
}
