package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.spring.command.SearchCriteria;
import com.spring.dto.NoticeVO;

public interface NoticeDAO {
	
	// 공지사항 조회
	List<NoticeVO> selectSearchNoticeList(SearchCriteria cri) throws SQLException;
	
	int selectSearchNoticeListCount(SearchCriteria cri) throws SQLException;
	
	NoticeVO selectNoticeByNno(int nno) throws SQLException;
	
	// viewcnt 증가
	void increaseViewCount(int nno) throws SQLException;
	
	// Notice seq.nextval 가져오기
	int selectNoticeSequenceNextValue() throws SQLException;
	
	void insertNotice(NoticeVO notice) throws SQLException;
	
	void updateNotice(NoticeVO notice) throws SQLException;
	
	void deleteNotice(int nno) throws SQLException;
	
}
