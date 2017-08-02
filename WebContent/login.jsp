<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<link href="./css/skeleton.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-contents">
		<font size="10">わったい菜掲示板</font><br /> <font size="5">ログイン</font><br />
		<div class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages }" var="message">
					<li><c:out value="${message }" />
				</c:forEach>
			</ul>
		</div>
		<div class="login-area">
			<c:remove var="errorMessages" scope="session" />
			<form action="login" method="post">
				<br /> <label for="login_id">ログインID</label> <input name="login_id"
					size="25" value="${login_id }" id="login_id" /><br /> <label
					for="password">パスワード</label> <input name="password" size="25"
					type="password" id="password" /><br /> <input type="submit"
					value="ログイン" /><br />
			</form>
		</div>
	</div>

</body>
</html>