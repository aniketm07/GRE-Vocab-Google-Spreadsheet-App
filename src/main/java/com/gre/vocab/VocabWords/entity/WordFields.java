package com.gre.vocab.VocabWords.entity;

public enum WordFields {
    WORD("wor"),
    TRICK("tri"),
    SENTENCE("sen"),
    DEFINITION("def"),
    SYNONYM("syn");

    public final String word;

    WordFields(String word) {
        this.word = word;
    }
}
