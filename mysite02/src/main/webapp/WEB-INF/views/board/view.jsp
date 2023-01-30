<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
			<div id="board" class="board-form">
				<!-- 게시글 출력 -->
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${boardVo.contents }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
				<!-- 이전 목록 페이지로 이동 -->
				<!-- parameter : offset, searchWord -->
					<a href="${pageContext.request.contextPath }/board?offset=${currentPage }&searchWord=${searchWord}">글목록</a>
						<!-- 글작성자가 로그인 한 유저 일 때 현재 글 수정 버튼 보이기  -->
						<!-- parameter : action = modifyform, 게시글no, currentPage, searchWord -->
					<c:if test="${sessionScope.authUser.no == boardVo.userNo}">
						<a href="${pageContext.request.contextPath }/board?a=modifyform&no=${boardVo.no}&currentPage=${currentPage}&searchWord=${searchWord}">글수정</a>
					</c:if>
					<!-- 로그인한 상태 일 때 답글 쓰기 버튼 보이기-->
					<!-- parameter : action = replyform, 게시글no, currentPage, searchWord -->
					<c:if test="${not empty sessionScope.authUser.no }">
						<a href="${pageContext.request.contextPath }/board?a=replyform&borderNo=${boardVo.no}&currentPage=${currentPage}&searchWord=${searchWord}" id="new-book">답글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>