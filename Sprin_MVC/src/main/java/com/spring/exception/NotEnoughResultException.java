package com.spring.exception;

public class NotEnoughResultException extends Exception{
	public NotEnoughResultException() {
		super("조회결과가 적절치 않습니다.");
	}
}
