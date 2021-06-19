package com.spring.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.command.NoticeModifyCommand;
import com.spring.command.NoticeRegistCommand;
import com.spring.command.SearchCriteria;
import com.spring.dto.NoticeVO;
import com.spring.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/main")
	public void main()throws Exception{}
	
	@RequestMapping("/list")
	public void list(SearchCriteria cri, Model model) throws SQLException {
		Map<String,Object> dataMap = noticeService.getNoticeList(cri);

		model.addAttribute("dataMap",dataMap);	
		// model.addAllAttribute를 쓰지않은 이유는 jsp에 넘겨줄 떄 dataMap에서 객체를 꺼내는 상태이므로, 
		// request에 하나하나 개별적으로 담아주는 addAllAttribute 는 적합하지 않음.
	}
	
	@RequestMapping("/registForm")
	public String registForm(){
		String url = "notice/regist";
		return url;
	}
	
	//파일 이슈 없음. 받는 파라미터 서비스에 넘김.
	@RequestMapping("/regist")
	public String regist(NoticeRegistCommand regReq,HttpServletRequest request) throws Exception{
		String url = "notice/regist_success";

		NoticeVO notice = regReq.toNoticeVO();
		notice.setTitle((String)request.getAttribute("XSStitle"));

		noticeService.regist(notice);
		return url;
	}
	
	// 화면 리턴 안하지만 명시적으로 준것.
	@RequestMapping("/detail")
	public ModelAndView detail(int nno,
							   @RequestParam(defaultValue="") String from,
							   ModelAndView mnv ) throws SQLException{
		String url="notice/detail";

		NoticeVO notice = null;

		if(from.equals("modify")) {
			notice = noticeService.getNoticeForModify(nno);
		}else {
			notice = noticeService.getNotice(nno);
		}

		mnv.addObject("notice",notice);
		mnv.setViewName(url);

		return mnv;
	}
	
	//post건 get이건 생각하지 않고 이걸로 처리
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int nno,ModelAndView mnv) throws Exception{
		String url="notice/modify";

		NoticeVO notice = noticeService.getNoticeForModify(nno);

		mnv.addObject("notice",notice);
		mnv.setViewName(url);

		return mnv;

	}
	
	// method 작성 구분 기준 : 메서드를 get,Post를 구분해야 하는 상황인 경우 method를 명시
	//post로 오지 않으면 안받음
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(NoticeModifyCommand modifyReq,HttpServletRequest request)throws Exception{
		String url = "redirect:/notice/detail.do?from=modify&nno="+request.getParameter("nno");

		NoticeVO notice = modifyReq.toNoticeVO();		
		notice.setTitle((String)request.getAttribute("XSStitle"));

		noticeService.modify(notice);

		return url;
	}
	
	// 삭제
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public String remove(int nno) throws Exception{
		String url="notice/remove_success";

		noticeService.remove(nno);		

		return url;
	}
}
