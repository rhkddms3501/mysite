package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("searchWord", request.getParameter("searchWord") == null ? "" : request.getParameter("searchWord"));
		request.setAttribute("currentPage", Long.parseLong(request.getParameter("currentPage")));
		
		MvcUtil.forward("board/write", request, response);
	}
}
