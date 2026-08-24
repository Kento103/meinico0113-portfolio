package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountEdit")
public class AccountEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ取得
        int id = Integer.parseInt(request.getParameter("id"));

        // DBから1件取得
        AccountDAO dao = new AccountDAO();
        Account account = dao.findById(id);

        // JSPへ渡す
        request.setAttribute("account", account);

        // 画面へ
        request.getRequestDispatcher("/adminAccountEdit.jsp").forward(request, response);
    }
}