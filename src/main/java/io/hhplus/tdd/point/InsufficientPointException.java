package io.hhplus.tdd.point;

public class InsufficientPointException extends RuntimeException {

    public InsufficientPointException() {
        super("잔고가 충분하지 않아 포인트 사용이 불가능합니다.");
    }

    public InsufficientPointException(String msg) {
        super(msg);
    }

} // InsufficientPointException
