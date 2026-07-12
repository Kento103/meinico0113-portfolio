package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountStatusToggle")
public class AccountStatusToggleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 画面から送られてきた「id」という名前の文字列を取得し、Javaの数値（int型）に変換
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();

            // ① 今のデータ取得
            Account account = dao.findById(id);

            // ② ステータス反転
            // 今のステータスが 1（有効）なら → 0（無効）にする、そうでなければ → 1（有効）にする
            int newStatus = (account.getStatus() == 1) ? 0 : 1;

            // ③ 更新
            // 対象の id に対して、新しく計算したステータスを渡して、データベースを更新する
            dao.updateStatus(id, newStatus);

            // ④ 一覧へ
            // 処理が終わったら、ユーザーをアカウント一覧画面へ自動的に転送する
            // これにより、画面がパッと切り替わり、ステータスの表示（有効 ⇔ 無効）が変わったように見える
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
