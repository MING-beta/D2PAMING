package com.example.board.domain;

public enum CurrencyType {
    RUNE("룬"), GEM("보석"), DP("DP (도파밍 포인트)");

    private final String description;

    CurrencyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
