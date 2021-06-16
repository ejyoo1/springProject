package com.spring.dto;

import java.util.Date;

public class AttachVO {
	// Attach 테이블 가른 이유 : 파일이 여러개일수도있음.
	private int ano; // Attach Table primary Key
	private String uploadPath; // 경로
	private String fileName;
	private String fileType; // 확장자
	private int pno; // Pds Table Primary Key == 본문 번호
	private String attacher; // 등록자
	private Date regDate; // 등록날짜
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getAttacher() {
		return attacher;
	}
	public void setAttacher(String attacher) {
		this.attacher = attacher;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
