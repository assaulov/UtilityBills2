package ru.assaulov.utilitybills2.exeptions;

import lombok.AllArgsConstructor;

public class BaseException extends RuntimeException{

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
