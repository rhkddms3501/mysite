package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		

		BoardVo boardVo = new BoardDao().findNo(no);
		String contents = boardVo.getContents();
		boardVo.setContents(contents.replace("<br>", "\r\n"));
		request.setAttribute("boardVo", boardVo);
		
		MvcUtil.forward("board/modify", request, response);

	}

}
