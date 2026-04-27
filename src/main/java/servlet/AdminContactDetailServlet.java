package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ContactDAO;
import model.Contact;

@WebServlet("/admin/contactDetail")
public class AdminContactDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    try {
        int id = Integer.parseInt(request.getParameter("id"));

        ContactDAO dao = new ContactDAO();
        Contact contact = dao.findById(id);

        request.setAttribute("contact", contact);
        request.getRequestDispatcher("/adminContactDetail.jsp")
               .forward(request, response);
    
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
