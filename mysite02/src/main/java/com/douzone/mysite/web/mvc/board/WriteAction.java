package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		contents = contents.replace("\r\n", "<br>");
		
		BoardVo vo = new BoardVo();
		vo.setUserNo(userNo);
		vo.setTitle(title);
		vo.setContents(contents);
		
		// 게시글 추가
		new BoardDao().insert(vo);
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}
}
