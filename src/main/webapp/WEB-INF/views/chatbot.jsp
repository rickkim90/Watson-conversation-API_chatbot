<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/bubbles.css" />">
<script src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico"/>
<script type="text/javascript">
$(document).ready(function(){
	ajax_process("");
	
	$('#txt_isay').keypress(function(e){
		if (e.which == 13){
			conversation();
		}
	});
	
	$('#btn_isay').click(function(){
		conversation();
	});
});

function conversation() {
	var _isay = $.trim($('#txt_isay').val());

	var isay = '<p class="triangle-right right">' + _isay + '</p>';
	$('#said').append(isay);
	$('#txt_isay').val('');
	ajax_process(_isay);

}

function ajax_process(_isay){
	$.ajax({
		type : 'POST',
		url : 'watsonsay',
		data : 'isay=' + encodeURIComponent(_isay),
		async : false,
		success : function(data) {
			console.log(data);
			var watsonsay = 
				'<p class="triangle-right left">' + data.output.text;
			if (_isay ==='') {
				watsonsay += '<br/><img src="<c:url value="/resources/img/1.png" />" width="730" />';
				watsonsay += '<br/><img src="<c:url value="/resources/img/2.png" />" width="730" />';
				watsonsay += '<br/><img src="<c:url value="/resources/img/3.png" />" width="730" />';
			}
			
			if (data.context.image!=='') {
		            watsonsay += '<br/><img src="resources/img/'+data.context.image+'.jpg" width="700" />';
		    }
			
			watsonsay += '</p>';
			$('#said').append(watsonsay);
			$('html, body').animate({scrollTop: $(document).height()}, 500);
		}
	});
}
</script>
</head>
<body>
<div id="said"></div>
<input type="text" id="txt_isay" class="triangle-border center" autofocus="autofocus" />
<button type="button" id="btn_isay" class="btn btn-default">전송</button>
</body>
</html>