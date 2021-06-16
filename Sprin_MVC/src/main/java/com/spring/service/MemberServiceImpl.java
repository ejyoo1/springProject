package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.command.Criteria;
import com.spring.command.PageMaker;
import com.spring.command.SearchCriteria;
import com.spring.dao.MemberDAO;
import com.spring.dto.MemberVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIDException;

public class MemberServiceImpl implements MemberService {
   
    private MemberDAO memberDAO;
    public void setMemberDAO(MemberDAO memberDAO) {
       this.memberDAO = memberDAO;
    }
   
   @Override
   public void login(String id, String pwd) throws SQLException, NotFoundIDException, InvalidPasswordException {
	  MemberVO member = memberDAO.selectMemberById(id);
	  if (member == null) 
		  throw new NotFoundIDException();
	  if (!pwd.equals(member.getPwd()))
		  throw new InvalidPasswordException();
   }

   @Override
   public MemberVO getMember(String id) throws SQLException {
      MemberVO member = null;
	 member = memberDAO.selectMemberById(id);
      return member;
   }

	@Override
	public List<MemberVO> getMemberList() throws SQLException {
		List<MemberVO> memberList = memberDAO.selectMemberList();
		return memberList;
	}

	@Override
	public List<MemberVO> getMemberList(Criteria cri) throws SQLException { // 오버로딩(확장)
		List<MemberVO> memberList = memberDAO.selectMemberList(cri);
		return memberList;
	}

	@Override
	public Map<String,Object> getMemberList(SearchCriteria cri) throws SQLException {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(memberDAO.selectMemberListCount(cri));
		
		List<MemberVO> memberList = memberDAO.selectMemberList(cri);
		
		dataMap.put("memberList", memberList);
		dataMap.put("pageMaker", pageMaker);
		
		return dataMap;
	}

	@Override
	public void regist(MemberVO member) throws SQLException {
		memberDAO.insertMember(member);
	}

	@Override
	public void modify(MemberVO member) throws SQLException {
		memberDAO.updateMember(member);
	}

	@Override
	public void remove(String id) throws SQLException {
		memberDAO.deleteMember(id);
	}

	@Override
	public void disabled(String id) throws SQLException {
		memberDAO.disabledMember(id);
	}

	@Override
	public void enabled(String id) throws SQLException {
		memberDAO.enabledMember(id);
	}
}