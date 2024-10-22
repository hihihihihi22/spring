package com.example.boot14.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot14.dto.MemberDto;
import com.example.boot14.service.MemberService;


@RestController
public class MemberController {
	
	@Autowired private MemberService service;
	
	@PutMapping("/members/{num}") //번호는 MemberDto 에 들어 있어서 경로 파라미터값은 따로 필요 없음 
	public MemberDto update(@RequestBody MemberDto dto) {
		System.out.println(dto);
		service.updateMember(dto);
		return dto;
	}
	
	@GetMapping("/members/{num}")
	public MemberDto getData(@PathVariable("num") int num) {
		//경로 파라미터로 전달되는 번호에 해당하는 회원정보를 리턴한다(json 으로 응답)
		return service.selectOne(num);
	}
	
	@DeleteMapping("/members/{num}")
	public Map<String, Object> delete(@PathVariable("num") int num){
		service.deleteMember(num);
		
		Map<String, Object> map=new HashMap<>();
		map.put("isSuccess", true);
		
		return map;
	}
	
	/*
	 *  axios.post("/members/", object type)  로 전송 했기 때문에 
	 *  request 의 body 에는 json 문자열이 들어 있다
	 *  json 문자열을 java 객체로 받기 위해서는 
	 *  @RequestBody 어노테이션이 필요하다 
	 *  
	 *  {"name":"입력한이름", "addr":"입력한주소"} 
	 *  
	 *  입력한 이름은 MemberDto 의 name 이라는 필드에 
	 *  입력한 주소는 MemberDto 의 addr 이라는 필더에 자동으로 담긴다 
	 */
	@PostMapping("/members")
	public MemberDto insert(@RequestBody MemberDto dto) {
		System.out.println(dto);
		//서비스가 리턴해주는 MemberDto 에는 추가된 회원의 번호도 들어 있다. 
		return service.addMember(dto);
	}
	
	@GetMapping("/members")
	public List<MemberDto> members() {
		
		return service.selectList();
	}
	
}










