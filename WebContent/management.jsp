<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
<link href="./css/skeleton.css" rel="stylesheet" type="text/css">
<link href="./css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="form-area">
		<div class="header">
			<font size="5">ユーザー管理</font><br /> <a href="signup">ユーザー新規登録</a> <a
				href="./">ホーム</a>
		</div>
	</div>
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


	<div class="management-area">
		<table class="type11">
			<thead>
				<tr>
					<th scope="cols">ログインID</th>
					<th scope="cols">名称</th>
					<th scope="cols">支店</th>
					<th scope="cols">部署・役職</th>
					<th scope="cols">復活・停止</th>
					<th scope="cols">編集</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><span class="login_id"><c:out
									value="${user.login_id }" /></span></td>
						<td><span class="name"><c:out value="${user.name }" /></span></td>
						<c:forEach items="${branchs}" var="branch">
							<c:if test="${branch.id == user.branch_id}">
								<td><span class="name"><c:out
											value="${branch.name }" /></span></td>
							</c:if>
						</c:forEach>
						<c:forEach items="${positions}" var="position">
							<c:if test="${position.id == user.position_id}">
								<td><span class="name"><c:out
											value="${position.name }" /></span></td>
							</c:if>
						</c:forEach>
						<td>
							<form class="workButton" action="isworking" method="post">
								<c:if test="${loginUser.id != user.id }">
									<c:choose>
										<c:when test="${user.is_working == '1'}">
											<input type="hidden" name="id" id="id" value="${ user.id }" />
											<input type="hidden" name="is_working" id="is_working"
												value="${ user.is_working }" />
											<input type="submit" class="button" value="停止"
												onclick="return confirm('アカウントを停止しますか？')"
												style="color: black; background-color: lightgreen; border-color: black;" />
										</c:when>
										<c:otherwise>
											<input type="hidden" name="id" id="id" value="${ user.id }" />
											<input type="hidden" name="is_working" id="is_working"
												value="${ user.is_working }" />
											<input type="submit" class="button" value="復活"
												onclick="return confirm('アカウントを復活しますか？')"
												style="color: black; background-color: yellow; border-color: black;" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</form>
						</td>
						<td><input type="button"
							onclick="location.href='settings?id=${user.id } '" value="編集"
							style="color: black; background-color: lightblue; border-color: black;" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>