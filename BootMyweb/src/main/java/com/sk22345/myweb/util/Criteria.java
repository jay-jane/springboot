package com.sk22345.myweb.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {
	
	//SQL에 전달할 page, amount 값을 갖고 다니는 클래스
	private int page;
	private int amount;
	
	//검색키워드
	private String searchName;
	private String searchContent;
	private String searchPrice;
	private String startDate;
	private String endDate;
	
	public Criteria() {
		this.page = 1; //현재 페이지 번호
		this.amount = 10; //페이지 당 데이터 개수
	}
	
	public int getPageStart() {
		return (page - 1) * amount;
	}

}
