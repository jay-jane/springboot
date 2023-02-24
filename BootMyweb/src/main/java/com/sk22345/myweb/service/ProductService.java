package com.sk22345.myweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sk22345.myweb.command.CategoryVO;
import com.sk22345.myweb.command.ProductUploadVO;
import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.util.Criteria;

public interface ProductService {
	
	int regist(ProductVO vo, List<MultipartFile> list);
	ArrayList<ProductVO> getList(String user_id, Criteria cri);
	int getTotal(String user_id, Criteria cri);
	ProductVO getDetail(int prod_id);
	int update(ProductVO vo);
	//카테고리 대분류
	List<CategoryVO> getCategory();
	//카테고리 중, 소분류
	List<CategoryVO> getCategoryChild(CategoryVO vo);
	//이미지 데이터 조회
	List<ProductUploadVO> getProductImg(ProductVO vo);
	
}
