package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.CategoryDAO;

@WebServlet("/admin/categoryDelete")
public class CategoryDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            CategoryDAO dao = new CategoryDAO();
            dao.delete(id);

            response.sendRedirect(request.getContextPath() + "/admin/categoryList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}