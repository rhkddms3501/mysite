<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

// 화면에 렌더링 할 것
var render = function(vo, mode) {
	console.log("랜더 속입니다.");
	var htmls = 
		"<li data-no='" + vo.no + "'>" +
		"	<strong>" + vo.name + "</strong>" +
		"	<p>" + vo.message + "</p>" +
		"	<strong></strong>" +
		"	<a id='delbtn' href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
	
		$("#list-guestbook")[mode? "prepend" : "append"](htmls);
}


// 처음에 리스트 가져올 것
var fetch = function() {
	console.log("fetch 속입니다...");
	$.ajax({
		url: "${pageContext.request.contextPath}/guestbook/api?sno=0",
		type: "get",
		dataType: "json",
		success: function(response) { 
			if(response.result === 'fail') {
				console.error(response.message);
				return;
			}
			
			console.log(response.data)
			console.log("sdfsf", this);
			response.data.forEach(function(vo){
			render(vo);
			});
		}
	});	
}

$(function() {
	
	<!-- 게시글 등록  -->
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		// form serialization
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		
		/* validation & messagebox */
		
		$.ajax({
			url: "${pageContext.request.contextPath}/guestbook/api",
			type: "post",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(vo),
			success: function(response) { 
				if(response.result === 'fail') {
					console.error(response.message);
					return;
				}
				console.log(response.data);
				render(response.data, true);
			}
		});
	});
	
	
// 삭제 다이알로그 jQuery 객체 미리 만들기
	var $dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				console.log($("#password-delete").val());
				
				var vo = {};
				vo.no = $("#hidden-no").val();
				vo.password = $("#password-delete").val();
				
				console.log(vo);
				
				$.ajax({
					url: "${pageContext.request.contextPath}/guestbook/api",
					type: "delete",
					dataType: "json",
					contentType: "application/json",
					data: JSON.stringify(vo),
					success: function(response) { 
						if(response.result === 'fail') {
							console.error(response.message);
							return;
						}
						console.log("ajax 통신 후");
						console.log(">>>>>>", response.data);
						
						test();
						test();
						
						$("li[data-no=" + vo.no + "]").remove();
						
						console.log($("#password-delete").val());
						$("#password-delete").val("");
						$("#dialog-delete-form").dialog('close');
						
					}
				});
			},
			"취소": function() {
				console.log("삭제 다이알로그의 폼 데이터 리셋하기");
				console.log($("#password-delete").val());
				$("#password-delete").val("");
				$(this).dialog('close');
				console.log("취소의 this 확인 >> ", this)
			}
		}
	});
	
	
	
	// 메세지 삭제 버튼 click 이벤트 처리(Live Event)
	$(document).on('click', "#list-guestbook li #delbtn", function(event){
		event.preventDefault();
		
		$("#hidden-no").val($(this).data("no"));
		console.log("글자 나오나 안나오나");
		console.log($(this).attr("data-no"));
		console.log($(this).data("no"));
		$dialogDelete.dialog('open');
	});


	// 최초 리스트 가져오기
	fetch();
})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">

				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>