package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * offset searchWord
	 */
	@RequestMapping("")
	public String index(String offset, String searchWord, Model model) {
		Map<String, Object> map = boardService.getContentsList(offset, searchWord);
		model.addAllAttributes(map);
		return "/board/index";
	}

	/**
	 * no currentPage searchWord
	 */
	@RequestMapping("/view")
	public String view(String no, String currentPage, String searchWord, Model model) {

		Map<String, Object> map = boardService.getContents(no, currentPage, searchWord);
		model.addAllAttributes(map);
		return "/board/view";
	}

	/**
	 * currentPage searchWord
	 */
	@Auth
	@RequestMapping(value = ("/write"), method = RequestMethod.GET)
	public String write(String currentPage, String searchWord, Model model) {
		Map<String, Object> map = Map.of("currentPage", currentPage, "searchWord", searchWord);
		model.addAllAttributes(map);
		return "board/write";
	}

	/**
	 * vo
	 */
	@Auth
	@RequestMapping(value = ("/write"), method = RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.addContents(vo);
		return "redirect:/board";
	}

	/**
	 * boardNo currentPage searchWord
	 */
	@Auth
	@RequestMapping(value = ("/reply"), method = RequestMethod.GET)
	public String reply(String borderNo, String currentPage, String searchWord, Model model) {
		Map<String, Object> map = Map.of("replyNo", borderNo, "currentPage", currentPage, "searchWord", searchWord);
		model.addAllAttributes(map);
		System.out.println("BoardController - reply(GET)");
		return "board/reply";
	}

	/**
	 * vo replyNo currentPage searchWord
	 */
	@Auth
	@RequestMapping(value = ("/reply"), method = RequestMethod.POST)
	public String reply(BoardVo vo, String replyNo, String currentPage, String searchWord, Model model) {
		System.out.println("vo = " + vo);
		System.out.println("currentPage = " + currentPage);
		System.out.println("searchWord = " + searchWord);
		Map<String, Object> map = boardService.addReply(vo, currentPage, searchWord);
		model.addAllAttributes(map);
		return "redirect:/board";
	}

	/* */
	@Auth
	@RequestMapping(value = ("/modify"), method = RequestMethod.GET)
	public String modify(String no, String currentPage, String searchWord, Model model) {
		Map<String, Object> map = boardService.getContents(no, currentPage, searchWord);
		model.addAllAttributes(map);
		return "board/modify";
	}

	/* */
	@Auth
	@RequestMapping(value = ("/modify"), method = RequestMethod.POST)
	public String modify(String no, String title, String contents, String currentPage, String searchWord, Model model) {
		boardService.updateContents(no, title, contents, currentPage, searchWord);

		model.addAttribute("offset", currentPage);
		model.addAttribute("searchWord", searchWord);
		return "redirect:/board";
	}

	/* */
	@Auth
	@RequestMapping("/delete")
	public String delete(String no, String offset, Model model) {
		boardService.deleteContents(no);
		model.addAttribute("offset", offset);
		return "redirect:/board";
	}

}
