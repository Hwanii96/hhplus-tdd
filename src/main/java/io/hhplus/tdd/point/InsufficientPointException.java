package io.hhplus.tdd.point;

public class InsufficientPointException extends RuntimeException {

    public InsufficientPointException() {
        super("잔고가 충분하지 않습니다.");
    }

} // InsufficientPointException
