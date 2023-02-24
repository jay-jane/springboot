package com.sk22345.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk22345.myweb.command.CategoryVO;
import com.sk22345.myweb.command.ProductUploadVO;
import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.service.ProductService;

@RestController
public class AjaxController {
	
	@Autowired
	private ProductService service;
	
	@Value("${project.uploadpath}")
	private String uploadpath;
	
	//대분류 카테고리 요청
	@GetMapping("/getCategory")
	public List<CategoryVO> getCategory() {		
		return service.getCategory();
	}
	
	//중분류, 소분류 카테고리 요청
	@GetMapping("/getCategoryChild/{group_id}/{category_lv}/{category_detail_lv}")
	public List<CategoryVO> getCategoryChild(@PathVariable("group_id") String group_id,
											 @PathVariable("category_lv") int category_lv,
											 @PathVariable("category_detail_lv") int category_detail_lv) {
		CategoryVO vo = CategoryVO.builder().group_id(group_id)
											.category_lv(category_lv)
											.category_detail_lv(category_detail_lv)
											.build();
		return service.getCategoryChild(vo);
	}
	
	//이미지 정보 처리 - 화면에 2진 데이터 타입 반환
//	@GetMapping("/display/{filepath}/{uuid}/{filename}")
//	public byte[] display(@PathVariable("filepath") String filepath,
//						  @PathVariable("uuid") String uuid,
//						  @PathVariable("filename") String filename) {
//		
//		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
//		File file = new File(savename);
//		//저장된 이미지파일의 이진데이터 형식 구하기
//		byte[] result = null;
//		try {
//			result = FileCopyUtils.copyToByteArray(file);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	@GetMapping("/display/{filepath}/{uuid}/{filename}")
	public ResponseEntity<byte[]> display(@PathVariable("filepath") String filepath,
										  @PathVariable("uuid") String uuid,
										  @PathVariable("filename") String filename) {
		
		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
		File file = new File(savename);
		//저장된 이미지파일의 이진데이터 형식 구하기
		byte[] result = null; //1. 데이터
		ResponseEntity<byte[]> entity = null;
		try {
			result = FileCopyUtils.copyToByteArray(file);
			HttpHeaders header = new HttpHeaders(); //2. 헤더
			header.add("Content-type", Files.probeContentType(file.toPath())); //파일의 컨텐츠타입 구하기
			entity = new ResponseEntity<>(result, header, HttpStatus.OK); //3. 응답 본문(데이터, 헤더, 상태값)
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	//prod_id값을 받아서 이미지 정보 반환
	@PostMapping("/getProductImg")
	public ResponseEntity<List<ProductUploadVO>> getProductImg(@RequestBody ProductVO vo) {
		return new ResponseEntity<>(service.getProductImg(vo), HttpStatus.OK); //(데이터, 상태값) - 헤더는 생략 가능
	}
	
	//이미지 다운로드
	@GetMapping("/download/{filepath}/{uuid}/{filename}")
	public ResponseEntity<byte[]> download(@PathVariable("filepath") String filepath,
										  @PathVariable("uuid") String uuid,
										  @PathVariable("filename") String filename) {
		
		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
		File file = new File(savename);
		//저장된 이미지파일의 이진데이터 형식 구하기
		byte[] result = null; //1. 데이터
		ResponseEntity<byte[]> entity = null;
		try {
			result = FileCopyUtils.copyToByteArray(file);
			HttpHeaders header = new HttpHeaders(); //2. 헤더
			//다운로드 기능
			header.add("Content-Disposition", "attachment; filename=" + filename);
			entity = new ResponseEntity<>(result, header, HttpStatus.OK); //3. 응답 본문(데이터, 헤더, 상태값)
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entity;
	} 
	
}
