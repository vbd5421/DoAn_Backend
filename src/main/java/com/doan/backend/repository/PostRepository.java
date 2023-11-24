package com.doan.backend.repository;


import com.doan.backend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByActiveIsTrue();

    List<Post> findAllByTitle(String title);

    @Query("SELECT p.image.name FROM Post p WHERE p.id=:id")
    String getImageByPostId(Long id);

    @Query("SELECT p.image.pathFile FROM Post p WHERE p.id=:id")
    String getPathFileByPostId(Long id);

    @Query("SELECT p FROM Post p WHERE p.active = true ORDER BY p.id DESC")
    List<Post> listAll();



    @Query("SELECT p FROM Post p WHERE " +
            "(:si IS NULL OR (LOWER(p.title) LIKE LOWER(concat('%', :si , '%')) " +
            "OR LOWER(p.description) LIKE LOWER(concat('%', :si , '%')))) " +
            "AND((:start IS NULL AND :end IS NULL) OR(p.createDate BETWEEN :start AND :end))" +
            "AND p.active=true")

    Page<Post> searchTitleDescriptionAndCategory(Pageable pageable,
                                                 @Param("si")String searchInput,
                                                 @Param("start")LocalDate start,
                                                 @Param("end")LocalDate end
    );




    @Query(value = "SELECT DISTINCT p.* FROM post p LEFT JOIN post_hashtag ph ON p.id = ph.id " +
            "LEFT JOIN hashtag h ON ph.hashtag_id = h.hashtag_id " +
            "INNER JOIN category c ON p.category_id = c.category_id " +
            "WHERE (:si IS NULL OR (LOWER(p.title) LIKE LOWER(concat('%', :si , '%')) " +
            "OR LOWER(p.description) LIKE LOWER(concat('%', :si , '%')))) " +
            "AND (:cate IS NULL OR c.type_name LIKE :cate) " +
            "AND (:tag IS NULL OR h.name=:tag)" +
            "AND p.active=true",nativeQuery = true)
    Page<Post> searchTitleAndDescription(Pageable pageable,
                                         @Param("si")String searchInput,
                                         @Param("cate")String cate,
                                         @Param("tag") String tag);

    @Query(value = "SELECT * FROM post p " +
            "WHERE p.url LIKE ?1 AND p.active =true",nativeQuery = true)
    Post findPostBaseByUrl(String url);


    @Query("SELECT p.id FROM Post p WHERE LOWER(p.title)=LOWER(:title)")
    Long findIdByTitle(String title);

    Post findByTitle(String title);

    boolean existsByTitle(String title);

    @Query("SELECT pr.content, pr.title FROM Product pr INNER JOIN Post p ON  p.id = pr.id WHERE :si is null or (pr.title LIKE %:si% OR pr.content LIKE %:si%)")
    Page<Post> searchAll(Pageable pageable, @Param("si")String searchInput);


}
