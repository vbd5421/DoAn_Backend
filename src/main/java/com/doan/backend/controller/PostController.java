package com.doan.backend.controller;


import com.doan.backend.dto.PostDTO;
import com.doan.backend.model.Post;
import com.doan.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.time.LocalDate;



@RestController
@RequestMapping("/api/bpost")
public class PostController {
    @Autowired
    private PostService postService;

    //* WORKED

    /***
     * Create a new Post
     * @param postDTO payload
     * @param file image file
     * @return a new post
     * @throws Exception
     */

    @PostMapping("/add")

    public ResponseEntity<?> createPost(@RequestPart PostDTO postDTO,
                                        @RequestPart(required = false) MultipartFile file
    )
            throws Exception {
        return ResponseEntity.ok(postService.createPost(postDTO, file));
    }

    //* WORKED

    /***
     * Update an existing post
     * @param postDTO payload
     * @param file image file
     * @return updated post
     * @throws Exception
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id,
                                        @RequestPart PostDTO postDTO,
                                        @RequestPart(required = false) MultipartFile file)
            throws Exception {
        return ResponseEntity.ok(postService.updatePost(id, postDTO, file));
    }

    //* WORKED

    /***
     * Get an existing post by id
     * @param id post's id
     * @return an existing post
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    //* WORKED

    /***
     * Disable an existing post by id
     * @param id post's id
     * @return an disabled post
     */
    @PostMapping("/delete/{id}")
    public void disablePostById(@PathVariable Long id) {
        postService.disablePostById(id);
    }

    //* WORKED

    /***
     * Return list of active post
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> getListPostActive() {
        return ResponseEntity.ok(postService.getListPostActive());
    }


    @GetMapping("")
    public Page<Post> searchTitleAndDescription(

            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "6")int size,
            @RequestParam(name = "search",required = false)String searchInput,
            @RequestParam(name = "start",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startTime,
            @RequestParam(name = "end",required = false )@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endTime
    )
    {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return postService.searchTitleAndDescription(pageable,searchInput,startTime,endTime);
    }

    @GetMapping("/tim-kiem")
    public Page<Post> search(

            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "9")int size,
            @RequestParam(name = "search",required = false)String searchInput,
            @RequestParam(name = "cate",required = false)String cate,
            @RequestParam(name = "tag",required = false)String tag
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return postService.search(pageable,searchInput,cate, tag);

    }
    //* WORKED

    /***
     * Return a post found by url
     * @param url post's url
     * @return an existing post
     */
    @GetMapping("/home/{url}")
    public ResponseEntity<?> getPostByUrl(@PathVariable("url") String url) {
        return ResponseEntity.ok(postService.getPostByUrl(url));
    }





    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImageByPostId(@PathVariable Long id) throws MalformedURLException {
        return ResponseEntity.ok(postService.getImageByPostId(id));
    }



    @GetMapping("/search")
    public Page<Post> searchAll(
            @RequestParam(name = "pageNo", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "5") int size,
            @RequestParam(name = "search", required = false) String searchInput) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return postService.searchAll(pageable, searchInput);
    }

}
