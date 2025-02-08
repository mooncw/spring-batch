package com.example.demo.batch.dto;

import com.example.demo.domain.Dummy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * batch update 하기전에 더티체킹으로 인한 update가 안되도록 dto 사용
 */
@Getter
@Builder
@AllArgsConstructor
public class DummyDto {
    private Long id;
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six;
    private String seven;

    public static DummyDto from(Dummy dummy) {
        return DummyDto.builder()
            .id(dummy.getId())
            .one(dummy.getOne())
            .two(dummy.getTwo())
            .three(dummy.getThree())
            .four(dummy.getFour())
            .five(dummy.getFive())
            .six(dummy.getSix())
            .seven(dummy.getSeven())
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

    public boolean isUpper() {
        return Character.isUpperCase(this.one.charAt(0));
    }
}
