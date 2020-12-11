package br.edu.ifsp.domain.entities.book;

import java.util.Arrays;

public enum BookGenre {
    ACTION("Ação"),
    DRAMA ("Drama"),
    HISTORY ("História"),
    HORROR ("Horror"),
    SCIENCE ("Ciência"),
    TECHNICAL("Técnico");

    private String label;

    BookGenre(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static BookGenre toEnum(String value){
        return Arrays.stream(BookGenre.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
