package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;

@WebServlet("/admin/accountDelete")
public class AccountDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // id取得
            int id = Integer.parseInt(request.getParameter("id"));

            // DAOで削除
            AccountDAO dao = new AccountDAO();
            dao.delete(id);

            // 一覧に戻る
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}