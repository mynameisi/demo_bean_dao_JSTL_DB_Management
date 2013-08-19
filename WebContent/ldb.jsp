<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 1.字符串变量JNDI 表示 DataSource 查询地址  -->
	<c:set var="JNDI" value="jdbc/testdb"></c:set>

	<!-- 2.用 JNDI 找到 DataSource, 然后通过指定的 SQL ，更新数据库，结果保存到 count 里 -->
	<!-- 2.1 通过 param 获得request里面的参数P-->
	<!-- 不能用requestScope, 因为它是用来获得Attribute的Map对象-->
	<!-- 参考：http://www.informit.com/articles/article.aspx?p=30946&seqNum=7】 -->
	<!-- 注意两个SQL:update标签之间除了SQL不能有任何东西，比如comment -->
	<sql:update dataSource="${JNDI}" var="count">
  		DELETE FROM user WHERE Id = ? <sql:param value="${param.deleteID}" />
	</sql:update>

	<c:out value="删除了${count}行数据"></c:out>

	<!-- 把查询的结果存储到Result里面 -->
	<sql:query dataSource="${JNDI}" var="result">
		SELECT * from user;
	</sql:query>

	<table border="1" width="100%">
		<tr>
			<!-- result.columnName 相当于 Java中的 result.getColumnNames() -->
			<!-- 用它来获得一个按照数据库顺序的列名数组 -->
			<!-- 参见: http://docs.oracle.com/cd/E17802_01/products/products/jsp/jstl/1.1/docs/api/javax/servlet/jsp/jstl/sql/Result.html#getColumnNames() -->
			<c:forEach var="col" items="${result.columnNames}">
				<th><c:out value="${col}" /></th>
			</c:forEach>
			<th></th>
		</tr>

		<!-- 用result.rows获得一个Map[], 每一行都是一个Map，包括所有的列名和值的对应 -->
		<!-- 如果你考虑要把result set的结果拷贝除了,jsp的做法可以参考 -->
		<c:forEach var="row" begin="0" items="${result.rows}">
			<tr>
				<c:forEach var="col" items="${result.columnNames}">
					<td><c:out value="${row[col]}" /></td>
				</c:forEach>
				<!-- 加一个删除行的机制 -->
				<td><a href="ldb.jsp?deleteID=${row['id']}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
	<a href="index.jsp">回到主页</a>
</body>
</html>