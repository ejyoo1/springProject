package com.spring.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
   public void loginForm() throws Exception {
   }
   
   @RequestMapping(value="/common/login",method=RequestMethod.POST)
   public String loginPost(LoginCommand loginReq, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
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
   
}