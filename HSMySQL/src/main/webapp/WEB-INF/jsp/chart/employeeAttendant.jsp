<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['出勤', '天數'],
          ['正常', ${ list[1].count }],
          ['異常', ${ list[0].count }]
        ]);

        var options = {
          title: '${ year } / ${ month }  ${ emp_name }(${ emp_no }) ：出勤狀況統計',
          is3D: true
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
        
        var chart2 = new google.visualization.ColumnChart(document.getElementById('piechart2'));
        chart2.draw(data, options);
      }
    </script>
  </head>
  <body>

      ${ list }
      ${ list[0].count }
      ${ list[1].count }

    <div id="piechart" style="width: 800px; height: 400px;"></div>
    <div id="piechart2" style="width: 800px; height: 400px;"></div>
  </body>
</html>

