package com.spring.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.spring.dto.AttachVO;

/**
 * 파일명을 고유하게 받는 방법
 * @author PC-19
 * UUID + 구분자
 */
public class MakeFileName {
	public static String toUUIDFileName(String fileName, String delimiter) {// fileName : originalFileName /
		 String uuid = UUID.randomUUID().toString().replace("-", "");
		 return uuid + delimiter + fileName;
	}
	
	public static String parseFileNameFromUUID(String fileName, String delimiter) {
		String[] uuidFileName = fileName.split(delimiter);
		return uuidFileName[uuidFileName.length -1];
	}
	
	public static List<AttachVO> parseFileNameFromAttaches(List<AttachVO> attachList, String delimiter){
		List<AttachVO> renamedAttachList = new ArrayList<AttachVO>();
		
		if(attachList != null) {
			for(AttachVO attach : attachList) {
				String fileName = attach.getFileName(); //DB상의 fileName
				fileName = parseFileNameFromUUID(fileName, delimiter); // uuid가 제거됨 // fileName
				
				attach.setFileName(fileName);
				
				renamedAttachList.add(attach);
			}
		}
		return renamedAttachList;
	}
}
