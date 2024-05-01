package org.mjulikelion.memomanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class Like {
    private int memoId; // 좋아요를 누른 메모의 아이디
    private String userId; // 이 메모에 좋아요를 누른 유저의 아이디
    private LocalDateTime createdAt; // 좋아요가 눌린 시간
}