package com.example.board.domain;

public enum TradeStatus {
    SELLING("판매중"), SOLD("거래완료");

    private final String description;

    TradeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
