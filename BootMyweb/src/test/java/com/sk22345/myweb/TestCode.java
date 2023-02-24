package com.sk22345.myweb;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.service.ProductMapper;
import com.sk22345.myweb.util.Criteria;

@SpringBootTest
public class TestCode {
	
	@Autowired
	ProductMapper mapper;
	
//	@Test
//	public void testCode01() {
//		for(int i = 1; i <= 300; i++) {
//			ProductVO vo =
//				ProductVO.builder().prod_enddate("2023-02-15").prod_writer("admin")
//								   .prod_name("test" + i).prod_price(i * 1000)
//								   .prod_count(i * 100).prod_discount(i)
//								   .prod_purchase_yn("N").prod_content("content" + i)
//								   .prod_comment("comment" + i).build();
//			mapper.regist(vo);
//		}
//	}
	
//	@Test
//	public void testCode02() {
//		
//		ArrayList<ProductVO> list = mapper.getList("admin", new Criteria(1, 10));
//		System.out.println(list.toString());
//		
//	}
	
}
