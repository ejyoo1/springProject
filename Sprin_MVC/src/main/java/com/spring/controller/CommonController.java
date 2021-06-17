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

@Controller
public class CommonController {
   
   @Autowired
   private MemberService memberService;
   
   @Autowired
   private MenuService menuService;
   
   @RequestMapping(value="/common/login",method=RequestMethod.GET)
   public void loginForm()throws Exception{}
   
   @RequestMapping(value="/common/login",method=RequestMethod.POST)
   public String loginPost(LoginCommand loginReq, HttpServletRequest request,
			RedirectAttributes rttr)throws Exception{
	   // 리플렉션으로 인해 브라우저에서 오는 id, pwd를 찾아 자동으로 넣어준다.
	   // redirect 할 때 넘겨줄 데이터가 있을 때, RedirectAttributes 객체를 사용한다.
	   // RedirectAttributes 는 HandlerAdapter가 주는 것이다.
	   String url = "redirect:/index.do";
      
      HttpSession session = request.getSession();
      
      try {
         memberService.login(loginReq.getId(), loginReq.getPwd());
         session.setAttribute("loginUser", memberService.getMember(loginReq.getId()));
         session.setMaxInactiveInterval(6*60);
      } catch (SQLException e) {
         e.printStackTrace();
         throw e;
      } catch (NotFoundIDException | InvalidPasswordException e) {
         url="redirect:/common/login.do";
         rttr.addFlashAttribute("msg", e.getMessage());
      }
      
      return url;
   }
   
   @RequestMapping("/index")
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
   
   @RequestMapping("/getMcode")
   @ResponseBody
   public ResponseEntity<MenuVO> getMcode(String mName) throws Exception{
	   
	   ResponseEntity<MenuVO> entity = null;
	   
	   try {
		   MenuVO menu = menuService.getMenuByMname(mName);
		   
		   entity = new ResponseEntity<MenuVO>(menu, HttpStatus.OK); // 200 과 Menu 를 내보냄.
		   // HandlerAdaptor 가 받고 ResponseBody를 본 후
		   // Jackson bind(호환성 좋음)을 갖고와서 jackson에게 물음.
		   // jackson이 확인 후 변환시켜 String으로 HandlerAdaptor에게 넘겨주고
		   // HandlerAdaptor 가 Response에 담아서 넘겨준다.
	   } catch(SQLException e) {
		   entity = new ResponseEntity<MenuVO>(HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
	   // URL이 없으면 HandlerAdaptor 가 받은 URL로 리턴함.
	   // 화면 리턴 값이 없고 화면에 뿌려줄(Response) 데이터가 존재하다면, HandlerAdaptor 는 들어온 URL을 페이지 URL로 넘기는 작동을 하지 않는다.
	   // Response가 없다면, Request 시 받은 URL을 넘긴다. ==> /getMcode 를 넘김.
	   // 현재 화면 결정 인자가 존재하기 때문에 Request URL을 넘기지 않음.
	   // @ResponseBody : 리턴할 때 ViewResolver에게 전달하지 말고 ResponseBody에 담아서 전송요청
	   // HandlerAdaptor 'ResponseBody'를 읽고 제너릭 타입을 보고 판단하여 ResponseBody에 담음.
	   
	   return entity;
   }
   
   @RequestMapping("/subMenu")
   @ResponseBody
   public ResponseEntity<List<MenuVO>> subMenu(String mCode) throws Exception{
	   
	   ResponseEntity<List<MenuVO>> entity = null;
	   
	   try {
		   List<MenuVO> menuList = menuService.getSubMenuList(mCode);
		   
		   entity = new ResponseEntity<List<MenuVO>>(menuList, HttpStatus.OK); // 200 과 Menu 를 내보냄.
	   } catch(SQLException e) {
		   entity = new ResponseEntity<List<MenuVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   return entity;
   }
   
   // Handler Adaptor 를 session을 바로 주면, invalidate를 하고 redirect
   @RequestMapping(value="/common/logout", method=RequestMethod.GET)
   public String logout(HttpSession session) {
	   String url = "redirect:/";
	   session.invalidate();
	   
	   return url;
   }
}