package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.spring.command.Criteria;
import com.spring.command.SearchCriteria;
import com.spring.dto.MemberVO;

public interface MemberDAO { // 서비스로두터 Session을 받아서 처리할 것을 생각. DAO 메서드 명 구성할 때 무엇을 주고 무엇을 받을건지 생각
	
	// 회원 정보 조회(아이디)
	MemberVO selectMemberById(String id) throws SQLException;
	
	// 회원 정보 조회(전체)
	List<MemberVO> selectMemberList() throws SQLException;
	List<MemberVO> selectMemberList(Criteria cri) throws SQLException; // 페이징
	List<MemberVO> selectMemberList(SearchCriteria cri) throws SQLException; // 검색에 페이징
	
	// 검색 결과의 전체 리스트 개수
	int selectMemberListCount(SearchCriteria cri) throws SQLException;
	
	// 회원 추가
	void insertMember(MemberVO member) throws SQLException;
	
	// 회원 수정
	void updateMember(MemberVO member) throws SQLException;
	
	// 회원정보 삭제
	void deleteMember(String id) throws SQLException;
	
	// 회원 정지
	void disabledMember(String id) throws SQLException;
	
	// 회원 활성화
	void enabledMember(String id) throws SQLException;
}
