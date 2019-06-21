package com.venux.redis.exception;

public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotFoundException(int i, String s) {
		super(s);
	}
}
