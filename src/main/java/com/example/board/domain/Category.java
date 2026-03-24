package com.example.board.domain;

public enum Category {
    WEAPON("무기"), ARMOR("방어구"), RUNE("룬"), GEM("보석"), CHARM("참/주얼"), ETC("기타");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
