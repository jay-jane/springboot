package com.sk22345.myweb.util;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PageVO {
	
	//페이지네이션 계산 클래스
	private int start; //첫 페이지 번호
	private int end; //끝 페이지 번호
	private boolean prev; //이전 페이지 버튼
	private boolean next; //다음 페이지 버튼
	
	private int page; //현재 페이지 번호
	private int amount; //페이지 당 데이터 개수
	private int total; //전체 데이터 수
	private int realEnd; //실제 끝 페이지 번호
	
	private int pageCount = 10; //페이지네이션 개수s
	
	private Criteria cri; //
	private ArrayList<Integer> pageList;
	
	public PageVO(Criteria cri, int total) {
		this.cri = cri;
		this.page = cri.getPage();
		this.amount = cri.getAmount();
		this.total = total;
		
		//끝 페이지 번호 :: (int)Math.ceil(현재 페이지 번호 / 페이지네이션 개수) * 페이지네이션 개수
		this.end = (int)Math.ceil(this.page / (double)this.pageCount) * this.pageCount;
		//시작 페이지 번호 :: 끝 페이지 번호 - 페이지네이션 개수 + 1
		this.start = this.end - this.pageCount + 1;
		//실제 끝 번호 :: (int)Math.ceil(전체 데이터 개수 / 페이지 당 데이터 개수)
		this.realEnd = (int)Math.ceil(this.total / (double)this.amount);
		//실제 끝 번호 재계산 :: 1~10 ? end=10 realEnd=15, 11~20 ? end=20 realEnd=15
		this.end = this.end > this.realEnd ? this.realEnd : this.end;
		//이전 버튼 활성화 :: start = 1, 11, 21 ...
		this.prev = this.start > 1;
		//다음 버튼 활성화
		this.next = this.realEnd > this.end;
		
		this.pageList = new ArrayList<>();
		for(int i = this.start; i <= this.end; i++) {
			pageList.add(i);
		}
	}
	
}
