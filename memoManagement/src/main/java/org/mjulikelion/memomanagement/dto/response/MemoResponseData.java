package org.mjulikelion.memomanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.domain.Memo;

@Builder
@Getter
@AllArgsConstructor
public class MemoResponseData {
    private Memo memo;
}
