package io.hhplus.tdd;

import io.hhplus.tdd.point.InsufficientPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegal(IllegalArgumentException e) {

        return ResponseEntity.status(400).body(new ErrorResponse("400", "금액이 0보다 크지 않아 충전 또는 사용이 불가능합니다."));
    }

    @ExceptionHandler(value = InsufficientPointException.class)
    public ResponseEntity<ErrorResponse> handleInsufficient(InsufficientPointException e) {

        return ResponseEntity.status(400).body(new ErrorResponse("400", "잔고가 충분하지 않아 포인트 사용이 불가능합니다."));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

} // ApiControllerAdvice
