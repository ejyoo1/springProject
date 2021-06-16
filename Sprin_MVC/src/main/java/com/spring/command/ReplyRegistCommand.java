package com.spring.command;

import java.util.Date;

import com.spring.dto.ReplyVO;

public class ReplyRegistCommand {
	private int bno; // 글번호
	private int qno;
	private String replyer; //댓글 작성자
	private String replytext; //댓글 내용
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getQno() {
		return qno;
	}
	public void setQno(int qno) {
		this.qno = qno;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public String getReplytext() {
		return replytext;
	}
	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	
	public ReplyVO toReplyVO() { // Service 호출하기 위해서 ReplyVO에 담을 메서드 생성 (vo에 담을때는 최대한 vo 데이터 넣을 수 있는것은 넣어야 함. => 나중에 없어서 문제될 수 있는 것을 방지함)
		ReplyVO reply = new ReplyVO();
		reply.setBno(bno);
		reply.setQno(qno);
		reply.setReplyer(replyer);
		reply.setReplytext(replytext);
		reply.setRegdate(new Date());
		reply.setUpdatedate(new Date());
		
		return reply;
	}
	
}
