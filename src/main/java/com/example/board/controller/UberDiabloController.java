package com.example.board.controller;

import com.example.board.service.UberDiabloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/uber-diablo")
public class UberDiabloController {

    private final UberDiabloService uberDiabloService;

    public UberDiabloController(UberDiabloService uberDiabloService) {
        this.uberDiabloService = uberDiabloService;
    }

    @GetMapping
    public Object getUberDiabloProgress() {
        return uberDiabloService.fetchAllProgress();
    }
}
