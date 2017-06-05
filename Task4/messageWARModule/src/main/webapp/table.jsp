<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Table page</title>
    </head>

    <body>
        <div>
            <table>
                <tr>
                    <td>Symbol</td>
                    <td>Amount</td>
                </tr>
                <c:forEach var="symb" items="${sessionScope.table}">
                    <tr>
                        <td>${symb.key}</td>
                        <td>${symb.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <p> <a href="${pageContext.request.contextPath}">Again</a> </p>
    </body>
</html>