package ru.assaulov.utilitybills2.exeptions;

import java.io.Serial;

public class BaseException extends RuntimeException {

    @Serial
    static final long serialVersionUID = -7034897190745766939L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
