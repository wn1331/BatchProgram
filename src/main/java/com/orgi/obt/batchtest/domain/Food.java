package com.orgi.obt.batchtest.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private Long serial_no;

    private String name;

    //상태
    @Enumerated(EnumType.STRING)
    private FoodStatus status;

    //생성일자
    private final LocalDateTime makeDateTime = LocalDateTime.now();

    @Builder
    public Food(Long serial_no, String name, FoodStatus status) {
        this.serial_no = serial_no;
        this.name = name;
        this.status = status;
    }

    public void changeFoodStatus(FoodStatus status) {
        this.status = status;
    }
}
