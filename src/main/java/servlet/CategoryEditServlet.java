package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CategoryDAO;
import model.Category;

@WebServlet("/admin/categoryEdit")
public class CategoryEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.setCharacterEncoding("UTF-8");

        try {
            // ①パラメーターの取得
            // URLの末尾（?id=3）から、編集したいカテゴリのID番号を受け取り、Javaの数値（int型）に変換する
            int id = Integer.parseInt(request.getParameter("id"));
            // ②DBから1件取得
            // カテゴリ用DAOをインスタンス化し、指定されたID（例: 3番のカテゴリ）の詳しい情報をDBからピンポイントで1件だけ取得してオブジェクトに格納する
            CategoryDAO dao = new CategoryDAO();
            Category category = dao.findById(id);
            // ③JSPへ引き渡す準備
            // 取得したカテゴリデータを、次の編集画面で最初から入力欄に表示させるために、「category」という名前のポケット（リクエストスコープ）に詰め込む
            request.setAttribute("category", category);
            // ④編集画面のJSPへフォワード
            // 荷物（category）を持ったまま、編集フォームが用意されているJSP画面へ処理をバトンタッチする
            request.getRequestDispatcher("/adminCategoryEdit.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
