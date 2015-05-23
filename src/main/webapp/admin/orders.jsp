<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dasha
  Date: 21.05.2015
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
    <title>Orders</title>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>

<div align="center">
    <table>
        <tr>
            <td>Date check in:</td>
            <td><input type="text" id="date_check_in" name="date_check_in" placeholder="mm/dd/yyyy"></td>
            <td>Date check out:</td>
            <td><input type="text" id="date_check_out" name="date_check_out" placeholder="mm/dd/yyyy"></td>
            <c:forEach var="status" items="${statuses}">
            <td>
                <input id="status${status.id}" type="checkbox" name="status" value="${status.id}">
                <label for="status${status.id}">${status.type}</label>
            </td>
            </c:forEach>
        <tr>
            <td colspan="4">
                <div id="errors" style="color: #ff0000;"></div>
            </td>
        </tr>
    </table>
</div>
<div align="center">
    <table id="appropriate_orders">
    </table>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        $("#date_check_in").datepicker({dateFormat: 'mm/dd/yy'});
        $("#date_check_out").datepicker({dateFormat: 'mm/dd/yy'});

        $('#date_check_in, #date_check_out').change(function () {
            filter_by_dates_and_statuses();
        });

        $("input[type='checkbox'][name='status']").click(function () {
            filter_by_dates_and_statuses();
        });

        function filter_by_dates_and_statuses() {
            var date1 = $('#date_check_in').val();
            var date2 = $('#date_check_out').val();
            var statuses = [];
            $("input:checkbox[name='status']:checked").each(function () {
                statuses.push($(this).val());
            });

            $.ajax({
                type: "POST",
                url: "/admin/orders",
                data: {
                    date_check_in: date1,
                    date_check_out: date2,
                    statuses: statuses
                },
                success: function (data) {
                    var response = $.parseJSON(data);
                    if (response.correct) {
                        fill_table(response.orders);
                    } else {
                        put_error(response.error);
                    }
                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                }
            });
        }

        function put_error(message) {
            $('#errors').html(message);
            clean_table();
        }

        function clean_table() {
            var table = $('#appropriate_orders');
            table.html('');
        }

        function fill_table(orders) {
            clean_table();
            put_error('');
            var table = $('#appropriate_orders');

            var th = '<tr><th class="date_check_in_out">Date check in</th><th class="date_check_in_out">Date check out</th>'
                    + '<th class="client">Client</th><th class="room">Room</th><th class="status">Status</th>'
                    + '<th class="price">Price ($/24h)</th><th class="more"></th></tr>';
            if (orders.length > 0) {
                table.append(th);
                $.each(orders, function (i, order) {
                    th = "<tr align='center'><td>" + order.dateCheckIn + "</td><td>" + order.dateCheckOut + "</td><td>"
                            + order.client.surname + ' ' + order.client.name + "</td><td>" + order.room.number + "</td><td>"
                            + order.status.type + "</td><td>" + order.totalPrice + "</td><td><a href='/admin/orders/"
                            + order.id + "'>More...</a></td></tr>";
                    table.append(th);
                });
            }
        }
    })
</script>

</body>
</html>
