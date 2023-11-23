package com.doan.backend.service;


import com.doan.backend.dto.PostDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Post;
import com.doan.backend.repository.ImageRepository;
import com.doan.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static com.doan.backend.service.StringUtils.getSearchableString;


@Service
public class PostService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;


    @Autowired
    private PostRepository postRepository;



    /***
     * Create a new Post
     * @param postDTO payload
     * @param file image file
     * @return a new post

     */
    public Post createPost(PostDTO postDTO, MultipartFile file) throws Exception {
        Post postInDB = postRepository.findByTitle(postDTO.getTitle());
        if (postInDB != null) {
            throw new ResourceException("Tiêu đề đã tồn tại");
        }
        // get title from payload
        String title = postDTO.getTitle();
        // get content from payload
        String content = postDTO.getContent();
        // get description from payload
        String description = postDTO.getDescription();

        // set value
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setDescription(description);
        post.setCreateDate((new Date()).toString());
        post.setUpdateDate((new Date()).toString());
        if (file != null) {
            post.setImage(imageRepository.save(fileService.uploadImage(file)));
        }

        post.setUrl(getSearchableString(postDTO.getTitle()));
        post.setActive(true);



        // save post
        return postRepository.save(post);
    }

    /***
     * Update an existing post
     * @param postDTO payload
     * @param file image file
     * @return updated post

     */
    public Post updatePost(Long id, PostDTO postDTO, MultipartFile file) throws Exception {
        // find post by id
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) { // check if post is not exist
            throw new ResourceException("Bài viết không tồn tại");
        }
        Post updatePost = post.get();

        //kiểm tra tiêu đề tồn tại trong các bài viết khác
        if (!updatePost.getTitle().equals(postDTO.getTitle())) {
            if (postRepository.existsByTitle(postDTO.getTitle())) {
                throw new ResourceException("Tên đã tồn tại");
            }
        }
        // get title from payload
        String title = postDTO.getTitle();
        // get content from payload
        String content = postDTO.getContent();
        // get description from payload
        String description = postDTO.getDescription();
        // get category from payload
        // set value
        updatePost.setTitle(title);
        updatePost.setContent(content);
        updatePost.setDescription(description);
        updatePost.setUpdateDate((new Date()).toString());
        if (file != null) {
            updatePost.setImage(imageRepository.save(fileService.uploadImage(file)));
        }

        updatePost.setUrl(getSearchableString(postDTO.getTitle()));
        updatePost.setActive(true);

        //get hashtags


//        updatePost.setHashtags(newHashtagList);
        // save post
        postRepository.save(updatePost);
        return updatePost;
    }

    /***
     * Get an existing post by id
     * @param id post's id
     * @return an existing post
     */
    public Post findPostById(Long id) {
        // find post by id
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new ResourceException("Không tìm thấy bài viết");
        } else {
            Post getPost = post.get();
            return getPost;
        }
    }

    /***
     * Disable an existing post by id
     * @param id post's id
     * @return an disabled post
     */
    public void disablePostById(Long id) {
        // find post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceException("khong tim thay id bai viet"));
        post.setUrl(null);
        post.setActive(false);
        postRepository.save(post);
    }

    /***
     * Return list of active post
     * @return
     */
    public List<Post> getListPostActive() {
        return postRepository.listAll();
    }


    public Page<Post> searchTitleAndDescription(Pageable pageable, String searchInput, String cate, String tag, LocalDate start, LocalDate end) {
//        if(start!= null && end!= null){
        return postRepository.searchTitleDescriptionAndCategory(pageable, searchInput, cate, start, end);
//        }else{
//            return postRepository.searchTitleAndDescription(pageable,searchInput,cate, tag);
//        }
    }

    public Resource getImageByPostId(Long id) throws MalformedURLException {
        String imageName = postRepository.getImageByPostId(id);
        String pathFile = postRepository.getPathFileByPostId(id);
        Path imagePath = Paths.get(pathFile + "/" + imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if (imageResource.exists() && imageResource.isReadable()) {
            return imageResource;
        } else {
//            throw new NoPathFileException("Không tìm thấy đường dẫn tới ảnh");
            return null;
        }

    }

    public Page<Post> search(Pageable pageable, String searchInput, String cate, String tag) {

        return postRepository.searchTitleAndDescription(pageable, searchInput, cate, tag);
    }


    /***
     * Return a post found by url
     * @param url post's url
     * @return an existing post
     */
    public Post getPostByUrl(String url) {
        // find post by url
        Post post = postRepository.findPostBaseByUrl(url);
        if (post == null) { // check if post is empty
            throw new ResourceException("Không tìm thấy bài viết");
        } else return post;
    }



    public Page<Post> searchAll(Pageable pageable, String searchInput) {
        return postRepository.searchAll(pageable, searchInput);
    }



}