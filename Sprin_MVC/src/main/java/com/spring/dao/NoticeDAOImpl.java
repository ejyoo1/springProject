package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.spring.command.SearchCriteria;
import com.spring.dto.NoticeVO;

public class NoticeDAOImpl implements NoticeDAO{
	
	private SqlSession session;
	public void setSqlSession(SqlSession session) { // setSqlSession set메서드 이름으로 타겟 확인
		this.session=session;
	}
	
	@Override
	public List<NoticeVO> selectSearchNoticeList(SearchCriteria cri) throws SQLException {

		int offset = cri.getStartRowNum();
		int limit = cri.getPerPageNum();
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		List<NoticeVO> noticeList = 
				session.selectList("Notice-Mapper.selectSearchNoticeList", cri, rowBounds);
		
		return noticeList;
	}
	@Override
	public int selectSearchNoticeListCount(SearchCriteria cri) throws SQLException {
		int count = session.selectOne("Notice-Mapper.selectSearchNoticeListCount", cri);
		return count;
	}
	@Override
	public NoticeVO selectNoticeByNno(int nno) throws SQLException {
		NoticeVO notice = session.selectOne("Notice-Mapper.selectNoticeByNno", nno);
		return notice;
	}
	@Override
	public void increaseViewCount(int nno) throws SQLException {
		session.update("Notice-Mapper.increaseViewCount", nno);
	}
	@Override
	public int selectNoticeSequenceNextValue() throws SQLException {
		int seq_num = session.selectOne("Notice-Mapper.selectNoticeSequenceNextValue");
		return seq_num;
	}
	@Override
	public void insertNotice(NoticeVO notice) throws SQLException {
		session.update("Notice-Mapper.insertNotice", notice);
	}
	@Override
	public void updateNotice(NoticeVO notice) throws SQLException {
		session.update("Notice-Mapper.updateNotice", notice);
		
	}
	@Override
	public void deleteNotice(int nno) throws SQLException {
		session.update("Notice-Mapper.deleteNotice", nno);
		
	}
}
