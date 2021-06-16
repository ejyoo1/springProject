package com.spring.command;

import org.apache.log4j.Logger;

public class PageMaker {
	private static final Logger INFO_LOGGER = Logger.getLogger(PageMaker.class);
	
	private int totalCount; // 전체 행의 개수 (fix Data - 개발자가 결정하는 데이터)
	private int startPage = 1; // 시작 페이지 번호
	private int endPage = 1; // 마지막 페이지 번호
	private int realEndPage; // 끝 페이지 번호
	private boolean prev; // 이전 페이지 버튼 유무
	private boolean next; // 다음 페이지 버튼 유무
	
	private int displayPageNum = 10; // 한 페이지에 보여줄 페이지 번호 개수 (fix Data - 개발자가 결정하는 데이터)
	
	private SearchCriteria cri; // 현재 페이지 정보
	
	// startPage, endPage
	private void calcDate() { // DB에서 totalCount 가져올 때 호출
		INFO_LOGGER.info("cri.getPage() : " + cri.getPage());
		INFO_LOGGER.info("cri.getPerPageNum() : " + cri.getPerPageNum());
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum); // (15 / 10) => 1.5 => 소수 첫째자리 수 올림 => * 10 ==> Math함수는 1의 자리 올림이 없음.
		INFO_LOGGER.info("endPage : " + endPage);
		startPage = (endPage - displayPageNum) + 1;
		INFO_LOGGER.info("startPage : " + startPage);
		
		realEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum())); // 실제 마지막 페이지
		INFO_LOGGER.info("realEndPage : " + realEndPage);
		
		if(startPage < 0) {
			startPage = 1;
		}
		if(endPage > realEndPage) {
			endPage = realEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcDate();
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getRealEndPage() {
		return realEndPage;
	}

	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public SearchCriteria getCri() {
		return cri;
	}

	public void setCri(SearchCriteria cri) {
		this.cri = cri;
	}
	
}
