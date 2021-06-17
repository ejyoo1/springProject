package com.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.command.MemberModifyCommand;
import com.spring.command.MemberRegistCommand;
import com.spring.command.SearchCriteria;
import com.spring.dto.MemberVO;
import com.spring.service.MemberService;
import com.spring.util.MakeFileName;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/main")
	public void main() {}
	
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws SQLException {
		String url = "member/list";
  
		Map<String, Object> dataMap = memberService.getMemberList(cri);
		mnv.addAllObjects(dataMap);
		mnv.setViewName(url);
		
		return mnv;
	// getParameter 생각하지 않고 ModelAndView 에서 알아서 가져와줌.
	// 클라이언트에서 가져오는 Command 객체가 있어야 ModelAndView로 한번에 가져올 수 있는 것임
	// ModelAndView 에 URL과 내보낼 데이터 객체를 담아 MNV리턴
	// 만약 부적합한 열이 에러가 난다면 클라이언트, 서버단에서 데이터 가져오는 것 둘 중 하나임.
	// ModelAndView 에 Map을 넣는 경우 Map이 그대로 추가되는 것이 아니라, 각각 따로따로 request에 담아준다.
    // addAllObjects 로 하게되면 ModelAndView 가 Request에 하나씩 담아주는 것.
		// List 만들 떄, addAllObjects해서 Map을 넣었지만, 실제로는 따로따로 주는 것임.
	}
	
	@RequestMapping(value = "/registForm", method = RequestMethod.GET)
	public String registForm() {
		String url = "member/regist";
		return url;
	}

	// Autowire는 타입 비교이기 때문에 bean 객체를 가져오기 위해서는 'id'을 기준으로 해야 한다.
	// Bean 객체의 id를 찾아서 알아서 의존 주입 시켜준다.
	// MultipartPaser -> ServletUploadBuilder -> getUploadPath 한번에 처리
	@Resource(name = "picturePath")
	private String picturePath; // 이미지를 가져오는 핸들러 
	
	// 이미지 업로드
	// 파일 업로드 비동기 방식 ==> ajax에 처리 여부만 알림
	// 파일명 고유해야 하므로 savePicture 메서드 사용
	// Rest 방식으로 처리 결과를 보냄.(ResponseBody)
	// 한글 파일 명은 헤더에 오기 때문에 깨짐 방지로 produces = "text/plain;charset=utf-8"
	@RequestMapping(value = "/picture", method=RequestMethod.POST, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> picture(@RequestParam("pictureFile") MultipartFile multi, String oldPicture)
		throws Exception {
		ResponseEntity<String> entity = null;
		
		String result = "";
		HttpStatus status = null;
		
		/*파일 저장 확인*/
		if((result = savePicture(oldPicture, multi)) == null) {
			result = "업로드 실패했습니다.!";
			status = HttpStatus.BAD_REQUEST;
		} else {
			status = HttpStatus.OK;
		}
		
		entity = new ResponseEntity<String>(result, status);
		
		return entity;
	}
	
	// 지워야 할 파일, 저장할 파일 확인
	// multiFile을 직접 받아와서 -> Java 형식으로 변경하면서 저장. (transferTo) ==> 별도의 Parser가 필요없어짐.
	   //==> 지정된 경로에 지정한 파일이름으로 저장
	//사진 삭제
	
	// SpringMVC를 사용하는 이유? Spring 인프라 사용하며 Controller에 집중할 수있다. 나머지 제반 사항은 힘을 빼도 되는 것.
	
	//이미지 저장
	private String savePicture(String oldPicture, MultipartFile multi) throws Exception {
		String fileName = null;
		
		/*파일 유무 확인*/
		if(!(multi == null || multi.isEmpty() || multi.getSize() > 1024 * 1024 * 5)) {
			
			/* 파일 저장 폴더 설정 */
			String uploadPath = picturePath;
			fileName = MakeFileName.toUUIDFileName(multi.getOriginalFilename(), "$$");
			File storeFile = new File(uploadPath, fileName);
			
			storeFile.mkdirs();
			
			// local HDD 에 저장.
			multi.transferTo(storeFile);
			
			if(oldPicture != null && !oldPicture.isEmpty()) { // Empty보다 null 판단이 먼저
				File oldFile = new File(uploadPath, oldPicture);
				if(oldFile.exists()) {
					oldFile.delete();
				}
			}
		}
		return fileName;
	}
	
	// 이미지 내보내기
	@RequestMapping(value = "/getPicture", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public ResponseEntity<byte[]> getPicture(String picture) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String imgPath = this.picturePath;
		try {
			// in=new File InputStream(imgPath + File.separator+picture);
			in = new FileInputStream(new File(imgPath, picture));
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			in.close();
		}
		return entity;
	}
	
	// 화면에 내보내는 것이 아니므로 ResponseBody 사용.
	// Produces 로 한글 설정 처리 (byte 단위로..)
	// 파일명 (picture)을 받음.
	// 엔터티 이용, ioUtils 사용하여  Byte 단위로 ResponseEntity 에 넣으면 HandlerAdaptor가 넣어줌.
	// 무슨 데이터를 내보낼 지 집중.
	// Byte 단위면 ResponseEntity가 Response에 넣어 내보내게 됨. ==> FileDownloadResolver가 필요없음.
	
	// 아이디 체크
	@RequestMapping("/idCheck")
	@ResponseBody
	public ResponseEntity<String> idCheck(String id) throws Exception{
		ResponseEntity<String> entity = null;
		try {
			MemberVO member = memberService.getMember(id);
			
			if(member != null) {
				entity = new ResponseEntity<String>("duplicated",HttpStatus.OK);
			}else {
				entity = new ResponseEntity<String>("",HttpStatus.OK);
			}
		} catch (SQLException e) {
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entity;
	}
	
	// 화면 전송
	@RequestMapping(value = "/regist", method= RequestMethod.POST)
	public String regist(MemberRegistCommand memberReq) throws SQLException, IOException{
		String url = "member/regist_success";
		
		MemberVO member = memberReq.toMemberVO();
		memberService.regist(member);
		
		return url;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(String id, Model model) throws SQLException {
		String url = "member/detail";
		
		MemberVO member = memberService.getMember(id);
		model.addAttribute("member",member);
		
		return url;
		
		//modelAndView 이 아닌 Model 로 작성하는 코드
		// model일 때, 중요한 것은 addAttribute, Map일 때에는 addAllAttribute
	}
	
	// 수정 폼
	@RequestMapping(value = "/modifyForm", method = RequestMethod.GET)
	public String modify(String id, Model model) throws SQLException {
		String url = "member/modify"; // Resolver 가 링크를 붙여줌. (Servlet-context.xml)
		
		MemberVO member = memberService.getMember(id);
		model.addAttribute("member",member);
		
		return url;
	}
	
	// 수정 적용
	@RequestMapping(value = "/modify", method= RequestMethod.POST)
	public String modify(MemberModifyCommand modifyReq, HttpSession session, Model model) throws Exception {
		String url = "member/modify_success"; 
		MemberVO member = modifyReq.toParseMember();
		
		// 신규 파일 변경 및 기존 파일 삭제
		String fileName = savePicture(modifyReq.getOldPicture(), modifyReq.getPicture());
		member.setPicture(fileName);
		
		// 파일 변겨 없을 시 기존 파일 명 유지
		if(modifyReq.getPicture().isEmpty()) {
			member.setPicture(modifyReq.getOldPicture());
		}
		
		//DB 내용 수정
		memberService.modify(member);
		
		// 로그인한 사용자의 경우 수정된 정보로 session 업로드
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser != null && member.getId().equals(loginUser.getId())) {
			session.setAttribute("loginUser", member);
		}
		
		model.addAttribute("member",memberService.getMember(modifyReq.getId()));
		
		return url;
	}
	
	// 삭제
	// success 후 메시지를 넘길 때, member의 정보가 필요할 수 있어서 model에 member를 담는다.
	// 회원을 삭제할 때, 반드시 이미지를 삭제한 뒤, member를 삭제한다.
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(String id, HttpSession session, Model model) throws SQLException {
		String url = "member/remove_success";
		
		MemberVO member;
		
		// 이미지 파일을 삭제
		member = memberService.getMember(id);
		
		String savePath = this.picturePath;
		File imageFile = new File(savePath, member.getPicture());
		if(imageFile.exists()) {
			imageFile.delete();
		}
		
		// DB 삭제
		memberService.remove(id);
		
		// 삭제되는 회원이 로그인 회원인 경우 로그아웃 해야함.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser.getId().equals(member.getId())) {
			session.invalidate();
		}
		
		model.addAttribute("member",member);
		return url;
	}
}
