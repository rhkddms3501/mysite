<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Exception Occurs</h1>
	<h3>404,500페이지가 안뜨고 여기 페이지가 뜸.</h3>
	<p>404,500페이지는 리포지토리의 캐치, 엑셉션 폴더안에 있음</p>
	<p>이페이지는 GlobalException</p>
	<!-- pre테그 : 소스 모양 그대로 -->
	<pre style="color:red;">${exception }</pre>
</body>
</html>