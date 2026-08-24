package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Account;
import dao.AccountDAO;

@WebServlet("/UserProfileUpdateServlet")
@MultipartConfig // 画像などのファイルアップロード（multipart/form-data）を受け取るために必須
public class ProfileUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // ログインチェック
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("account");
        if (loginUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
        }

        // フォームからの入力値取得
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String kana = request.getParameter("kana");
        String gender = request.getParameter("gender");
        String ageStr = request.getParameter("age");
        String introduction = request.getParameter("introduction");

        int id = loginUser.getId();
        if (idStr != null && !idStr.isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                // ID変換失敗時はログインユーザーのIDを使用
            }
        }

        String errorMsg = null;

        // バリデーション処理
        // 名前のチェック
        if (errorMsg == null) {
            if (name == null || name.trim().isEmpty()) {
                errorMsg = "名前を入力してください。";
            } else if (name.length() > 255) {
                errorMsg = "名前は255文字以内で入力してください。";
            }
        }

        // ふりがなのチェック
        if (errorMsg == null) {
            String kanaPattern = "^[\\u3041-\\u3096ー]*$";
            if (kana == null || kana.trim().isEmpty()) {
                errorMsg = "ふりがなを入力してください。";
            } else if (kana.length() > 255) {
                errorMsg = "ふりがなは255文字以内で入力してください。";
            } else if (!kana.matches(kanaPattern)) {
                errorMsg = "ふりがなは「ひらがな」で入力してください。";
            }
        }

        // 性別のチェック
        if (errorMsg == null) {
            if (gender == null || gender.trim().isEmpty()) {
                errorMsg = "性別を選択してください。";
            } else if (!"male".equals(gender) && !"female".equals(gender) && !"other".equals(gender)) {
                errorMsg = "性別を正しく選択してください。";
            }
        }

        // 年齢のチェック
        int age = 0;
        if (errorMsg == null) {
            if (ageStr == null || ageStr.trim().isEmpty()) {
                errorMsg = "年齢を入力してください。";
            } else if (!ageStr.matches("^[0-9]{1,3}$")) {
                errorMsg = "年齢は3桁以内の数字で入力してください。";
            } else {
                age = Integer.parseInt(ageStr);
            }
        }

        // 自己紹介のチェック
        if (errorMsg == null) {
            if (introduction != null && introduction.length() > 1500) {
                errorMsg = "自己紹介は1500文字以内で入力してください。";
            }
        }

        // 画像のチェック
        Part filePart = null;
        if (errorMsg == null) {
            try {
                filePart = request.getPart("profileImage");
                if (filePart != null && filePart.getSize() > 0) {
                    long maxSize = 2 * 1024 * 1024; // 2MB
                    if (filePart.getSize() > maxSize) {
                        errorMsg = "プロフィール画像は2MB以内のファイルを選択してください。";
                    }
                }
            } catch (Exception e) {
                errorMsg = "画像の読み込み中にエラーが発生しました。";
            }
        }

        // 更新用オブジェクトを作成して値をセット
        Account userProfile = new Account();
        userProfile.setId(id);
        userProfile.setName(name);
        userProfile.setKana(kana);
        userProfile.setGender(gender);
        userProfile.setAge(age);
        userProfile.setProfile(introduction); // JSPの textarea name="introduction" をモデルの profile に設定

        AccountDAO dao = new AccountDAO();

        // エラーがあった場合はフォームへ押し戻す
        if (errorMsg != null) {
            // 現在設定されている画像パスを復元して再表示に備える
            Account current = dao.findById(id);
            if (current != null) {
                userProfile.setImagePath(current.getImagePath());
            }
            
            // sessionにセットして UserEditServlet 経由で再表示させる
            session.setAttribute("error", errorMsg);
            session.setAttribute("editInput", userProfile); // 入力内容を保持
            response.sendRedirect(request.getContextPath() + "/UserEditServlet");
            return;
        }

        // 画像のファイル保存処理（バリデーション通過後）
        String saveFileName = null;
        if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            try {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadDir = getServletContext().getRealPath("/uploads");
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                saveFileName = System.currentTimeMillis() + "_" + fileName;
                filePart.write(uploadDir + File.separator + saveFileName);
                
                // DBにはファイル名（または相対パス）をセット
                userProfile.setImagePath(saveFileName); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 画像が新規選択されなかった場合、既存の画像をそのまま引き継ぐ
        if (saveFileName == null) {
            Account current = dao.findById(id);
            if (current != null) {
                userProfile.setImagePath(current.getImagePath());
            }
        }

        // DB更新の実行
        boolean isSuccess = dao.updateProfile(userProfile);

        if (isSuccess) {
            session.setAttribute("account", userProfile);
            response.sendRedirect(request.getContextPath() + "/user.jsp");
        } else {
            session.setAttribute("error", "プロフィールの更新に失敗しました。");
            response.sendRedirect(request.getContextPath() + "/UserEditServlet");
        }
    }
}