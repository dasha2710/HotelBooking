<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 27.04.2015
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<html>
<head>
  <title>Apartment booking</title>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
        type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<sec:authorize access="!hasRole('ADMIN')">
    <form:form action="/client/result" method="post">
        <div align="center">
            <table>
                <tr>
                    <td>Date check in:</td>
                    <td><input type="text" id="date_check_in" name="date_check_in" placeholder="mm/dd/yyyy"></td>
                    <td>Date check out:</td>
                    <td><input type="text" id="date_check_out" name="date_check_out" placeholder="mm/dd/yyyy"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div id="errors" style="color: #ff0000;"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div align="center">
            <input id="category_id" type="hidden" name="category_id">
            <table id="available_categories">
            </table>
        </div>
    </form:form>
</sec:authorize>


<sec:authorize access="hasRole('ADMIN')">
    <form:form action="/admin/result" method="post">
        <div align="center">
            <table>
                <tr>
                    <td>Select user</td>
                    <td>
                        <sec:authorize access="hasRole('ADMIN')">
                            <select name="client" size="1">
                                <c:forEach items="${clients}" var="cl">
                                    <option value="${cl}">${cl}</option>
                                </c:forEach>
                            </select>
                        </sec:authorize>
                    </td>
                </tr>
                <tr>
                    <td>Date check in:</td>
                    <td><input type="text" id="date_check_in" name="date_check_in" placeholder="mm/dd/yyyy"></td>
                    <td>Date check out:</td>
                    <td><input type="text" id="date_check_out" name="date_check_out" placeholder="mm/dd/yyyy"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div id="errors" style="color: #ff0000;"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div align="center">
            <input id="category_id" type="hidden" name="category_id">
            <table id="available_categories">
            </table>
        </div>
    </form:form>
</sec:authorize>

<script type="text/javascript">

    $(document).ready(function () {
        $("#date_check_in").datepicker({dateFormat: 'mm/dd/yy'});
        $("#date_check_out").datepicker({dateFormat: 'mm/dd/yy'});

        $('#date_check_in, #date_check_out').change(function () {
            filter_by_dates();
        });

        $('input[type=submit]').live('click', function () {
            var categoryId = $(this).data('category_id');
            $('#category_id').val(categoryId);
            $(this).parents('form').submit();
        })

        function filter_by_dates() {
            var date1 = $('#date_check_in').val();
            var date2 = $('#date_check_out').val();
            if (date1 && date2) {
                $.ajax({
                    type: "POST",
                    url: "/booking",
                    data: {
                        date_check_in: date1,
                        date_check_out: date2
                    },
                    success: function (data) {
                        var response = $.parseJSON(data);
                        if (response.correct) {
                            fill_table(response.categories);
                        } else {
                            put_error(response.error);
                        }
                    },
                    error: function (data, status, er) {
                        alert("error: " + data + " status: " + status + " er:" + er);
                    }
                });
            }
        }

        function put_error(message) {
            $('#errors').html(message);
            clean_table();
        }

        function clean_table() {
            var table = $('#available_categories');
            table.html('');
        }

        function fill_table(categories) {
            clean_table();
            put_error('');

            var table = $('#available_categories');

      var th = '<tr><th class="picture">Photo</th><th class="type">Apartment type</th><th class="capacity">Capacity (people)</th>'
              + '<th class="description">Description</th><th class="price">Price ($/24h)</th><th class="more"></th></tr>';
      if (categories.length > 0) {
        table.append(th);
        $.each(categories, function(i, category) {
          th = "<tr><td><img width='100%' src='" + category.mainPicture.path + "'/></td><td>" + category.type + "</td><td>" + category.capacity + "</td><td>" + category.description
          + "</td><td>" + category.price + "</td><td><input type='submit' data-category_id='" + category.id + "' value='Book'></td></tr>";
          table.append(th);
        })
      }
    }
  })

</script>

</body>
</html>
