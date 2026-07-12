package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CategoryDAO;

@WebServlet("/admin/categoryDelete")
public class CategoryDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ①パラメーターの取得
            // URLの末尾（例：?id=5）から、削除したいカテゴリのID番号を文字列として受け取り、Integer.parseInt を使ってJava の数値（int型）に変換する
            int id = Integer.parseInt(request.getParameter("id"));
            // ②DBからの削除処理
            // カテゴリ用DAOをインスタンス化し、deleteメソッドにIDを渡して、DBからそのカテゴリのレコードを完全に削除する
            CategoryDAO dao = new CategoryDAO();
            dao.delete(id);
            // ③削除完了後のリダイレクト
            // データの削除が綺麗に完了したので、ブラウザに対して最新のカテゴリ一覧画面を開き直してねと指示する
            // これにより、画面がパッと切り替わったときには、今消したカテゴリが一覧から消えた状態になる
            response.sendRedirect(request.getContextPath() + "/admin/categoryList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}