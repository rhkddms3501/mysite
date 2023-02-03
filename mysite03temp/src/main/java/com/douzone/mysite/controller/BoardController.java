package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model) {
		Map<String, Object> map = boardService.getContentsList(1, "");
		// jsp에서 map.list, map.beginPage 이렇게 쓸거를 list, beginPage로 바로 쓸수 있도록
		// 풀어서 모델에 담아 보내는게 addAllAttributes(map)
		model.addAllAttributes(map);
		
		return "board/index";
	}
	
	@RequestMapping(value = ("/write"), method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value = ("/write"), method = RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@RequestMapping("/view")
	public String view(BoardVo vo, Model model) {
		BoardVo boardVo = boardService.getContents(vo.getNo());
		model.addAttribute(boardVo);
		return "/board/view";
	}
	
	@RequestMapping(value = ("/modify"), method = RequestMethod.GET)
	public String modify(BoardVo vo, Model model) {
		BoardVo boardVo = boardService.getContents(vo.getNo());
		model.addAttribute(boardVo);
		return "board/modify";
	}
	
	@RequestMapping(value = ("/modify"), method = RequestMethod.POST)
	public String modify(BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board";
	}
	
	@RequestMapping("/delete")
	public String delete(BoardVo vo) {
		boardService.deleteContents(vo.getNo(), vo.getUserNo());
		return "redirect:/board";
	}

}
