<%--
  Created by IntelliJ IDEA.
  User: Ti_g_programmist(no)
  Date: 21.05.2017
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/css/all_style.css">
    <title>Worker</title>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(document).on('click', 'td.editable_textfield', function (e) {
                var t = e.target || e.srcElement;
                var elm_name = t.tagName.toLowerCase();
                if (elm_name == 'input') {
                    return false;
                }
                var val = $(this).html();
                var code = '<input type="text" id="edit" value="' + val + '" />';
                $(this).empty().append(code);
                $('#edit').focus();
                $('#edit').blur(function () {
                    var val = $(this).val();
                    $(this).parent().parent().addClass("tr_changed");
                    $(this).parent().parent().css('background-color', '#aa0000');
                    $(this).parent().empty().html(val);
                });
            });
            $(document).on('click', 'td.editable_combobox', function (e) {
                var t = e.target || e.srcElement;
                var elm_name = t.tagName.toLowerCase();
                if ((elm_name == 'input') || (elm_name == 'select')) {
                    return false;
                }
                var code =
                    '<select id="edit">' +
                    '   <option value="In progress">In progress</option>' +
                    '   <option value="Review">Review</option>' +
                    '</select>';
                $(this).empty().append(code);
                $('#edit').focus();
                $('#edit').blur(function () {
                    var val = $("#edit option:selected").text();
                    $(this).parent().parent().addClass("tr_changed");
                    $(this).parent().parent().css('background-color', '#aa0000');
                    $(this).parent().empty().html(val);
                });
            });
        });

        $(window).keydown(function (event) {
            if (event.keyCode == 13) {
                $('#edit').blur();
            }
        });

        function packData() {
            var table = document.getElementById('currenttasktable');
            var row = table.rows[1];
            var id = row.cells[0].innerText;
            var name = row.cells[1].innerText;
            var status = row.cells[2].innerText;
            var comment = row.cells[3].innerText;

            var data = {
                id: id,
                name: name,
                status: status,
                comment: comment
            };

            return data;
        }

        function saveData() {
            document.getElementById("query").value =
                JSON.stringify({
                    type: "update",
                    data: packData()
                });
        }
    </script>
</head>

<body>
<div style="text-align: right; padding-top: 1%">
    <div style="display: inline-block">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" class="button" value="Logout"/>
        </form>
    </div>
</div>
<c:choose>
    <c:when test="${currenttask != null}">
        <div style="text-align: center; padding-top: 0%; width: 100%;">
            <div style="display: inline-block">
                <table border="1" id="currenttasktable" class="main-table">
                    <caption>Current Task</caption>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Comment</th>
                    </tr>
                    <tr>
                        <td>
                                ${currenttask.id}
                        </td>
                        <td>
                                ${currenttask.name}
                        </td>
                        <td class="editable_combobox">
                                ${currenttask.status}
                        </td>
                        <td class="editable_textfield">${currenttask.comment}</td>
                    </tr>
                </table>
                <form>
                    <p>
                        <button class="button" id="savebutton" onclick="saveData()" formmethod="get"
                                formaction="/WorkerServlet">Save
                        </button>
                        <input type="hidden" name="query" id="query"/>
                    </p>
                </form>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <c:out value="You don't have any current tasks!"/>
    </c:otherwise>
</c:choose>
<div style="text-align: center; padding-top: 0%; width: 100%;">
    <div style="display: inline-block">
        <table border="1" class="main-table">
            <caption>Previous Tasks</caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Status</th>
                <th>Comment</th>
            </tr>
            <c:forEach items="${oldtasks}" var="item">
                <tr>
                    <td>
                            ${item.id}
                    </td>
                    <td>
                            ${item.name}
                    </td>
                    <td>
                            ${item.status}
                    </td>
                    <td>
                            ${item.comment}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>