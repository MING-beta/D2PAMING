package com.example.board.controller;

import com.example.board.domain.Comment;
import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.repository.CommentRepository;
import com.example.board.service.MemberService;
import com.example.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public CommentController(CommentRepository commentRepository, PostService postService, MemberService memberService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.memberService = memberService;
    }

    @PostMapping("/board/detail/{postId}/comment")
    @Transactional
    public String addComment(@PathVariable Long postId, @RequestParam String content, Principal principal) {
        if (content == null || content.trim().isEmpty()) {
            return "redirect:/board/detail/" + postId;
        }
        Member member = memberService.findByUsername(principal.getName());
        Post post = postService.findById(postId);
        
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(member);
        comment.setPost(post);
        
        commentRepository.save(comment);
        
        return "redirect:/board/detail/" + postId;
    }

    @PostMapping("/board/detail/{postId}/comment/{commentId}/delete")
    @Transactional
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Principal principal) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (comment.getAuthor().getUsername().equals(principal.getName())) {
            commentRepository.delete(comment);
        }
        return "redirect:/board/detail/" + postId;
    }
}
