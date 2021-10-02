/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mark ihab
 */
@WebServlet(urlPatterns = {"/CancelTransaction"})
public class CancelTransaction extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String url = "jdbc:mysql://localhost:3306/bankingsystem";
            String user = "root";
            String password = "mark25597929";          
            Connection Con = null;
            Statement Stmt = null;
            Con = DriverManager.getConnection(url, user, password);
            Stmt = Con.createStatement();
            String BankID=request.getSession().getAttribute("session_BankID").toString();
            String BankTransactionID=request.getParameter("BTID");
            int newBalance=0;
            int newBalance2=0;
            int Amount=0;
            int BTTo=0;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date Date1 = new Date();
            Date Date2 = null;
            ResultSet Rs = Stmt.executeQuery("SELECT * FROM bankTransaction WHERE BankTransactionID= "+BankTransactionID);
            boolean CanCancel=false;
             while(Rs.next()){
                 
                Date2=formatter.parse(Rs.getNString("BTCreationDate"));
                int Diff=(int)((Date1.getTime()-Date2.getTime())/(1000*60*60*24));
                 if((Rs.getString("BTFromAccount").equals(BankID)))
                 {
                     if(Diff<=1)
                     {  
                         
                        CanCancel=true;
                        BTTo=Rs.getInt("BTToAccount");
                        Amount=Rs.getInt("BTAmount");
  
                     }
                     else 
                     {
                         out.print("More than 1 Day is Passed");
                     }
                    
                 }
                 if(!(Rs.getString("BTFromAccount").equals(BankID)))
                 {
                     out.print("You Can't Cancel thistransaction unless you are the sender");
                 }
                
             }
                 
             if(CanCancel){
             ResultSet Rs2= Stmt.executeQuery("SELECT * FROM bankaccount WHERE BankAccountID= "+BankID);
             while(Rs2.next()){
                           
                           newBalance=Rs2.getInt("BACurrentBalance")+Amount;
                          
          
              }
             ResultSet Rs3= Stmt.executeQuery("SELECT * FROM bankaccount WHERE BankAccountID= "+BTTo);
             while(Rs3.next()){
                           
                         newBalance2=Rs3.getInt("BACurrentBalance")-Amount;                         
                        
                    
              }
                        String Line="update bankaccount set BACurrentBalance = "+ newBalance+" Where BankAccountID= "+BankID;
                        int Rows = Stmt.executeUpdate(Line);  
                        String Line2="update bankaccount set BACurrentBalance = "+ newBalance2+" Where BankAccountID= "+BTTo;
                        int Rows2 = Stmt.executeUpdate(Line2);
                        String Line3="DELETE FROM banktransaction WHERE BankTransactionID= "+BankTransactionID;
                        int Rows3 = Stmt.executeUpdate(Line3);
                        
          
             }                         
               response.sendRedirect("ViewTransactions.jsp");          
            //int daydiff=SELECT * DATEDIFF('2019-10-10', '2019-02-15');      
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
            Logger.getLogger(CancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
