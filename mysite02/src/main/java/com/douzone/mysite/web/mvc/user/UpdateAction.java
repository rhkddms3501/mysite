package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");

		UserVo vo = new UserVo();
		vo.setNo(authUser.getNo());
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);

		new UserDao().update(vo);

		/* session update..? */
		authUser.setName(vo.getName());

		MvcUtil.redirect(request.getContextPath() + "/user?a=updateform", request, response);

	}

}
