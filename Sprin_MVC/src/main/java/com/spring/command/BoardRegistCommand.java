package com.spring.command;

import java.util.Date;

import com.spring.dto.BoardVO;
import com.spring.dto.NoticeVO;

public class BoardRegistCommand {
	private String writer;
	private String title;
	private String content;
	
	
	
	public String getWriter() {
		return writer;
	}



	public void setWriter(String writer) {
		this.writer = writer;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "BoardRegistCommand [writer=" + writer + ", title=" + title + ", content=" + content + "]";
	}
	
	public BoardVO toBoardVO() {
		// VO에서 받을 수 있는 데이터는 최대한 작성
		BoardVO board = new BoardVO();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
      
		return board;
	}

}
