package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/")
	public String index(Model model) {
		Map<String, Object> map = boardService.getContentsList(1, "");
		
		// jsp에서 map.list, map.beginPage 이렇게 쓸거를 list, beginPage로 바로 쓸수 있도록
		// 풀어서 모델에 담아 보내는게 addAllAttributes(map)
		model.addAllAttributes(map);
		
		return "board/index";
	}

}
