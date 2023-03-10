<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
			<%-- <h1>${sitevo.title }</h1> --%>
			
			<!-- 스프링 쪽에서 뷰 리졸브에다가 ex어쩌고 해가지고 site라고 되어있는건 빈이다? -->
			<h1>${site.title }</h1>
			<ul>
			<!-- authUser는 세션 컨텍스트 부분 안에 들어있으니 알아서 찾을 것임. 동일한 이름이 없으면. -->
			<c:choose>
				<c:when test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath }/user/login">로그인</a><li>
					<li><a href="${pageContext.request.contextPath }/user/join">회원가입</a><li>	
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath }/user/update">회원정보수정</a><li>
					<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a><li>
					<li>${authUser.getName() }님 안녕하세요 ^^;</li>
				</c:otherwise>
			</c:choose>
			</ul>
		</div>