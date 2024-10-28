package com.boot.petboard.domain.constant;

import lombok.Getter;

public enum Category {
    FREEBOARD("자유게시판"),
    QNA("Q&A"),
    PHOTO("사진&UCC"),
    GUESTBOOK("방명록");


    @Getter private final String description;

    Category(String description) {
        this.description = description;
    }

}
