<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 27.04.2015
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
</head>
<body>
<h1>Booking page!</h1>
<div align="center">
  <table><tr>
    <td>Date check in:</td>
    <td><input type="date" id="date_check_in" placeholder="mm/dd/yyyy"></td>
    <td>Date check out:</td>
    <td><input type="date" id="date_check_out" placeholder="mm/dd/yyyy"></td>
  </tr>
  <tr><td colspan="4"><div id="errors"></div> </td></tr></table>
</div>

<script type="text/javascript">

  $(document).ready(function() {
    $('#date_check_in, #date_check_out').change(function () {
      filter_by_dates();
    });

    function filter_by_dates() {
      var date1 = $('#date_check_in').val();
      var date2 = $('#date_check_out').val();
      if (date1 && date2 && date1 > date2) {
        put_error('Start date should be before end date');
        clean_table();
      } else {
        put_error('');
        if (date1 && date2) {
          $.ajax({
            type: "POST",
            url: "/booking",
            data: {
              date_check_in: date1,
              date_check_out: date2
            },
            success: function (data) {
              var categories = $.parseJSON(data);
              fill_table(categories);
            },
            error: function(data, status, er) {
              alert("error: "+data+" status: "+status+" er:"+er);
            }
          });
        }
      }
    }

    function put_error(message) {
      $('#errors').html(message);
    }

    function clean_table() {
      var table = $('#available_categories');
      table.html('');
    }

    function fill_table(categories) {
      clean_table();

      var table = $('#available_categories');

      var th = "<tr><th>Apartment type</th><th>Capacity (people)</th><th>Description</th><th>Price ($/24h)</th><th></th></tr>";

      if (categories.length > 0) {
        table.append(th);
        $.each(categories, function(i, category) {
          th = "<tr><td>" + category.type + "</td><td>" + category.capacity + "</td><td>" + category.description
          + "</td><td>" + category.price + "</td><td><input type='button' value='Book'></td></tr>";
          table.append(th);
        })
      }
    }
  })
</script>

<div align="center">
  <table id="available_categories">
  </table>
</div>

</body>
</html>
