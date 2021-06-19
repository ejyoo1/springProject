package com.spring.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.command.BoardModifyCommand;
import com.spring.command.BoardRegistCommand;
import com.spring.command.SearchCriteria;
import com.spring.dto.BoardVO;
import com.spring.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	@RequestMapping("/main")
	public String main()throws Exception{
		return "board/main";
	}
	
	//criteria, return
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv)throws SQLException{
		String url="board/list";		

		Map<String,Object> dataMap = service.getBoardList(cri);

		mnv.addObject("dataMap",dataMap);
		mnv.setViewName(url);

		return mnv;
	}
	
	@RequestMapping("/registForm")
	public String registForm(){
		String url="board/regist";		
		return url;
	}
	
	@RequestMapping("/regist")
	public String regist(BoardRegistCommand regReq)throws Exception{
		String url="board/regist_success";

		BoardVO board=regReq.toBoardVO();
		service.regist(board);

		return url;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(int bno,String from, ModelAndView mnv )throws SQLException{
		String url="board/detail";		

		BoardVO board =null;
		if(from!=null && from.equals("modify")) {
			board=service.getBoardForModify(bno);
		}else {
			board=service.getBoard(bno);
		}

		mnv.addObject("board",board);		
		mnv.setViewName(url);

		return mnv;
	}
	
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int bno,ModelAndView mnv)throws SQLException{
		String url="board/modify";

		BoardVO board = service.getBoardForModify(bno);

		mnv.addObject("board",board);		
		mnv.setViewName(url);

		return mnv;
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(BoardModifyCommand modifyReq,HttpServletRequest request) throws Exception{

		String url = "redirect:/board/detail.do?from=modify&bno="+request.getParameter("bno");

		BoardVO board = modifyReq.toBoardVO();				
		board.setTitle((String)request.getAttribute("XSStitle"));

		service.modify(board);

		return url;
	}

	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public String remove(int bno) throws Exception{
		String url = "board/remove_success";
		service.remove(bno);		
		return url;		
	}
}
