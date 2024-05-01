package org.mjulikelion.memomanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @NotNull(message = "name이 null입니다.")
    private String name;
}
