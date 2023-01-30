package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보수정은 세션은 사용하지 말자고 하시는데 쌤이 ?
		
		
		/**
		 ************************ Access Control (접근제어) ***********************
		 **/
		HttpSession session = request.getSession();
		if(session == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		/**
		 ************************************************************************
		 **/
		UserVo vo = new UserDao().findByNo(authUser.getNo()); // 구현해야함.
		// 업데이트 할때 전체를 하번에 업데이트 하는데 비밀번호가 비어있을때랑 안비어있을떄가 달라진다?
		// 비밀번호를 안바꾸려면 jsp에서 ""이 날라오고 비밀번호를 바꾸려면 "바꾸려는값"이 날라오기 때문.
		request.setAttribute("vo", vo); // vo이름 바꿔도 되고 안바꿔도 되고
		MvcUtil.forward("user/updateform", request, response);
	}
}
