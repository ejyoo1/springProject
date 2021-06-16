package com.spring.command;

public class SearchCriteria extends Criteria {
	
	// 사용자가 값을 주지 않아도 null이 되지 않기위해 명시적 초기화 수행함.
	private String searchType = ""; // 검색구분 
	private String keyword = ""; // 검색어
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
