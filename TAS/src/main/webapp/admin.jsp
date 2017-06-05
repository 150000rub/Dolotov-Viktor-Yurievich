<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Administrator</title>
    <link rel="stylesheet" href="/css/all_style.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $(document).on('click', 'td.editable_textfield', function(e) {
                var t = e.target || e.srcElement;
                var elm_name = t.tagName.toLowerCase();
                if(elm_name == 'input') {return false;}
                var val = $(this).html();
                var code = '<input type="text" id="edit" value="'+val+'" />';
                $(this).empty().append(code);
                $('#edit').focus();
                $('#edit').blur(function() {
                    var val = $(this).val();
                    $(this).parent().parent().addClass("tr_changed");
                    $(this).parent().parent().css('background-color', '#aa0000');
                    $(this).parent().empty().html(val);
                });
            });
            $(document).on('click', 'td.editable_combobox', function(e) {
                var t = e.target || e.srcElement;
                var elm_name = t.tagName.toLowerCase();
                if((elm_name == 'input') || (elm_name == 'select')) {
                    return false;
                }
                var code =
                    '<select id="edit">' +
                    //'   <option value="Admin">Admin</option>' +
                    '   <option value="Manager">Manager</option>' +
                    '   <option value="Worker">Worker</option>' +
                    '</select>';
                $(this).empty().append(code);
                $('#edit').focus();
                $('#edit').blur(function() {
                    var val = $("#edit option:selected").text();
                    $(this).parent().parent().addClass("tr_changed");
                    $(this).parent().parent().css('background-color', '#aa0000');
                    $(this).parent().empty().html(val);
                });
            });
        });

        $(window).keydown(function(event){
            if(event.keyCode == 13) {
                $('#edit').blur();
            } });

        function buildTable() {
            var table =  document.getElementById('accounttable');
            var array = [];
            for (var i = 1, row; row = table.rows[i]; i++) {
                if(row.className == "tr_changed") {
                    var id = row.cells[0].innerText;
                    var name = row.cells[1].innerText;
                    var password = row.cells[2].innerText;
                    var status = row.cells[3].innerText;
                    array.push({
                        id: id,
                        name: name,
                        password: password,
                        status: status
                    });
                }
            }
            return array;
        }

        function saveData() {
            document.getElementById("query").value =
                JSON.stringify({
                    type: "update",
                    data: buildTable()
                });
        }

        function newRecord() {
            var code = '<tr class="tr_changed">' +
                '<td></td>' +
                '<td class = "editable_textfield">username</td>' +
                '<td class = "editable_textfield">password</td>' +
                '<td class = "editable_combobox">Worker</td>' +
                '</tr>';
            console.log(code);
            $("#accounttable").append(code);
        }
    </script>
</head>

<body>

<div style="text-align: right; width: 100%; padding-top: 5%">
    <form action="${pageContext.request.contextPath}logout" method="post">
        <input class="button" type="submit" value="Logout" />
    </form>
</div>

<div style="text-align: center; padding-top: 1%">
    <div style="display: inline-block">
        <table border="1" id="accounttable" class="main-table">
            <caption>Users</caption>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Password</th>
                <th>Status</th>
            </tr>
            <c:forEach items="${users}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <c:choose>
                        <c:when test="${item.id == 1}">
                            <td>${item.login}</td>
                            <td class = "editable_textfield">${item.password}</td>
                            <td>${item.status}</td>
                        </c:when>
                        <c:otherwise>
                            <td class = "editable_textfield">${item.login}</td>
                            <td class = "editable_textfield">${item.password}</td>
                            <td class = "editable_combobox">${item.status}</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <div style="text-align: center; width: 105%; padding-top: 2%">
            <form>
                <p>
                    <button class="button" type="button" id="newrecord" onclick="newRecord()">New</button>
                    <button class="button" id="savebutton" onclick="saveData()" formmethod="get" formaction="/AdminServlet">Save</button>
                    <input type="hidden" name="query" id="query"/>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>