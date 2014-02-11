<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>

<html>
  <body>

<%
String url = null;
if (SystemProperty.environment.value() ==
    SystemProperty.Environment.Value.Production) {
  // Load the class that provides the new "jdbc:google:mysql://" prefix.
  Class.forName("com.mysql.jdbc.GoogleDriver");
  url = "jdbc:google:mysql://joeltoby-guestbook:guestbook/AWReports?user=root";
} else {
  // Local MySQL instance to use during development.
  Class.forName("com.mysql.jdbc.Driver");
  url = "jdbc:mysql://127.0.0.1:3306/AWReports"; //?user=root";
}

Connection conn = DriverManager.getConnection(url, "reportuser", "SOME_PASSWORD");
ResultSet rs = conn.createStatement().executeQuery(
    "select count(*) from AW_ReportAccount");
%>

<table style="border: 1px solid black">
<tbody>
<tr>
<th width="35%" style="background-color: #CCFFCC; margin: 5px">Name</th>
<th style="background-color: #CCFFCC; margin: 5px">Message</th>
<th style="background-color: #CCFFCC; margin: 5px">ID</th>
</tr>

<%
while (rs.next()) {
    String accountName = rs.getString("ACCOUNT_DESCRIPTIVE_NAME");
    String rowId = rs.getString("ROW_ID");
 %>
<tr>
<td><%= accountName %></td>
<td><%= rowId %></td>
</tr>
<%
}
conn.close();
%>

</tbody>
</table>
<br />
No more messages!
<p><strong>Sign the guestbook!</strong></p>
<form action="/guestbook" method="get">
    <div>First Name: <input type="text" name="fname"></input></div>
    <!-- <div>Message:
    <br /><textarea name="content" rows="3" cols="60"></textarea>
    </div> -->
    <div><input type="submit" value="TEST" /></div>
    <input type="hidden" name="guestbookName" />
  </form>
  </body>
</html>