<%-- 
    Document   : transactions
    Created on : Dec 23, 2020, 7:05:26 PM
    Author     : mark ihab
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>transactions</title>
    </head>
    <body>
        <h1>Welcome to your Bank Account</h1>
        <form action="ViewTransactions.jsp">
            <input type="submit" value="ViewTransaction" />
        </form>
        <br>
        <br>
        <form action="MakeTransaction">
             <table border="1" cellpadding="1">
                <tr>
                    <td>Enter bank Transaction Amount</td>
                    <td><input type="text" name="BTAmount" value="" /></td>
                </tr>  
                <tr>
                    <td>Enter Bank Transaction To Account</td>
                    <td><input type="text" name="BTToAccount" value="" /></td>
                </tr>  
           </table>        
            <input type="submit" value="Make a Transaction" />
        </form>
        <br>
        <br>
        
    </body>
</html>
