<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
<link href="./css/skeleton.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-contents">
		<font size="5">新規投稿</font><br />
		<br />
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		<div class="form-area">
			<form action="message" method="post">
				<label for="subject">件名(30文字以下)</label> <input type="text"
					name="subject" size="50" value="${makeMessage.subject }"
					class="subject-box"> <br /> <label for="category">カテゴリー(10文字以下)</label>
				<input type="text" name="category" size="50"
					value="${makeMessage.category }" class="category-box"> <br />
				<label for="body">本文(1000文字まで)</label>
				<textarea name="body" cols="50" rows="10" class="body-box"
					style="height: 300px; width: 402px;">${makeMessage.body }</textarea>
				<br /> <input type="submit" value="投稿"> <br /> <a
					href="./">キャンセル</a>
			</form>
		</div>
	</div>

</body>
</html>