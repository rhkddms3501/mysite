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
		// limit 설정 값
		Long limit = 10L;
		Long offset = 0L;

		// 검색어가 없을 경우 검색어는 비어있는 값
		if (searchWord == null) {
			searchWord = "";
		}

		// 페이지 사이즈
		int maxPage = new BoardDao().findMaxPage(searchWord);

		// offset이 0 보다 작을 경우 0으로 초기화
		if (request.getParameter("offset") != null) {
			offset = Long.parseLong(request.getParameter("offset"));
			if (offset < 0) {
				offset = 0L;
			} else {
				offset = offset * 10 - 10;
			}
		}
		
		// offset이 페이지 사이즈를 넘어가면 마지막 페이지로 초기화
		if (offset > maxPage) {

			if (maxPage % 10 == 0) {
				offset = (long) (maxPage-10);
			} else {
				offset = (long) (maxPage / 10) * 10;
			}
		}

		if (maxPage % 10 == 0) {
			maxPage = maxPage / 10;
		} else {
			maxPage = maxPage / 10 + 1;
		}

		// 게시글 리스트
		List<BoardVo> list = new BoardDao().findAll(searchWord, limit, offset);

		// 게시글 리스트, 검색어, offset, 페이지 사이즈, 현재 페이지
		request.setAttribute("list", list);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("offset", ((((offset.intValue() / 50) + 1) * 5) - 4));
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("currentPage", (offset.intValue() + 10) / 10);

		MvcUtil.forward("board/list", request, response);
	}
}
