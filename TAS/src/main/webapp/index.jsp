<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Authorization page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style_index.css"media="screen" type="text/css" />
    <link rel="icon" href="/css/icon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/css/icon.ico" type="image/x-icon"/>
</head>

<body>
<div id="login"; style= 'padding-top: 3%'>
    <form  action="${pageContext.request.contextPath}/auth/">
        <fieldset class="clearfix">
            <p><span class="fontawesome-user"></span><input class="textbox" placeholder="Your login ..." type="text" name="login"></p>
            <p><span class="fontawesome-lock"></span><input class="textbox" placeholder="Your password ..."type="password" name="password"></p>
            <p><input type="submit"  value="Log in"></p>
        </fieldset>
    </form>

    <c:if test="${not empty requestScope.error}">
        <div style="color:red;" class="error" align="center">
                ${requestScope.error}
        </div>
    </c:if>
</div>
</body>
</html>