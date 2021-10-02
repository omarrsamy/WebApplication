<%-- 
    Document   : ViewTransactions
    Created on : Dec 23, 2020, 4:32:55 PM
    Author     : mark ihab
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Transactions</title>
    </head>
    <body>
        <h1>Your Transactions</h1>
        <%
            String BankID=request.getSession().getAttribute("session_BankID").toString();
            String url = "jdbc:mysql://localhost:3306/bankingsystem";
            String user = "root";
            String password = "mark25597929";
            Connection Con = null;
            Statement Stmt = null;
            ResultSet Rs = null;
            
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();             
               Rs = Stmt.executeQuery("SELECT * FROM banktransaction WHERE BTFromAccount ="+ 
                        BankID + " OR BTToAccount="+ BankID);
        %>
         <table border="1">
            <tr>
                <th>Bank Transaction ID</th> 
                <th>Bank Transaction Date</th> 
                <th>Bank Transaction Amount</th> 
                <th>Bank Transaction From Account</th> 
                <th>Bank Transaction To Account</th> 
            </tr>
            <%
                while (Rs.next()) {%>
            <tr>
                <td><%=Rs.getString("BankTransactionID")%></td>
                <td><%=Rs.getString("BTCreationDate")%></td>
                <td><%=Rs.getString("BTAmount")%></td>
                <td><%=Rs.getString("BTFromAccount")%></td>
                <td><%=Rs.getString("BTToAccount")%></td>
            </tr>         
            <%}%>
        </table>
        <br>
        <br>
        <form action="CancelTransaction">
              <table border="1" cellpadding="1">
                <tr>
                    <td>Enter bank Transaction ID</td>
                    <td><input type="text" name="BTID" value="" /></td>
                </tr>
                 <tr>
                     <td><input type="submit" value="Cancel a Transaction" /></td>
                 </tr>
              </table>
        </form>
        <br>
        <br>
        <form action="transactions.jsp">
              <table border="1" cellpadding="1">
                 <tr>
                     <td><input type="submit" value="Make a new transaction" /></td>
                 </tr>
              </table>
        </form>
    </body>
</html>
