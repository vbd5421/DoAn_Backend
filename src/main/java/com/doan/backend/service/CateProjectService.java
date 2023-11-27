package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.CateProject;
import com.doan.backend.repository.CateprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateProjectService {
    @Autowired
    CateprojectRepository cateprojectRepository;

    public Page<CateProject> getListCateProject(Pageable pageable, String searchInput) {
        return cateprojectRepository.getListCateProject(pageable,searchInput);
    }

    public List<CateProject> getAllCateProject(Sort sort){
        return cateprojectRepository.findAll(sort);
    }
    public CateProject getCateById(Long id) {
        return cateprojectRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy cate có id = "+id));
    }
    public CateProject addOrUpdateCate(CateProject cateProject) {
        CateProject newCateProject = new CateProject();
        if(cateProject.getId() != null) {
            newCateProject = cateprojectRepository.findById(cateProject.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy cate có id = "+cateProject.getId()));
        }
        newCateProject.setTypeName(cateProject.getTypeName());
        newCateProject.setCateUrl(cateProject.getCateUrl());
        return cateprojectRepository.save(newCateProject);
    }
    public void deleteCateProject(Long id) {
        CateProject cateProject = cateprojectRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy cate có id = "+id));
        cateprojectRepository.delete(cateProject);
    }
}
