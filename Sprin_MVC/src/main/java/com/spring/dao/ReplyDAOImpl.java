package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.spring.command.SearchCriteria;
import com.spring.dto.ReplyVO;

public class ReplyDAOImpl implements ReplyDAO{

	private SqlSession session;
	public void setSqlSession(SqlSession session) {
		this.session=session;
	}
	
	@Override
	public void insertReply(ReplyVO reply) throws SQLException {
		session.update("Reply-Mapper.insertReply", reply);
	}

	@Override
	public void updateReply(ReplyVO reply) throws SQLException {
		session.update("Reply-Mapper.updateReply", reply);
	}

	@Override
	public void deleteReply(int rno) throws SQLException {
		session.update("Reply-Mapper.deleteReply", rno);
	}

	@Override
	public List<ReplyVO> selectReplyListPage(int bno, SearchCriteria cri) throws SQLException {
		int offset = cri.getStartRowNum();
		int limit = cri.getPerPageNum();
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		List<ReplyVO> replyList = session.selectList("Reply-Mapper.selectReplyList", bno, rowBounds);
		return replyList;
	}

	@Override
	public int countReply(int bno) throws SQLException {
		int count = session.selectOne("Reply-Mapper.countReply", bno);
		return count;
	}
	
	@Override
	public int selectReplySeqNextValue() throws SQLException {
		int rno = (Integer) session.selectOne("Reply-Mapper.selectReplySeqNextValue");
		return rno;
	}

}
