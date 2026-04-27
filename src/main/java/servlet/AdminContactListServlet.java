package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.ContactDAO;
import model.Contact;

@WebServlet("/admin/contactList")
public class AdminContactListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ContactDAO dao = new ContactDAO();
            List<Contact> list = dao.findAll();

            request.setAttribute("contactList", list);

            request.getRequestDispatcher("/adminContactList.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
