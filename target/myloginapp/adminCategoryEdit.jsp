<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ編集</title>
</head>
<body>

<h2>カテゴリ編集</h2>

<form action="${pageContext.request.contextPath}/admin/categoryUpdate" method="post">
    <input type="hidden" name="id" value="${category.id}">
    <input type="text" name="name" value="${fn:escapeXml(category.name)}" maxlength="255">
    <input type="submit" value="更新">
</form>

</body>
</html>