<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="tag" %>

<html>
    <head>
        <title>Ruletka page</title>
    </head>

    <body>

        <h1 style="color:red;">WELCOME TO RUSSIAN RULETKA</h1>

        <div>
            <p> Enter a number from 1 to 100:</p>
        </div>

        <div>
            <form action="start.jsp">
                <p><input type="text" name="number" autocomplete="off"></p>
                <p><input type="submit" name="Try" value="Try"></p>
            </form>
            <% if (request.getParameter("number")!=null) {
                request.getSession().setAttribute("number", request.getParameter("number")); %>
                <tag:RuletkaTag/>
             <% } %>
        </div>
        <c:if test="${not empty requestScope.error}">
            <div style="color:yellow;" class="error">
                ${requestScope.error}
            </div>
        </c:if>
    </body>
</html>