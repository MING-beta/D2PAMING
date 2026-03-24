package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("상세 조회 시 조회수가 1 증가해야 한다.")
    void increaseViewCountTest() {
        // given
        Member author = new Member("testuser", "1234");
        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");
        post.setAuthor(author);
        post.setViewCount(0);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // when
        Post foundPost = postService.findByIdAndIncreaseViewCount(1L);

        // then
        assertEquals(1, foundPost.getViewCount());
    }

    @Test
    @DisplayName("작성자 본인이면 게시글을 삭제할 수 있다.")
    void deleteSuccessTest() {
        // given
        Member author = new Member("testuser", "1234");
        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");
        post.setAuthor(author);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // when
        postService.delete(1L, "testuser");

        // then
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    @DisplayName("작성자 본인이 아니면 게시글 삭제 시 예외가 발생한다.")
    void deleteFailTest() {
        // given
        Member author = new Member("testuser", "1234");
        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");
        post.setAuthor(author);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.delete(1L, "otheruser");
        });
        
        assertEquals("삭제 권한이 없습니다.", exception.getMessage());
        verify(postRepository, never()).delete(any());
    }
}
