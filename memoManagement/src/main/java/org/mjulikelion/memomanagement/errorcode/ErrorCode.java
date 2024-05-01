package org.mjulikelion.memomanagement.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("4040", "user not found"),
    MEMO_NOT_FOUND("4041", "memo not found"),
    USER_ALREADY_EXISTS("4042", "user already exists"),
    USER_DOES_NOT_HAVE_ACCESS("4043", "user does not have access"),
    NOT_NULL("9001", "required value is null."),
    NOT_BLANK("9002", "required value is null or empty");

    private final String code;
    private final String message;

    //Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;

            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
