package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
