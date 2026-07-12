package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/admin/accountNew")
public class AccountNewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/adminAccountNew.jsp")
               .forward(request, response);
    }
}

// 何もデータを加工せず、そのまま新規登録画面のJSPへ処理をバトンタッチする
// →なぜServletが必要なのか？
// Servletを経由させることで、「一般ユーザーがURLを直接打ち込んで、不正に登録画面を開いてしまう」といった事態を防ぐため