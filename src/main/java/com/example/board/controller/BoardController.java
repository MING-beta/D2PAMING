package com.example.board.controller;

import com.example.board.domain.ServerType;
import com.example.board.domain.Category;
import com.example.board.domain.CurrencyType;
import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.dto.PostForm;
import com.example.board.service.MemberService;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.security.Principal;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final MemberService memberService;
    private final com.example.board.service.ItemImageService itemImageService;

    public BoardController(PostService postService, MemberService memberService, com.example.board.service.ItemImageService itemImageService) {
        this.postService = postService;
        this.memberService = memberService;
        this.itemImageService = itemImageService;
    }

    // 게시판 목록 (서치 및 필터 지원 및 페이지네이션)
    @GetMapping("/list")
    public String list(@RequestParam(required = false) ServerType server,
                       @RequestParam(required = false) Category category,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        
        String cleanKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        Pageable pageable = PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postsPage = postService.search(server, category, cleanKeyword, pageable);
        
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        
        // 필터 유지를 위한 속성
        model.addAttribute("servers", ServerType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedServer", server);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);

        return "board/list";
    }

    // 게시판 상세 화면 이동 (조회수 증가 로직 포함)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, Principal principal) {
        Post post = postService.findByIdAndIncreaseViewCount(id);
        model.addAttribute("post", post);

        boolean isAuthor = false;
        if (principal != null) {
            isAuthor = post.getAuthor().getUsername().equals(principal.getName());
        }
        model.addAttribute("isAuthor", isAuthor);

        return "board/detail";
    }

    // 게시판 작성 폼 이동
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("servers", ServerType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("currencies", CurrencyType.values());
        model.addAttribute("itemNames", itemImageService.getAllMappedItemNames());
        return "board/create";
    }

    // 게시판 작성 처리
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute PostForm postForm,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/create";
        }

        Member member = memberService.findByUsername(principal.getName());
        postService.create(postForm, member);

        return "redirect:/board/list";
    }

    // 게시판 수정 폼 이동 (본인 검증)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, Principal principal) {
        Post post = postService.findById(id);

        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            return "redirect:/board/list";
        }

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        postForm.setServerType(post.getServerType());
        postForm.setCategory(post.getCategory());
        postForm.setItemName(post.getItemName());
        postForm.setCurrencyType(post.getCurrencyType());
        postForm.setCurrencyName(post.getCurrencyName());
        postForm.setPriceAmount(post.getPriceAmount());
        postForm.setTradeStatus(post.getTradeStatus());

        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", id);
        model.addAttribute("servers", ServerType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("currencies", CurrencyType.values());
        model.addAttribute("itemNames", itemImageService.getAllMappedItemNames());

        return "board/edit";
    }

    // 게시판 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute PostForm postForm,
                       BindingResult bindingResult,
                       Principal principal,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", id);
            return "board/edit";
        }

        postService.update(id, postForm, principal.getName());
        return "redirect:/board/detail/" + id;
    }

    // 게시판 삭제 처리
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        postService.delete(id, principal.getName());
        return "redirect:/board/list";
    }
}
