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
			<div id="board">
				<!-- 게시글 리스트 출력 -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<!-- 10개 게시글 출력 -->
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>[${vo.no }]</td>
							<!-- vo.depth를 통해 답글인지 아닌지 여부 확인 및 padding처리 -->
							<!-- parameter :  action = view, 게시글no, offset, currentPage, searchWord -->
							<c:choose>
								<c:when test="${vo.depth == 0 }">
									<td style="text-align:left; padding-left:${vo.depth * 20}px">
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}&offset=${offset}&currentPage=${currentPage}&searchWord=${searchWord}">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left; padding-left:${vo.depth * 20}px">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}&offset=${offset}&currentPage=${currentPage}&searchWord=${searchWord}">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<!-- 로그인 유저가 글 작성자인 경우에만 삭제 버튼 보이기 -->
								<!-- parameter : action = delete, 게시물no, currentPage -->
								<c:if test="${sessionScope.authUser.no == vo.userNo}">
									<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}&offset=${currentPage}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- 게시글 검색 -->
				<!-- parameter :  action = search, searchWord -->			
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="hidden" name="a" value="search"> <input
						type="text" id="kwd" name="searchWord" value="${searchWord }">
					<input type="submit" value="찾기">
				</form>
				
				<!-- 페이지 -->
				<!-- ◀ 클릭시 현재 페이지 -5페이지의 페이지범위에서 첫번째 페이지로 -->
				<!-- ▶ 클릭시 현재 페이지 -5페이지의 페이지범위에서 첫번째 페이지로 -->
				<!-- 페이지 숫자 클릭 시 해당 페이지로 -->
				<!-- parameter :  offset, searchWord -->
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath }/board?offset=${offset-5}&searchWord=${searchWord}">◀</a></li>
						<!-- 5개의 페이지씩 출력 -->
						<c:forEach var="cnt" begin="0" end="4" step="1" varStatus="a">
							<!-- 최대로 갈 수 있는 페이지까지만 나오도록 범위 제한 -->
							<c:if test="${offset + cnt <= maxPage}">
								<!-- 현재 페이지에 slected 효과 -->
								<c:choose>
									<c:when test="${offset + cnt == currentPage}">
										<li class="selected"><a href="${pageContext.request.contextPath }/board?offset=${offset + cnt }&searchWord=${searchWord}">${offset + cnt }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/board?offset=${offset + cnt }&searchWord=${searchWord}">${offset + cnt }</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath }/board?offset=${offset+5}&searchWord=${searchWord}">▶</a></li>
					</ul>
				</div>
				
				<!-- 글 쓰기 -->
				<!-- 로그인한 유저에게만 글쓰기 버튼 보이기 -->
				<!-- parameter :  action = writeform, currentPage, searchWord  -->
				<c:if test="${not empty sessionScope.authUser.no }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=writeform&currentPage=${currentPage}&searchWord=${searchWord}" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>