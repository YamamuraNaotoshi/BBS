<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>わったい菜掲示板</title>
<link href="./css/skeleton.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="main-contents">
		<%--ログイン画面 --%>
		<c:if test="${not empty loginUser }">
			<div class="header">
				<font size="10">わったい菜掲示板</font><br />
				<%--ログインユーザー完了 --%>
				<a href="message">新規投稿</a>
				<c:if
					test="${loginUser.branch_id == '1' && loginUser.position_id == '1'}">
					<a href="management">ユーザー管理</a>
				</c:if>
				<a href="logout" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
			</div>
			<%--ログインユーザーの表示 --%>
			<div class="profile">
				<div class="name">
					<c:out value="${loginUser.name }" />
					でログイン中です<br />
				</div>
			</div>

			<%--エラーメッセージ --%>
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="errorMessage">
						<li><c:out value="${errorMessage}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />

			<%--検索画面 --%>

			<div class="search">
				<form action="./" method="get" style="display: inline">
					<select name="category">
						<option value="">カテゴリ選択</option>
						<c:forEach items="${categorys }" var="category">
							<c:if test="${category.category == checkCategory }">
								<option selected value="${category.category}"><c:out
										value="${category.category}" /></option>
							</c:if>
							<c:if test="${category.category != checkCategory }">
								<option value="${category.category}"><c:out
										value="${category.category}" /></option>
							</c:if>
						</c:forEach>
					</select> <input type="date" class="date" value="${from_date }"
						name="from_date" />から <input type="date" class="date"
						value="${to_date}" name="to_date" />までを<br /> <input
						type="submit" value="検索" />
				</form>
				<form action="./" method="get" style="display: inline">
					<input type="submit" value="すべて表示">
				</form>
			</div>


			<%--投稿表示 --%>
			<div class="messages-area">
				<c:forEach items="${messages}" var="message">
					<div class="message">
						<div class="user_name">
							<span class="user_name"><font size="4"><c:out
										value="${message.user_name}" /></font></span>
						</div>
						<div class="date">
							<fmt:formatDate value="${message.created_at}"
								pattern="yyyy/MM/dd HH:mm:ss" />
						</div>
						<br />
						<div align="left">件名</div>
						<div class="subject">
							<c:out value="${message.subject}" />
						</div>
						<div align="left">カテゴリー</div>
						<div class="category">
							<c:out value="${message.category}" />
						</div>
						<div align="left">本文</div>
						<div class="body">
							<c:forEach var="str" items="${fn:split(message.body,'
')}">
								<c:out value="${str}" />
								<br>
							</c:forEach>
						</div>
					</div>
					<br />
					<%--投稿削除 --%>
					<div>
						<c:if
							test="${loginUser.position_id == 2 ||loginUser.id == message.user_id
							|| (loginUser.branch_id == message.branch_id &&loginUser.position_id ==3)}">
							<form action="deleteMessage" method="post">
								<input type="hidden" name="messagedelete" value="${message.id}">
								<input type="submit" value="削除"
									onclick="return confirm('投稿を削除しますか？')"
									style="color: black; background-color: #2C7CFF; border-color: black;" />
							</form>
						</c:if>
					</div>

					<%--コメント投稿 --%>
					<div class="main-comment">
						<form action="comment" method="post">
							<label for="body"></label><br />
							<textarea name="body" cols="40" rows="5" maxlength="500">${makeComment.body }</textarea>
							（500文字まで）<br />
							<button type="submit" name="messageid" value="${message.id}"
								style="color: black; background-color: #BAD3FF; border-color: black;">コメント</button>
							<br />
						</form>
					</div>

					<%--コメント表示 --%>
					<div class="comments-area">
						<c:forEach items="${comments}" var="comment">
							<c:if test="${message.id == comment.message_id}">
								<div class="comment">
									<div class="user_name">
										<span class="user_name">名称: <c:out
												value="${comment.user_name}" /></span>
									</div>
									<div class="date">
										<fmt:formatDate value="${comment.created_at}"
											pattern="yyyy/MM/dd HH:mm:ss" />
									</div>
									<div class="body">
										<c:forEach var="str" items="${fn:split(comment.body,'
')}">
											<c:out value="${str}" />
											<br>
										</c:forEach>
									</div>
								</div>
								<br />
								<%--コメント削除 --%>
								<c:if
									test="${loginUser.position_id == 2 ||loginUser.id == comment.user_id
										|| (loginUser.branch_id == comment.branch_id &&loginUser.position_id ==3)}">
									<form action="deleteComment" method="post">
										<input type="hidden" name="commentdelete"
											value="${comment.id}"> <input type="submit"
											value="削除" onclick="return confirm('コメントを削除しますか？')"
											style="color: black; background-color: #2C7CFF; border-color: black;" />
									</form>
								</c:if>
							</c:if>
						</c:forEach>
					</div>
					<br />

				</c:forEach>
			</div>
		</c:if>
	</div>



</body>
</html>