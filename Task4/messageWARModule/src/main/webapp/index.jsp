<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Message page</title>
    </head>

    <body>
        <div align="center">
            <h1 style="color:green;">ENTER YOUR MESSAGE FOR ME</h1>

            <form action="${pageContext.request.contextPath}/messageServlet/" method="POST">
                <p><input type="text" name="text" autocomplete="off"></p>
                <p><input type="submit" name="Submit"></p>
            </form>
        </div>

        <c:if test="${not empty requestScope.error}">
            <div style="color:red;" class="error" align="center">
                ${requestScope.error}
            </div>
        </c:if>
    </body>
</html>