/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(urlPatterns = {"/MakeTransaction"})
public class MakeTransaction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        String BankID=request.getSession().getAttribute("session_BankID").toString(); 
        String amount = request.getParameter("BTAmount");
        int x=Integer.parseInt(amount);
        String BTToAccount= request.getParameter("BTToAccount");
            int newBalance=0;
            int newBalance2=0;
            String url = "jdbc:mysql://localhost:3306/bankingsystem";
            String user = "root";
            String password = "mark25597929";          
            Connection Con = null;
            Statement Stmt = null;
            Con = DriverManager.getConnection(url, user, password);
            Stmt = Con.createStatement();
             Random rand = new Random();
             int ID = rand.nextInt( 99999999);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;
                date = new Date();
                formatter.format(date);
            ResultSet Rs = Stmt.executeQuery("SELECT * FROM bankaccount");
            if(BankID.equals(BTToAccount)){
                  out.println("<meta http-equiv='refresh' content='3;URL=ViewTransactions.jsp'>");//redirects after 3 seconds
                  out.println("You can't transfer to the same account and this transaction wont be done");  
            }
            else{
            while(Rs.next()){
                if(Rs.getString("BankAccountID").equals(BTToAccount)){                
                  newBalance=Rs.getInt("BACurrentBalance")+x;
                }
                if(Rs.getString("BankAccountID").equals(BankID) && x<= Rs.getInt("BACurrentBalance")){
                  newBalance2=Rs.getInt("BACurrentBalance")-x;
                }
            } 
              String Line="update bankaccount set BACurrentBalance = "+ newBalance+" Where BankAccountID= "+BTToAccount;
               int Rows = Stmt.executeUpdate(Line);  
               String Line2="update bankaccount set BACurrentBalance = "+ newBalance2+" Where BankAccountID= "+amount;
               int Rows2 = Stmt.executeUpdate(Line2);
               String line3 = "INSERT INTO banktransaction(BankTransactionID,BTCreationDate,BTAmount,BTFromAccount,BTToAccount) VALUES"
                        + "("+ "'" +
                        ID+ "',"+ "'" 
                        + formatter.format(date)+"',"+"'" 
                        + amount + "',"+ "'" 
                        + BankID+ "',"+ "'"
                        + BTToAccount+ "')";                     
                
                 int Rows3 = Stmt.executeUpdate(line3); 
                 out.println("<meta http-equiv='refresh' content='3;URL=ViewTransactions.jsp'>");//redirects after 3 seconds
                 out.println("The transaction is done");
      
            }      
              }
                }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MakeTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MakeTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
