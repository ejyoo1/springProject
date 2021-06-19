package com.spring.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.command.LoginCommand;
import com.spring.dto.MenuVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIDException;
import com.spring.service.MemberService;
import com.spring.service.MenuService;

@Controller // 컨트롤러 지정
public class CommonController {
   
   @Autowired
   private MemberService memberService;
   
   @Autowired
   private MenuService menuService;
   
   // return이 없고 ResponseBody가 없을 때, 
   // HandlerAdaptor는 Request에서 받아온 주소를 넘겨준다.
   @RequestMapping(value="/common/login",method=RequestMethod.GET)
   public void loginForm() throws Exception {}
   
   @RequestMapping(value="/common/login",method=RequestMethod.POST) // 뷰의 요청경로 지정(WEB-INF-views 기준)
   public String loginPost(LoginCommand loginReq, HttpServletRequest request, 
		   RedirectAttributes rttr) throws Exception {
      // 리플렉션으로 인해 브라우저에서 오는 id와 pwd를 찾아 자동으로 LoginCommand에 넣어준다.
	  // redirect 할 때 넘겨줄 데이터가 있는 경우 RedirectAttributes 객체를 사용한다.
	  // RedirectAttributes 는 HandlerAdapter가 주는 것이다.
	  String url = "redirect:/index.do";
      
      HttpSession session = request.getSession();
      
      try {
         memberService.login(loginReq.getId(), loginReq.getPwd());
         session.setAttribute("loginUser", memberService.getMember(loginReq.getId()));
         session.setMaxInactiveInterval(6*60);
      } catch (SQLException e) {
    	 rttr.addFlashAttribute("msg","시스템 장애가 발생하였습니다. \n 관리자에게 문의해주세요.");
         e.printStackTrace();
         throw e;
      } catch (NotFoundIDException | InvalidPasswordException e) {
         url="redirect:/common/login.do";
         rttr.addFlashAttribute("msg", e.getMessage()); // login.jsp
         //addFlashAttribute로 메시지를 한번만 뿌려줌
      }
      
      return url;
   }
   
   // 데이터 넘길 때, Model 또는 ModelAndView 사용
   
   // * Model : 데이터만 설정
   // model.addAttribute("변수이름", "변수에 넣을 데이터값")
   // 스프링이 값을 뷰로 전달함.
   // 뷰(jsp 파일)dptjsms ${}를 사용하여 값을 가져옴.
   
   // * ModelAndView : 데이터, 뷰 설정 가능
   // mv.setViewName("Views에 있는 JSP이름"); // 뷰의 이름
   // mv.addObject("KEY","VALUE"); // 뷰로 보낼 때 데이터 값
   // 스프링이 값을 뷰로 전달함.
   // 뷰(jsp 파일)dptjsms ${}를 사용하여 값을 가져옴.
   
   // * RequestParam : 단일 파라미터를 받을때 사용하는 어노테이션
   // 단일 파라미터 넘어올 때 mCode가 있다면, 리플렉션으로 인하여 자동으로 받아올 수 있음.
   // ResponseBody가 없으므로 forward를 자동으로 해준다.
   @RequestMapping("/index") // 뷰의 요청 경로 저장 (CommonController / loginPost)
   public ModelAndView indexPage(ModelAndView mnv,
		   						@RequestParam(defaultValue="M000000")String mCode,
		   						HttpServletRequest request, HttpServletResponse response) throws Exception {
	   String url = "common/indexPage"; // forward
		
		try {
			List<MenuVO> menuList = menuService.getMainMenuList();
			MenuVO menu = menuService.getMenuByMcode(mCode);
			
			mnv.addObject("menuList", menuList);
			mnv.addObject("menu", menu);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		mnv.setViewName(url);
		return mnv;
   }
   
   @RequestMapping(value="/main")
   public String main() {
	   String url = "/common/main";
	   return url;
   }
   
   // HandlerMaper가 View에 URL을 넘기게 요청하지 않음.
   // HttpEntity는 Http의 요청(Request) 또는 응답(Response)에 해당하는
   // HttpHeader와 HttpBody를 포함하는 클래스
   // HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity임
   // 따라서 ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다.
   
   // HandlerAdaptor 가 받고 ResponseBody를 본 후
   // Jackson bind를 갖고와서 jackson에게 묻는다.
   // Jackson이 확인 후 변환시켜 String으로 HandlerAdaptor에게 넘겨주고
   // HandlerAdaptor 가 Response에 담아서 넘겨준다.
   @RequestMapping("/getMcode")
   @ResponseBody
   public ResponseEntity<MenuVO> getMcode(String mName) throws Exception{
	   ResponseEntity<MenuVO> entity = null;
	   
	   try {
		   MenuVO menu = menuService.getMenuByMname(mName);
		   
		   entity = new ResponseEntity<MenuVO>(menu, HttpStatus.OK); // 200 과 Menu를 내보냄
	   } catch(SQLException e) {
		   entity = new ResponseEntity<MenuVO>(HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   return entity;
   }
   
   
   
}