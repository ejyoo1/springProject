package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.spring.command.SearchCriteria;
import com.spring.dto.BoardVO;

public interface BoardDAO {
	// 자료실 조회
	List<BoardVO> selectSearchBoardList(SearchCriteria cri) throws SQLException;
	
	// 자료실 글 수 조회
	int selectSearchBoardListCount(SearchCriteria cri) throws SQLException;
	
	// bno 기준 글 조회
	BoardVO selectBoardByBno(int bno) throws SQLException;
	
	// 조회수 증가
	void increaseViewCount(int bno) throws SQLException;
	
	// 글 다음 번호 가져오기
	int selectBoardSequenceNextValue() throws SQLException;
	
	// 게시판 등록
	void insertBoard(BoardVO board) throws SQLException;
	
	// 게시판 수정
	void updateBoard(BoardVO board) throws SQLException;
	
	// 게시판 삭제
	void deleteBoard(int bno) throws SQLException;
}
