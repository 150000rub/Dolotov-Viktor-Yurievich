<%--
  Created by IntelliJ IDEA.
  User: Ti_g_programmist(no)
  Date: 21.05.2017
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Manager</title>
    <link rel="stylesheet" href="/css/all_style.css">
</head>

<body class="gradient-pattern">
<div style="text-align: right; width: 100%; padding-top: 5%">
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input class="button" type="submit" value="Logout"/>
    </form>
</div>
<div style="text-align: center; width: 100%; padding-top: 3%">
    <form>
        <button class="button" formaction="/ManagerServlet">Tasks</button>
        <input type="hidden" name="targetpage" value="/tasks.jsp"/>
    </form>
</div>
<div style="text-align: center; width: 100%; padding-top: 0%">
    <form>
        <button class="button" formaction="/ManagerServlet">Active workers</button>
        <input type="hidden" name="targetpage" value="/workers.jsp"/>
    </form>
</div>
</body>
</html>