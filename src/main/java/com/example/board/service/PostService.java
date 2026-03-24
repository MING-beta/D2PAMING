package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.domain.ServerType;
import com.example.board.domain.Category;
import com.example.board.domain.TradeStatus;
import com.example.board.dto.PostForm;
import com.example.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 전체 게시글 검색 및 필터링 조회
    @Transactional(readOnly = true)
    public List<Post> search(ServerType server, Category category, String keyword) {
        return postRepository.searchPosts(server, category, keyword);
    }

    // 최근 등록글 5개 조회
    @Transactional(readOnly = true)
    public List<Post> getRecentPosts() {
        return postRepository.findTop5ByOrderByCreatedAtDesc();
    }

    // 게시글 단건 조회 (작성자 정보 포함)
    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    // 상세 화면 등에서 호출: 게시글 조회 및 조회수 1 증가
    public Post findByIdAndIncreaseViewCount(Long id) {
        Post post = findById(id);
        post.setViewCount(post.getViewCount() + 1);
        return post;
    }

    // 새 거래글 작성
    public Post create(PostForm form, Member author) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setAuthor(author);
        post.setServerType(form.getServerType());
        post.setCategory(form.getCategory());
        post.setItemName(form.getItemName());
        post.setCurrencyType(form.getCurrencyType());
        post.setCurrencyName(form.getCurrencyName());
        post.setPriceAmount(form.getPriceAmount());
        post.setTradeStatus(form.getTradeStatus() != null ? form.getTradeStatus() : TradeStatus.SELLING);
        return postRepository.save(post);
    }

    // 거래글 수정 (작성자 본인만 가능)
    public Post update(Long id, PostForm form, String username) {
        Post post = findById(id);
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setServerType(form.getServerType());
        post.setCategory(form.getCategory());
        post.setItemName(form.getItemName());
        post.setCurrencyType(form.getCurrencyType());
        post.setCurrencyName(form.getCurrencyName());
        post.setPriceAmount(form.getPriceAmount());
        if (form.getTradeStatus() != null) {
            post.setTradeStatus(form.getTradeStatus());
        }
        return post;
    }

    // 게시글 삭제 (작성자 본인만 가능)
    public void delete(Long id, String username) {
        Post post = findById(id);
        // 삭제 권한 체크: 현재 로그인한 사용자와 게시글 작성자가 일치하는지 확인
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}
