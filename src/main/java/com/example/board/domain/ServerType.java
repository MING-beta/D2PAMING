package com.example.board.domain;

public enum ServerType {
    LADDER("래더"), STANDARD("스탠다드"), HARDCORE("하드코어");

    private final String description;

    ServerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
