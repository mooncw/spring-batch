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

    public void update() {
        this.one = Character.isLowerCase(this.one.charAt(0)) ? this.one.toUpperCase() : this.one.toLowerCase();
        this.two = Character.isLowerCase(this.two.charAt(0)) ? this.two.toUpperCase() : this.two.toLowerCase();
        this.three = Character.isLowerCase(this.three.charAt(0)) ? this.three.toUpperCase() : this.three.toLowerCase();
        this.four = Character.isLowerCase(this.four.charAt(0)) ? this.four.toUpperCase() : this.four.toLowerCase();
        this.five = Character.isLowerCase(this.five.charAt(0)) ? this.five.toUpperCase() : this.five.toLowerCase();
        this.six = Character.isLowerCase(this.six.charAt(0)) ? this.six.toUpperCase() : this.six.toLowerCase();
        this.seven = Character.isLowerCase(this.seven.charAt(0)) ? this.seven.toUpperCase() : this.seven.toLowerCase();
    }
}
