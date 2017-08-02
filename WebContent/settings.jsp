<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
<link href="./css/skeleton.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="main-contents">
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

		<form action="settings" method="post">
			<br /> <label for="login_id">ログインID(半角英数字 6文字以上20文字以下)</label> <input
				name="login_id" size="25" value="${editUser.login_id }"
				id="login_id" /> <br /> <label for="name">名称(10文字以下)</label> <input
				name="name" size="25" value="${editUser.name }" id="name" /> <br />
			<label for="password">パスワード(半角文字 6文字以上20文字以下)</label> <input
				name="password" size="23" type="password" id="password" /> <br /> <label
				for="password2">パスワード(確認用)</label> <input name="password2" size="23"
				type="password" id="password2" /> <br />
			<c:if test="${editUser.position_id == 1}">
				<label for="branch_id">支店</label>
				<select name="branch_id">
					<c:forEach items="${branchs}" var="branch">
						<c:if test="${editUser.branch_id == branch.id }">
							<option selected value="${branch.id}">${branch.name }</option>
						</c:if>
					</c:forEach>
				</select>
				<br />

				<label for="position_id">部署・役職</label>
				<select name="position_id">
					<c:forEach items="${positions}" var="position">
						<c:if test="${editUser.position_id == position.id }">
							<option selected value="${position.id}">${position.name }
							</option>
						</c:if>
					</c:forEach>
				</select>
				<br />
			</c:if>

			<c:if test="${editUser.position_id != 1}">
				<label for="branch_id">支店</label>
				<select name="branch_id">
					<c:forEach items="${branchs}" var="branch">
						<c:if test="${editUser.branch_id == branch.id }">
							<option selected value="${branch.id}">${branch.name }</option>
						</c:if>
						<c:if test="${editUser.branch_id != branch.id }">
							<option value="${branch.id}">${branch.name }</option>
						</c:if>
					</c:forEach>
				</select>
				<br />

				<label for="position_id">部署・役職</label>
				<select name="position_id">
					<c:forEach items="${positions}" var="position">
						<c:if test="${editUser.position_id == position.id }">
							<option selected value="${position.id}">${position.name }
							</option>
						</c:if>
						<c:if test="${editUser.position_id != position.id }">
							<option value="${position.id}">${position.name }</option>
						</c:if>
					</c:forEach>
				</select>
				<br />
			</c:if>
			<button type="submit" name="editUserId" value="${editUser.id }"
				onclick="return confirm('変更しますか？')">変更</button>
			<br /> <a href="management">キャンセル</a>
		</form>
	</div>

</body>
</html>