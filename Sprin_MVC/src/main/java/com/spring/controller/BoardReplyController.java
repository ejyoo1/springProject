package com.spring.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.command.PageMaker;
import com.spring.command.ReplyModifyCommand;
import com.spring.command.ReplyRegistCommand;
import com.spring.command.SearchCriteria;
import com.spring.dto.ReplyVO;
import com.spring.service.ReplyService;

@RestController
@RequestMapping("replies")
public class BoardReplyController {
	//RestController이기 때문에 ResponseBody가 필요없고
	// 가져오는 pathvariable의 값, (키값이라고 생각) ==> 그것을 PathVariable을 사용하여 가져온것을 변수에 삽입
	@Autowired
	private ReplyService service;
	
	//Method는 반드시 GET
	// pathvariable을 사용할때에는 .을 사용하면 안된다. Mapper를 거치기 때문에 . 이하는 생략된다.
	@RequestMapping(value="/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> replyList(@PathVariable("bno") int bno, 
														 @PathVariable("page") int page)throws Exception{
		ResponseEntity<Map<String, Object>> entity = null;

		SearchCriteria cri = new SearchCriteria();
		cri.setPage(page);

		try {
			Map<String, Object> dataMap = service.getReplyList(bno, cri);
			entity = new ResponseEntity<Map<String, Object>>(dataMap,HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


		return entity;

	}
	
	//JSON으로 넘겨줌
	
	// 댓글 등록
	// value="" 는 명시적으로 작성함. request 루트꺼를 쓰겠다.
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyRegistCommand registReq)	throws Exception {
		ResponseEntity<String> entity = null;

		ReplyVO reply = registReq.toReplyVO();	
		try {
			service.registReply(reply);

			SearchCriteria cri = new SearchCriteria();

			Map<String, Object> dataMap = service.getReplyList(reply.getBno(),cri);
			PageMaker pageMaker = (PageMaker) dataMap.get("pageMaker");
			int realEndPage = pageMaker.getRealEndPage();

			entity = new ResponseEntity<String>("SUCCESS,"+realEndPage,HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return entity;
	}
	
	@RequestMapping(value = "/{rno}", method = { RequestMethod.PUT,RequestMethod.PATCH })
	public ResponseEntity<String> modify(@PathVariable("rno") int rno,@RequestBody ReplyModifyCommand modifyReq) 
																					throws Exception {
		ResponseEntity<String> entity = null;

		ReplyVO reply = modifyReq.toReplyVO();
		reply.setRno(rno);

		try {
			service.modifyReply(reply);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return entity;
	}
	
	@RequestMapping(value="/{bno}/{rno}/{page}",method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno,
										 @PathVariable("bno") int bno,
										 @PathVariable("page") int page) throws Exception{
		ResponseEntity<String> entity=null;
		try{
			service.removeReply(rno);

			SearchCriteria cri= new SearchCriteria();

			Map<String,Object> dataMap=service.getReplyList(bno, cri);
			PageMaker pageMaker = (PageMaker)dataMap.get("pageMaker");

			int realEndPage=pageMaker.getRealEndPage();
			if(page>realEndPage){page=realEndPage;}


			entity=new ResponseEntity<String>(""+page,HttpStatus.OK);
		}catch(SQLException e){
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return entity;
	}
}
