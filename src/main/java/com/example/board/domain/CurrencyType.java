package com.example.board.domain;

public enum CurrencyType {
    RUNE("룬"), GEM("보석"), CP("CP (사이트 화폐)");

    private final String description;

    CurrencyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
