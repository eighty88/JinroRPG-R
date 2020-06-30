package com.github.eighty88.jinrorpg.api;

public enum TranslateType {
    NONE("none"),
    KANA("kana"),
    GOOGLE_IME("googleime");

    private String id;

    TranslateType(String id) {
        this.id = id;
    }

    public String toString() {
        return this.id;
    }
}
