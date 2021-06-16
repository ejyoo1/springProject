package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.spring.command.PageMaker;
import com.spring.command.SearchCriteria;
import com.spring.dao.BoardDAO;
import com.spring.dao.ReplyDAO;
import com.spring.dto.BoardVO;

public class BoardServiceImpl implements BoardService {

	private BoardDAO boardDAO;
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	private ReplyDAO replyDAO;
	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}

	@Override
	public Map<String, Object> getBoardList(SearchCriteria cri) throws SQLException {
		Map<String, Object> dataMap = null;
		dataMap = new HashMap<String, Object>();

		// 현재 Page 번호에 맞는 리스트를 perPageNum 개수만큼 가져오기.
		List<BoardVO> boardList = boardDAO.selectSearchBoardList(cri);

		// Board 기준 Reply 개수 가져오기
		for (BoardVO board : boardList) {
			int replycnt = replyDAO.countReply(board.getBno());
			board.setReplycnt(replycnt);
		}

		// 전체 board 개수
		int totalCount = boardDAO.selectSearchBoardListCount(cri);

		// pageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);

		dataMap.put("boardList", boardList);
		dataMap.put("pageMaker", pageMaker);
		return dataMap;
	}

	@Override
	public BoardVO getBoard(int bno) throws SQLException {
		BoardVO board = null;
		board = boardDAO.selectBoardByBno(bno);
		boardDAO.increaseViewCount(bno);
		return board;
	}

	@Override
	public BoardVO getBoardForModify(int bno) throws SQLException {
		BoardVO board = null;
		board = boardDAO.selectBoardByBno(bno);
		return board;
	}

	@Override
	public void regist(BoardVO board) throws SQLException {
		int bno = boardDAO.selectBoardSequenceNextValue();
		board.setBno(bno);
		boardDAO.insertBoard(board);
	}

	@Override
	public void modify(BoardVO board) throws SQLException {
		boardDAO.updateBoard(board);
	}

	@Override
	public void remove(int bno) throws SQLException {
		boardDAO.deleteBoard(bno);
	}
}
