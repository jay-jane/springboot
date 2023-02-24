package com.sk22345.myweb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.sk22345.myweb.command.CategoryVO;
import com.sk22345.myweb.command.ProductUploadVO;
import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.util.Criteria;

@Mapper
public interface ProductMapper {
	
	int regist(ProductVO vo);
	int registFile(ProductUploadVO vo);
	ArrayList<ProductVO> getList(@Param("user_id") String user_id, @Param("cri") Criteria cri);
	int getTotal(@Param("user_id") String user_id, @Param("cri") Criteria cri);
	ProductVO getDetail(int prod_id);
	int update(ProductVO vo);
	//카테고리 대분류
	List<CategoryVO> getCategory();
	List<CategoryVO> getCategoryChild(CategoryVO vo);
	//이미지 데이터 조회
	List<ProductUploadVO> getProductImg(ProductVO vo);
}
