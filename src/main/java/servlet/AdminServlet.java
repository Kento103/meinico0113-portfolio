package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import dao.CategoryDAO;
import model.Account;
import model.Category;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

    // 画面に「〇〇さん いいね数：5個」のような文字のリストを表示するため、Stringを詰めるリストを作る
    List<String> rankingList = new ArrayList<>();
    
    // 一般ユーザーのみのいいねランキング取得処理
        try {
            AccountDAO accountDAO = new AccountDAO();
            List<Account> accountRanking = accountDAO.findGeneralUsersOrderByLikes();

            for (Account acc : accountRanking) {
                // かなが空でなければかなを表示、空なら氏名を表示
                String displayName = (acc.getKana() != null && !acc.getKana().isEmpty()) 
                                        ? acc.getKana() : acc.getName();
                
                String record = displayName + " いいね数：" + acc.getLikes();
                rankingList.add(record);
                
                // デバッグ用ログ
                System.out.println("取得データ: " + record);
            }
            request.setAttribute("rankingList", rankingList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // カテゴリ一覧取得処理
        try {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categoryList = categoryDAO.findAll();
            request.setAttribute("categoryList", categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 最後に1回だけフォワードする
        request.getRequestDispatcher("success.jsp").forward(request, response);
        
    }
}