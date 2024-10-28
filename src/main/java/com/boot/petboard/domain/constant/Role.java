package com.boot.petboard.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    @Getter private final String roleName;
    @Getter private final String description;
}

