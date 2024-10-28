package com.boot.petboard.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pet")
public class KakaoUser {
    @Id
    private String email;

    @Column
    private String id;
}
