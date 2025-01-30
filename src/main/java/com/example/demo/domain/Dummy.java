package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 더미 데이터 엔티티
 */
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(50)")
    private String one;

    @Column(columnDefinition = "varchar(50)")
    private String two;

    @Column(columnDefinition = "varchar(50)")
    private String three;

    @Column(columnDefinition = "varchar(50)")
    private String four;

    @Column(columnDefinition = "varchar(50)")
    private String five;

    @Column(columnDefinition = "varchar(50)")
    private String six;

    @Column(columnDefinition = "varchar(50)")
    private String seven;

    // 더미 데이터 생성용 정적 팩토리 메서드
    public static Dummy create() {
        return Dummy.builder()
            .one("one")
            .two("two")
            .three("three")
            .four("four")
            .five("five")
            .six("six")
            .seven("seven")
            .build();
    }
}
