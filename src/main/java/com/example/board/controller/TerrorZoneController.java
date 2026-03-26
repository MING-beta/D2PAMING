package com.example.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/terror-zone")
public class TerrorZoneController {

    private final com.example.board.service.TerrorZoneService terrorZoneService;

    public TerrorZoneController(com.example.board.service.TerrorZoneService terrorZoneService) {
        this.terrorZoneService = terrorZoneService;
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public org.springframework.http.ResponseEntity<String> getTerrorZone() {
        return org.springframework.http.ResponseEntity.ok(terrorZoneService.fetchRawData());
    }

    @GetMapping(value = "/history", produces = "application/json;charset=UTF-8")
    public org.springframework.http.ResponseEntity<List<Map<String, String>>> getHistory() {
        return org.springframework.http.ResponseEntity.ok(terrorZoneService.getRecentHistory(10));
    }
}
