package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ContactDAO;
import model.Contact;

@WebServlet("/admin/contactDetail")
public class AdminContactDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    try {
        // パラメータ取得
        // 画面から「どのお問い合わせを表示するか」のID番号を取得
        // getParameterで取得した文字列を、Integer.parseIntでJavaの数値（int型）に変換する
        int id = Integer.parseInt(request.getParameter("id"));

        // DBから1件取得
        // お問い合わせデータを専門に扱う部品（ContactDAO）をインスタンス化
        ContactDAO dao = new ContactDAO();
        // 先ほど取得した idを渡して、そのデータの全内容をDBから1件だけ引っ張ってきて、Contactオブジェクトに格納する
        Contact contact = dao.findById(id);

        // JSPへ渡す
        // 取得したお問い合わせデータ（contact）を、詳細表示画面のJSPに引き渡すために「contact」という名前のポケットに詰め込む
        request.setAttribute("contact", contact);
        
        // 荷物（contact）を持ったまま、詳細画面のJSPへ処理をバトンタッチする
        // これにより、詳細画面を開いたときに「お問い合わせされた本文や日時」を画面に綺麗に表示させることができる
        request.getRequestDispatcher("/adminContactDetail.jsp")
               .forward(request, response);
    
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
