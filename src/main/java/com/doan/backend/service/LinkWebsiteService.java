package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.LinkWebsite;
import com.doan.backend.repository.LinkWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkWebsiteService {
    @Autowired
    LinkWebsiteRepository linkWebsiteRepository;

    public Page<LinkWebsite> getAllLink(Pageable pageable, String name) {
        return linkWebsiteRepository.getAll(pageable,name);
    }

    public LinkWebsite getLinkById(Long id) {
        return  linkWebsiteRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy link"));
    }

    public LinkWebsite addOrUpdate(LinkWebsite linkWebsite) {
        LinkWebsite newLink = new LinkWebsite();
        if(linkWebsite.getId() != null) {
            newLink = linkWebsiteRepository.findById(linkWebsite.getId())
                    .orElseThrow(() -> new ResourceException("không tìm thấy link"));
        }
        newLink.setName(linkWebsite.getName());
        newLink.setUrl(linkWebsite.getUrl());
        return linkWebsiteRepository.save(newLink);
    }

    public void deleteLink(Long id) {
        LinkWebsite linkWebsite = linkWebsiteRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy link"));
        linkWebsiteRepository.delete(linkWebsite);
    }
}
