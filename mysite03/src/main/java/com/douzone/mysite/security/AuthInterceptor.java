package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. Hendler 종류 확인
		if(! (handler instanceof HandlerMethod)) {
			// DefaultServlethandler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		//3. Handler Method의 @Auth를 가녀오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		/* 과제 */
		/* Type확인 하는 부분 추가 */
		//4. Handler Method에 @Auth가 없으면 Type(Class)에 붙어 있는 지 확인
		/* 여기 */
		/* 3번이랑 비슷하게 */
		/* 사이 */
		
		//5. Type이나 Method에 @Auth가 없는경우
		if(auth == null){
			return true;
		}
		
		
		//6. @Auth가 붙어 있기 때문에 인증(Authenfication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		/* 과제 */
		/* 권한 체크 */
		/* admin은 admin페이지, user페이지 다 갈수 있으나, */
		/* user는 admin페이지 못가고 user페이지만 갈 수 있음. */
		/* 관리자 아닌 사람이 admin페이지 갈려고 하면 메인으로  */
		//7. 권한(Authorization)체크를 위해 @Auth의 role 가져오기("ADMIN", "USER")
		String role = auth.role();
//		String authUserRole = authUser.getRole();
		
		
		//6 인증확인
		return true;
	}
}



















