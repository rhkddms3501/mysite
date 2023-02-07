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
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>[${vo.no }]</td>
							<c:choose>
								<c:when test="${vo.depth == 0 }">
									<td style="text-align:left; padding-left:${vo.depth * 20}px">
										<a href="${pageContext.request.contextPath }/board/view?no=${vo.no}&offset=${offset}&currentPage=${currentPage}&searchWord=${searchWord}">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left; padding-left:${vo.depth * 20}px">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">
										<a href="${pageContext.request.contextPath }/board/view?no=${vo.no}&offset=${offset}&currentPage=${currentPage}&searchWord=${searchWord}">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${authUser.no eq vo.userNo }">
                      			  	<a href="${pageContext.request.contextPath }/board/delete?no=${vo.no}&offset=${currentPage}" class="del">
										<img src="${pageContext.request.contextPath }/assets/images/recycle.png">
									</a>
                    			</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="searchWord" value="${searchWord }">
					<input type="submit" value="찾기">
				</form>
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath }/board?offset=${offset-5}&searchWord=${searchWord}">◀</a></li>
						<c:forEach var="cnt" begin="0" end="4" step="1" varStatus="a">
							<c:if test="${offset + cnt <= maxPage}">
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
				<c:if test="${not empty sessionScope.authUser.no }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/write?currentPage=${currentPage}&searchWord=${searchWord}" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>