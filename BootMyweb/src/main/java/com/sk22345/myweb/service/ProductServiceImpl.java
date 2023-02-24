package com.sk22345.myweb.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sk22345.myweb.command.CategoryVO;
import com.sk22345.myweb.command.ProductUploadVO;
import com.sk22345.myweb.command.ProductVO;
import com.sk22345.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper mapper;

	@Value("${project.uploadpath}")
	private String uploadpath;
	
	//날짜 별로 폴더 생성
	public String makeDir() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String now = sdf.format(date);
		String path = uploadpath + "\\" + now; //폴더 경로
		File file = new File(path);
		if(file.exists() == false) { //폴더가 존재하지 않으면
			file.mkdir(); //폴더 생성
		}
		return now;
	}

	//프로세스에 예외가 발생하면 기존에 진행했던 crud작업 롤백
	//catch를 통해 예외처리가 진행될 경우 실행되지않음
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int regist(ProductVO vo, List<MultipartFile> list) {
		//글 등록 처리
		int result = mapper.regist(vo);
		//list 빈 값 제거
		list = list.stream().filter((x) -> x.isEmpty() == false).collect(Collectors.toList());
		//파일 insert
		for(MultipartFile file : list) {
			String origin = file.getOriginalFilename(); //파일명
			String filename = origin.substring(origin.lastIndexOf("\\") + 1); //파일명만 받아오는 작업, 브라우저 별로 경로가 다르기 때문에 이렇게 처리함
			String filepath = makeDir(); //폴더 생성
			//중복 파일 처리
			String uuid = UUID.randomUUID().toString();
			String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename; //최종 저장 경로

			try {
				File save = new File(savename); //세이브 경로
				file.transferTo(save); //파일 업로드
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
			//insert작업
			ProductUploadVO upVo = ProductUploadVO.builder().filename(filename)
															.filepath(filepath)
															.uuid(uuid)
															.prod_writer(vo.getProd_writer())
															.build();
			mapper.registFile(upVo);
		}
		return result;
	}

	@Override
	public ArrayList<ProductVO> getList(String user_id, Criteria cri) {
		return mapper.getList(user_id, cri);
	}

	@Override
	public int getTotal(String user_id, Criteria cri) {
		return mapper.getTotal(user_id, cri);
	}

	@Override
	public List<CategoryVO> getCategory() {
		return mapper.getCategory();
	}

	@Override
	public List<CategoryVO> getCategoryChild(CategoryVO vo) {
		return mapper.getCategoryChild(vo);
	}

	@Override
	public ProductVO getDetail(int prod_id) {
		return mapper.getDetail(prod_id);
	}

	@Override
	public int update(ProductVO vo) {
		return mapper.update(vo);
	}

	@Override
	public List<ProductUploadVO> getProductImg(ProductVO vo) {
		return mapper.getProductImg(vo);
	}

}
