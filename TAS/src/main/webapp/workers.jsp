<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Workers</title>
    <link rel="stylesheet" href="/css/all_style.css">
</head>

<body>
<div style="text-align: right; width: 100%; padding-top: 5%">
    <form>
        <button class="button" formaction="/ManagerServlet">Back</button>
        <input type="hidden" name="targetpage" value="/manager.jsp"/>
    </form>
</div>
<div style="text-align: center; padding-top: 0%; width: 100%;">
    <div style="display: inline-block">
        <table border="1" id="accounttable" class="main-table">
            <caption>Active workers</caption>
            <tr>
                <th>Worker</th>
                <th>Task status</th>
                <th>Task</th>
            </tr>
            <c:forEach items="${workers}" var="item">
                <tr>
                    <td>${item.login}</td>
                    <td>${item.taskname}</td>
                    <td>${item.taskstatus}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>