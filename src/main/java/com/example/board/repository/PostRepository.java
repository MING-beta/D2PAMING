package com.example.board.repository;

import com.example.board.domain.Category;
import com.example.board.domain.Post;
import com.example.board.domain.ServerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();
    
    @EntityGraph(attributePaths = {"author"})
    List<Post> findTop100ByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"author"})
    @Query(value = "SELECT p FROM Post p WHERE " +
           "(:server IS NULL OR p.serverType = :server) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:keyword IS NULL OR LOWER(p.itemName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')))",
           countQuery = "SELECT count(p) FROM Post p WHERE " +
           "(:server IS NULL OR p.serverType = :server) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:keyword IS NULL OR LOWER(p.itemName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Post> searchPosts(@Param("server") ServerType server,
                           @Param("category") Category category,
                           @Param("keyword") String keyword,
                           Pageable pageable);
}
