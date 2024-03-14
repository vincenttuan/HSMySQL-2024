<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>demo</title>
</head>
<body>
	<%
		String[] names = { "张三", "李四", "王五" };
		Arrays.stream(names).forEach(name -> System.out.println(name + "<br>"));
	%>
</body>
</html>