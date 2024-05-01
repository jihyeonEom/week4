package org.mjulikelion.memomanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Memo {
    private int id; // 메모 ID
    private String title; // 메모의 제목u

    @Setter
    private String content; // 메모 내용

    @JsonIgnore
    // 데이터를 반환할 때 userId를 제외하고 반환한다.
    // 메모 작성자의 ID
    private String userId;
    private List<Like> likes; // 이 메모의 좋아요 정보
}
