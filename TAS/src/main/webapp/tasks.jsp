<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Tasks</title>
    <link rel="stylesheet" href="/css/all_style.css">
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
                if ((elm_name == 'input') || (elm_name == 'select') || (t.innerHTML === 'Finished')) {
                    return false;
                }
                var code =
                    '<select id="edit">' +
                    '<option value="Not started">Not started</option>' +
                    '<option value="In progress">In progress</option>' +
                    '<option value="Review">Review</option>' +
                    '<option value="Finished">Finished</option>' +
                    '</select>';
                $(this).empty().append(code);
                $('#edit').focus();
                $('#edit').blur(function () {
                    var val = $("#edit option:selected").text();
                    $(this).parent().parent().addClass("tr_changed");
                    if(val === 'Finished') {
                        $(this).parent().removeClass('editable_combobox');
                    }
                    $(this).parent().parent().css('background-color', '#aa0000');
                    $(this).parent().empty().html(val);
                });
            });
            $(document).on('click', 'td.editable_combobox_worker', function (e) {
                var t = e.target || e.srcElement;
                var elm_name = t.tagName.toLowerCase();
                if ((elm_name == 'input') || (elm_name == 'select')) {
                    return false;
                }
                var workers = buildWorkerList();
                var code = '<select id="edit">';
                for (var i = 0; i < workers.length; i++) {
                    code += '<option value="' + workers[i] + '">' + workers[i] + '</option>';
                }
                code += '</select>';
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

        function buildWorkerList() {
            var workers = new Array();
            <c:forEach items="${ourfreeworkers}" var="item">
            var x = '<c:out value="${item.login}"/>';
            workers.push(x);
            </c:forEach>
            <c:forEach items="${freeworkers}" var="item">
            var x = '<c:out value="${item.login}"/>';
            workers.push(x);
            </c:forEach>
            return workers;
        }

        function buildTable() {
            var table = document.getElementById('tasks');
            var array = [];
            for (var i = 1, row; row = table.rows[i]; i++) {
                if (row.className == "tr_changed") {
                    var id = row.cells[0].innerText;
                    var name = row.cells[1].innerText;
                    var comment = row.cells[2].innerText;
                    var status = row.cells[3].innerText;
                    var idWorker = row.cells[4].innerText;
                    array.push({
                        id: id,
                        name: name,
                        comment: comment,
                        status: status,
                        idWorker: idWorker
                    });
                }
            }
            return array;
        }

        function saveData() {
            var table = buildTable();
            var outval = {
                type: "update",
                data: table
            };
            document.getElementById("query").value = JSON.stringify(outval);
        }

        function newRecord() {
            var code = '<tr class="tr_changed">' +
                '<td></td>' +
                '<td class = "editable_textfield"></td>' +
                '<td class = "editable_textfield"></td>' +
                '<td class = "editable_combobox">Not started</td>' +
                '<td class = "editable_combobox_worker"></td>' +
                '</tr>';
            console.log(code);
            $("#tasks").append(code);
        }
    </script>
</head>

<body>
<div style="text-align: right; width: 100%; padding-top: 5%">
    <form action="index.html">
        <button formaction="/ManagerServlet" class="button">Back</button>
        <input type="hidden" name="targetpage" value="/manager.jsp"/>
    </form>
</div>
<div style="text-align: center; padding-top: 0%">
    <div style="display: inline-block">
        <table border="1" id="tasks" class="main-table" style="margin : 12px">
            <caption>Tasks</caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Comment</th>
                <th>Status</th>
                <th>Worker</th>
            </tr>
            <c:forEach items="${tasks}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td class="editable_textfield">${item.name}</td>
                    <td class="editable_textfield">${item.comment}</td>
                    <td class="editable_combobox">${item.status}</td>
                    <td class="editable_combobox_worker">${item.idWorker}</td>
                </tr>
            </c:forEach>
        </table>

        <div style="text-align: center; width: 105%;">
            <form>
                <p>
                    <button class="button" type="button" id="newrecord" onclick="newRecord()">New</button>
                    <button class="button" id="savebutton" onclick="saveData()" formmethod="get"
                            formaction="/ManagerServlet">Save
                    </button>
                    <input type="hidden" name="targetpage" value="/tasks.jsp"/>
                    <input type="hidden" name="query" id="query"/>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>