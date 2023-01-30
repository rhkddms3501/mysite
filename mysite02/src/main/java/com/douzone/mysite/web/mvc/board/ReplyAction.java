package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		Long replyNo = Long.parseLong(request.getParameter("replyNo"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		contents = contents.replace("\r\n", "<br>");
		Long currentPage = Long.parseLong(request.getParameter("currentPage"));
		String searchWord = request.getParameter("searchWord") == null ? "" : request.getParameter("searchWord");
		
		BoardVo vo = new BoardVo();
		vo.setUserNo(userNo);
		vo.setReplyNo(replyNo);
		vo.setTitle(title);
		vo.setContents(contents);
		
		// 답글 달기
		new BoardDao().Reply(vo);
		
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("currentPage", currentPage);
		
		MvcUtil.redirect(request.getContextPath() + "/board?offset=" + currentPage + "&searchWord=" + searchWord, request, response);
	}
}
