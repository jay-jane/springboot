package com.sk22345.myweb.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.service.ProductService;
import com.sk22345.myweb.util.Criteria;
import com.sk22345.myweb.util.PageVO;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	@Qualifier("productService")
	private ProductService service;
	
	@GetMapping("/productReg")
	public String reg() {
		return "product/productReg";
	}
	@GetMapping("/productList")
	public String list(HttpSession session, Criteria cri, Model model) { //HttpSession 또는 HttpServletRequest
		
		//프로세스
//		session.setAttribute("user_id", "admin"); //임의로 설정
		String user_id = (String)session.getAttribute("user_id");
		
		//페이지네이션 처리
		PageVO pageVO = new PageVO(cri, service.getTotal(user_id, cri));
		
		model.addAttribute("list", service.getList(user_id, cri));
		model.addAttribute("pageVO", pageVO);
		
		return "product/productList";
	}
	@GetMapping("productDetail")
	public String detail(@RequestParam("prod_id") int prod_id, Model model) {
		
		model.addAttribute("vo", service.getDetail(prod_id));
		
		return "product/productDetail";
	}
	
	@PostMapping("/registForm")
	public String registForm(@Valid ProductVO vo, RedirectAttributes ra, 
							 @RequestParam("file") List<MultipartFile> list) {
		//글 등록
		int result = service.regist(vo, list);
		String msg = result == 1 ? "입력되었습니다" : "실패";
		ra.addFlashAttribute("msg", msg);
		
		//파일 업로드 작업
		list = list.stream().filter((x) -> x.isEmpty() == false).collect(Collectors.toList());
		for(MultipartFile file : list) {
			if(file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "png, jpg, jpeg 형식만 등록할 수 있습니다");
				return "redirect:/product/productReg";
			}
		}
		
		return "redirect:/product/productList";
	}
	
	@PostMapping("/updateForm")
	public String updateForm(ProductVO vo, RedirectAttributes ra) {
		int result = service.update(vo);
		String msg = result == 1 ? "수정되었습니다" : "실패";
		ra.addFlashAttribute("msg", msg);
		
		return "redirect:/product/productList";
	}
	
}
