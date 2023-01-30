<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<!-- 답글 쓰기 입력 폼 -->
				<!-- parameter : action = reply, 로그인한 유저 no, 답글 쓸려던 글 no, currentPage, searchWord, title, content -->
				<form class="board-form" method="post" action="${pageContext.request.contextPath }/board">
					<input type = "hidden" name = "a" value="reply">
					<input type = "hidden" name = "userNo" value="${sessionScope.authUser.no }">
					<input type = "hidden" name = "replyNo" value="${replyNo }">
					<input type = "hidden" name = "currentPage" value="${currentPage }">
					<input type = "hidden" name = "searchWord" value="${searchWord }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">답글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<!-- 답글 달기 취소 버튼 -->
						<!-- parameter : actrion = view, 답글 쓸려던 글 no, currentPage, searchWord -->
						<a href="${pageContext.request.contextPath }/board?a=view&no=${replyNo}&currentPage=${currentPage}&searchWord=${searchWord}">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>