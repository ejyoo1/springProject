package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.spring.dto.MenuVO;

public interface MenuDAO {
	
	// 메인메뉴
	List<MenuVO> selectMainMenu() throws SQLException;
	
	// 서브메뉴
	List<MenuVO> selectSubMenu(String mCode) throws SQLException;
	
	//메뉴 정보 = mCode
	MenuVO selectMenuByMcode(String mCode) throws SQLException;
	
	// 메뉴 정보 = mName
	MenuVO selectMenuByMname(String mName) throws SQLException;
}
