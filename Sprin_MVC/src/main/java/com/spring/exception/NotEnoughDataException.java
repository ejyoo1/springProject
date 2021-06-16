package com.spring.exception;

public class NotEnoughDataException extends Exception{
	public NotEnoughDataException () {
		super("처리하고자 하는 데이터가 적절치 않습니다.");
	}
}
