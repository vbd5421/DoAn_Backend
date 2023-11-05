package com.doan.backend.service;

import com.doan.backend.model.Post;
import com.doan.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    FileService fileService;
    @Autowired
    PostRepository postRepository;

    Post createOrUpdate() {
        return null;
    }
}
