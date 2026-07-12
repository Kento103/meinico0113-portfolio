package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountDeletedList")
public class AccountDeletedListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // データベース処理などはエラーが起きる可能性があるため、try-catchで囲んで保護する
        try {
            AccountDAO dao = new AccountDAO();
            // 削除フラグ(is_deleted=1)のアカウントのみ取得
            List<Account> list = dao.findDeleted();

            request.setAttribute("deletedList", list);
            // 表示用のJSPへフォワード
            request.getRequestDispatcher("/adminAccountDeletedList.jsp").forward(request, response);

        } catch (Exception e) {
            // もしデータベース接続などでエラーが発生した場合は、原因をログに出力する
            e.printStackTrace();
        }
    }
}