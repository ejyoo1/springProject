package com.spring.service;

import java.sql.SQLException;
import java.util.Map;

import com.spring.command.SearchCriteria;
import com.spring.dto.NoticeVO;

public interface NoticeService {
	
	// 목록 조회 (noticeList, pageMaker를 한꺼번에 보내기 위해 Map으로 설정)
	Map<String, Object> getNoticeList(SearchCriteria cri) throws SQLException;
	
	// 상세보기
	NoticeVO getNotice(int nno) throws SQLException;
	
	// 수정화면 상세
	NoticeVO getNoticeForModify(int nno) throws SQLException;
	
	// 등록
	void regist(NoticeVO notice) throws SQLException;
	
	// 수정
	void modify(NoticeVO notice) throws SQLException;
	
	//삭제
	void remove(int nno) throws SQLException;
	
}
