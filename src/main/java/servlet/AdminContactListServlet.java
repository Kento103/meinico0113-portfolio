package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.ContactDAO;
import model.Contact;

@WebServlet("/admin/contactList")
public class AdminContactListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // お問い合わせデータを専門に扱う部品（ContactDAO）をインスタンス化
            ContactDAO dao = new ContactDAO();
            // 【全件取得】
            // DAOにお願いして、データベースにあるすべてのお問い合わせデータを取ってきてもらい、Contactオブジェクトが詰まった「List」として一括で受け取る
            List<Contact> list = dao.findAll();
            // 【JSPへ引き渡す準備】
            // 取得したお問い合わせリスト（list）を、次のJSP画面に引き渡すために「contactList」という名前のポケットに詰め込む
            request.setAttribute("contactList", list);
            // 【画面へフォワード】
            // 荷物（リスト）を持ったまま、一覧表示専用のJSP画面へ処理をバトンタッチする
            // これにより、JSP画面側でループ処理を使って、お問い合わせを上から順に並べて表示できる
            request.getRequestDispatcher("/adminContactList.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
