package com.yaoxinyuan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TranslateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException {
        String English=request.getParameter("English");
        String Chinese;
        try (Connection c=DBUtil.getConnection()){
            String sql="select Chinese from dictionary where English=?";
            try (PreparedStatement s=c.prepareStatement(sql)){
                s.setString(1,English);
                    try (ResultSet r=s.executeQuery()){
                        if (r.next()) {
                           Chinese=r.getString("Chinese");
                        }else {
                            Chinese="不认识的单词";
                        }
                    }
            }
        } catch (SQLException e) {
            throw  new  ServletException(e);
        }

        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer=response.getWriter();
        writer.println("<h1>翻译结果</h1>");
        writer.println("<p>"+English+"翻译结果是"+Chinese+"</p>");
    }

}
