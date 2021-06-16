package com.spring.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.spring.command.Criteria;
import com.spring.command.SearchCriteria;
import com.spring.dto.MemberVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIDException;

public interface MemberService {
	
	// 로그인
	void login(String id, String pwd) throws SQLException, NotFoundIDException,
											InvalidPasswordException;
	
	// 회원 정보 조회
	MemberVO getMember(String id) throws SQLException;
	
	// 회원 목록 조회(오버로딩)
	List<MemberVO> getMemberList() throws SQLException; // 전체 목록
	List<MemberVO> getMemberList(Criteria cri) throws SQLException; // 페이지 목록
	Map<String,Object> getMemberList(SearchCriteria cri) throws SQLException; // 검색 목록
	
	// 회원 등록
	void regist(MemberVO member) throws SQLException;
	
	// 회원 수정
	void modify(MemberVO member) throws SQLException;
	
	// 회원 삭제
	void remove(String id) throws SQLException;
	
	// 회원 정지
	void disabled(String id) throws SQLException;
	
	// 
	void enabled(String id) throws SQLException;
}
