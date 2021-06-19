package com.spring.command;

import java.util.Date;

import com.spring.dto.NoticeVO;

public class NoticeRegistCommand {
	// 사용자가 보내는 화면만 받아오면 됨
	private String title;
	private String writer;
	private String content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "NoticeRegistCommand [title=" + title + ", writer=" + writer + ", content=" + content + "]";
	}
	
	public NoticeVO toNoticeVO() {
		// VO에서 받을 수 있는 데이터는 최대한 작성
		NoticeVO notice = new NoticeVO();
		notice.setContent(this.content);
		notice.setTitle(this.title);
		notice.setWriter(this.writer);
		notice.setRegdate(new Date());
		return notice;
	}
}
