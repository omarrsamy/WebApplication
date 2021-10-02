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
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mark ihab
 */
@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/bankingsystem";
                String user = "root";
                String password = "mark25597929";
                Connection Con = null;
                Statement Stmt = null;
                
                  out.println("hi");
                  Con = DriverManager.getConnection(url, user, password);
                  out.println("hi");
                  Stmt = Con.createStatement();
                  String Customerid = request.getParameter("CustomerID");
                  String Pass = request.getParameter("Password");
                  ResultSet Rs=null;
                  
                  Rs=Stmt.executeQuery("Select * From customer");
                  ArrayList<String> dataID=new ArrayList<>();
                   ArrayList<String> dataPass=new ArrayList<>();
                  while(Rs.next()){
                      dataID.add(Rs.getString("CustomerID"));
                      dataPass.add(Rs.getString("Password"));
                  }

              //  String line = "INSERT INTO project(pname,pnumber,plocation,dnum) VALUES("
                     //   + "'" + Customerid + "',"
                     //   + "'" + Pass +  "')";
                //int Rows = Stmt.executeUpdate(line);
                
                if(dataID.contains(Customerid)&&dataPass.contains(Pass)){
                  HttpSession S=request.getSession(true);
                  S.setAttribute("session_CustomerID", Customerid);
                  S.setAttribute("sesssion_Pass",Pass);
                  response.sendRedirect("Customerhome.jsp");          
                }
                else{
                response.sendRedirect("index.html");
                }
                out.println("hi");
           
            
            } catch (Exception ex) {
                ex.printStackTrace();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
