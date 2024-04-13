<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hello</title>
</head>
<body>
	<h1>Hello, Struts 2!</h1>
	<s:if test="hasActionMessages()">
        <div>
            <s:actionmessage/>
        </div>
    </s:if>
</body>
</html>