package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchWord = request.getParameter("searchWord");
		Long limit = 10L;
		Long offset = 0L;
		
		if(searchWord == null) {
			searchWord = "";
		}
		
		int maxPage = new BoardDao().findMaxPage(searchWord);
		
		if(request.getParameter("offset") != null) {
			offset = Long.parseLong(request.getParameter("offset"));
			if(offset < 0) {
				offset = 0L;
			}else {
				offset = offset * 10 - 10;				
			}
		}
		if(offset > maxPage) {
			offset = (long) (maxPage / 10 * 10);
		}
		List<BoardVo> list = new BoardDao().findAll(searchWord, limit, offset);
		
		request.setAttribute("list", list);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("offset", ((((offset.intValue() / 50) + 1) * 5) - 4));
		request.setAttribute("maxPage", maxPage / 10 + 1);
		request.setAttribute("currentPage", (offset.intValue() + 10) / 10 );
		
		MvcUtil.forward("board/list", request, response);
	}
}
