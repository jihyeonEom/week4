package org.mjulikelion.memomanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class User {
    private String id; // 유저 ID
    @Setter
    private String name; // 유저 이름
}
