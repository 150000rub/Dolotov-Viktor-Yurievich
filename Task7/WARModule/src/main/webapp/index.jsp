<%@ page import="com.exigenservices.lectures.util.ViewHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Message Page</title>
    </head>

    <body>
        <h1>Enter message</h1>
        <form action="send" method="post">
            <input type="text" name="msg"/>
            <input type="submit"/>
        </form>

        <h1>Messages</h1>
        <p><%= ViewHelper.getValuesAsString() %></p>
        <a href="javascript:location.reload(true)">Refresh</a>
    </body>
</html>
