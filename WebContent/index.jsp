<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<strong><c:out value="${requestScope.msg}"></c:out></strong>
	<form action="RegisterServlet" method="post">
		<fieldset>
			<legend>注册</legend>
			密码:
			<input type="password" name="password" value="admin2" />
			电子邮箱
			<input type="text" name="email" value="testmailclient@126.com" />
			<button type="submit">注册</button>
		</fieldset>
		<a href="ldb.jsp">查看数据库内容</a>
	</form>
</body>
</html>