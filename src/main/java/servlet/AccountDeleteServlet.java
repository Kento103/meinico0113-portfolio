package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountDelete")
public class AccountDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ① id取得
            int id = Integer.parseInt(request.getParameter("id"));

            // ② DAOで削除
            AccountDAO dao = new AccountDAO();
            dao.delete(id);

            // ③ 一覧に戻る
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}