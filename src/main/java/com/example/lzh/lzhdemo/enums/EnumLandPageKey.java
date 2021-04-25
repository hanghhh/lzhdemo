package com.example.lzh.lzhdemo.enums;

public enum EnumLandPageKey {

    Entries("entries/"),
    Articles("articles/"),
    ALL("all");

    private final String value;

    EnumLandPageKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
