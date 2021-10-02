<%-- 
    Document   : Customerhome
    Created on : Dec 22, 2020, 8:54:54 PM
    Author     : mark ihab
--%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Home</title>
    </head>
    <body>
        <h1>Hello Dear Customer</h1>
         <%
            String CustomerID=request.getSession().getAttribute("session_CustomerID").toString();
            String CustomerPass=request.getSession().getAttribute("sesssion_Pass").toString();
            String url = "jdbc:mysql://localhost:3306/bankingsystem";
            String user = "root";
            String password = "mark25597929";
            Connection Con = null;
            Statement Stmt = null;
            ResultSet Rs = null;

            
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();             
                Rs = Stmt.executeQuery("SELECT * FROM bankaccount;");
                 ArrayList<String> bankID=new ArrayList<>();
                 ArrayList<String> balance=new ArrayList<>();
                 boolean valid=false;
                  while(Rs.next()){
                      if(Rs.getString("CustomerID").equals(CustomerID)){
                         bankID.add(Rs.getString("BankAccountID"));
                         balance.add(Rs.getString("BACurrentBalance"));
                          HttpSession S=request.getSession(true);
                          S.setAttribute("session_BankID",Rs.getString("BankAccountID"));                         
                          valid=true;
                          break;
                      }                  
                  }
                  if(!valid){
                      %>
                      
                      <form  action = "addaccount">
                                  
                          <input type="submit" value="Create account" />                               
                                  </form>
                      
                  <%
                      response.sendRedirect("addaccount");
                  }                                  
                  else{
                 
                  %>
                  <form action="transactions.jsp">

                          <table border="1">
            <tr>
                <th>Bank Account ID</th> 
                <th> Bank Account Balance</th>               
            </tr>
            
            <tr>
                <td><%=Rs.getString("BankAccountID")%></td>
                <td><%=Rs.getString("BACurrentBalance")%></td>            
            </tr>s
            <tr>
                <td> <input type="submit" value="Go to Transactions" /> </td>
            </tr>
                        </table>
                 </form>
                  <%
                  }             
                  %>  
                  <br>
                  <br>
                  <form action="index.html">
                      <input type="submit" value="Logout" />                          
                 </form>
    </body>
</html>
