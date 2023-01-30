package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		Long currentPage = Long.parseLong(request.getParameter("currentPage"));
		String searchWord = request.getParameter("searchWord") == null ? "" : request.getParameter("searchWord");

		// 쿠키 읽어오기
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		boolean isCookie = false;

		// 쿠키가 존재하면
		if (cookies != null && cookies.length > 0) {
			for (Cookie ck : cookies) {
				// 쿠키들 중 쿠키이름이 visit 쿠키가 존재하면
				if ("visit".equals(ck.getName())) {
					cookie = ck;
					// no에 해당하는 게시글을 방문한 적이 없으면
					if (!ck.getValue().contains("[" + no + "]")) {
						// visit 쿠키에 no추가
						cookie.setValue(cookie.getValue() + "[" + no + "]");
						// no게시글 조회수 업데이트
						new BoardDao().hitUp(no);
					}
					isCookie = true;
				}
			}
		}
		if (!isCookie) {
			// visitTest 쿠키가 존재하지 않으면
			// visitTest 쿠키 생성 및 추가
			cookie = new Cookie("visit", "[" + no + "]");
			new BoardDao().hitUp(no);
		}
		// 자정 - 현재시간 = 자정 까지 남은 시간
		LocalTime now = LocalTime.now();
		int h = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("HH")));
		int m = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("mm")));
		int s = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("ss")));
		int remainTime = 86400 - ((h*3600) + (m*60) + s);
		cookie.setPath(request.getContextPath()); // Path 설정
		cookie.setMaxAge(remainTime); // 하루 동안 유지
		response.addCookie(cookie);

		BoardVo boardVo = new BoardDao().findNo(no);
		request.setAttribute("boardVo", boardVo);
		String contents = boardVo.getContents();
		boardVo.setContents(contents.replace("\r\n", "<br>"));
		
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("currentPage", currentPage);

		MvcUtil.forward("board/view", request, response);
	}
}
