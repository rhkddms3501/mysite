package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		contents = contents.replace("\r\n", "<br>");
		Long currentPage = Long.parseLong(request.getParameter("currentPage"));
		String searchWord = request.getParameter("searchWord") == null ? "" : request.getParameter("searchWord");
		
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContents(contents);
		
		// 게시글 수정
		new BoardDao().modify(vo);
		
		// redirect에 한글이 들어갈 시 ???로 나오는 문제
		// 원래 되던건 브라우저에서 자동으로 encoding을 해주기 때문
		// 직접 한글을 encoding 후 redirect에 사용
		String encodedParam = URLEncoder.encode(searchWord, "UTF-8");
		MvcUtil.redirect(request.getContextPath() + "/board?a=view&no=" + no + "&currentPage=" + currentPage + "&searchWord=" + encodedParam, request, response);
	}
}
