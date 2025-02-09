package com.example.boot07.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	/*
	 *	custom.properties 파일에 있는 파일업로드 
	 *  경로를 읽어와서 필드에 담는다
	 */
	@Value("${file.location}")
	private String fileLocation;
	
	@GetMapping("/file/uploadform")
	public String uploadForm() {
		return "file/uploadform";
	}
	/*
	 *	<input type="file" name="myFile"> 에 선택된 파일데이터를 처리하기 위해서는 
	 *
	 *	MultipartFile myFile로 매개변수를 선언하면된다.
	 */
	
	@PostMapping("/file/upload")
	public String upload(String title, MultipartFile myFile, Model m) {
		//원본파일명
		String orgFileName = myFile.getOriginalFilename();
		//파일의 크기
		long fileSize=myFile.getSize();
		//저장할 파일의 이름을 Universal Unique 한 ID 로 저장하기 위해
		String saveFileName = UUID.randomUUID().toString();
		//저장할 파일의 전체경로 구성하기 
		String filePath = fileLocation+File.separator+saveFileName;
		try {
			//업로드된 파일을 이동시킬 목적지 File 객체생성
			File dest = new File(filePath);
			//MultipartFile 객체의 메소드를 통해서 실제로 이동시키기(전송하기)
			myFile.transferTo(dest);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//원래는 DB 에 저장해야 하지만 테스트를 위해 view page 에 전달하기
		m.addAttribute("orgFileName",orgFileName);
		m.addAttribute("saveFileName",saveFileName);
		m.addAttribute("fileSize", fileSize);
		
		return "file/upload";
	}
}
