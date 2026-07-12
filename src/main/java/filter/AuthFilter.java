package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// アプリケーション内の全リクエストを対象にする
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // リクエストされたURLのパスを取得
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // ログインしなくてもアクセスできる「公開ページ」や「ログイン画面・処理」のパスを定義
        // 直接URLを入力してアクセスしていいページのパスだけを記載する
        boolean isPublicPage = path.equals("/") || 
                               path.equals("/index.jsp") || 
                               path.equals("/login") || 
                               path.startsWith("/css/") || 
                               path.startsWith("/js/");

        // セッションからログインユーザー情報を取得
        // ログインServletで session.setAttribute("xxx", ユーザーオブジェクト) としている名前を入れる
        Object loginUser = (session != null) ? session.getAttribute("account") : null;

        if (isPublicPage || loginUser != null) {
            // 公開ページか、ログイン済みならそのまま通す
            chain.doFilter(request, response);
        } else {
            // 未ログインで制限ページにアクセスしたら、公開画面へリダイレクト
            httpResponse.sendRedirect(contextPath + "/top.jsp"); 
        }
    }

    @Override
    public void destroy() {
        // 破棄処理
    }
}