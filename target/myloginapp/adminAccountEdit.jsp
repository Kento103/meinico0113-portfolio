<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Account" %>
<%
    Account account = (Account)request.getAttribute("account");
    // ロールの判定
    String role = (account != null && account.getRole() != null) ? account.getRole() : "user";
    
    // 一般ユーザー項目の null 対策（画面に "null" と表示されないようにする）
    String kana = (account.getKana() != null) ? account.getKana() : "";
    String gender = (account.getGender() != null) ? account.getGender() : "";
    String profile = (account.getProfile() != null) ? account.getProfile() : "";
%>

<h2>アカウント編集（<%= role.equals("admin") ? "管理者" : "一般ユーザー" %>）</h2>

<form action="<%= request.getContextPath() %>/admin/accountUpdate" 
      method="post" 
      enctype="multipart/form-data">

    <%-- 共通項目：IDとロール --%>
    <input type="hidden" name="id" value="<%= account.getId() %>">

    <%-- ★新規追加：一般、管理者切替ラジオボタン --%>
    <div style="margin-bottom: 15px;">
        種別：
        <input type="radio" name="role" value="user" id="roleUser" <%= "user".equals(role) ? "checked" : "" %> onchange="switchFields()">
        <label region="roleUser">一般</label>
        
        <input type="radio" name="role" value="admin" id="roleAdmin" <%= "admin".equals(role) ? "checked" : "" %> onchange="switchFields()">
        <label region="roleAdmin">管理者</label>
    </div>

    <hr region="separator">

    <%-- ★管理者用項目グループ --%>
    <div id="adminFields">
        <h3>管理者項目</h3>

        <%-- 管理者の場合：名前・メール・ステータスのみ --%>
        名前：<input type="text" name="name" value="<%= account.getName() %>" required maxlength="255"><br>
        メール：<input type="email" name="email" value="<%= account.getEmail() %>" required maxlength="255"><br>
        ステータス：
        <select name="status">
            <option value="1" <%= account.getStatus() == 1 ? "selected" : "" %>>アクセス許可</option>
            <option value="0" <%= account.getStatus() == 0 ? "selected" : "" %>>アクセス禁止</option>
        </select><br>
    </div>

     <%-- 一般ユーザー用項目グループ --%>
    <div id="userFields">
        <h3>一般項目</h3>
        <%-- 
          一般ユーザー選択時でも、更新用DAOがnameやemail、statusのパラメータを
          要求する場合は、ここに現在の値をセットして送信できるようにしておく
        --%>
        <%-- ★一般ユーザーの場合：ふりがな〜画像変更のみ --%>
        <%-- ※DAOのuserがname, email, statusを要求するため、hiddenで値を送る必要がある --%>
        <input type="hidden" name="name" value="<%= account.getName() %>">
        <input type="hidden" name="email" value="<%= account.getEmail() %>">
        <input type="hidden" name="status" value="<%= account.getStatus() %>">

        ふりがな：<input type="text" name="kana" value="<%= kana %>" maxlength="255"><br>
        性別：
        <input type="radio" name="gender" value="男性" <%= "男性".equals(gender) ? "checked" : "" %>>男性
        <input type="radio" name="gender" value="女性" <%= "女性".equals(gender) ? "checked" : "" %>>女性
        <input type="radio" name="gender" value="その他" <%= "その他".equals(gender) ? "checked" : "" %>>その他<br>

        年齢：<input type="number" name="age" min="0" max="150" value="<%= account.getAge() %>" oninput="if(value.length>3)value=value.slice(0,3)">

        自己紹介：<br>
        <%-- textareaはタグの間に値を挟む --%>
        <textarea name="profile" rows="4" cols="40" maxlength="1500"><%= profile %></textarea><br>

        <%-- 現在の画像がある場合に表示（任意） --%>
        <% if(account.getImagePath() != null) { %>
            <p>現在の画像：<br><img src="<%= account.getImagePath() %>" width="100"></p>
        <% } %>
        プロフィール画像変更：<input type="file" name="image"><br>
    </div>

    <br>
    <button type="submit">更新保存</button>
    <a href="<%= request.getContextPath() %>/admin/accountList">キャンセル</a>

</form>

<%-- リアルタイム切り替え用JavaScript --%>
<script>
function switchFields() {
    // ラジオボタンの選択状態を取得
    const isAdmin = document.getElementById('roleAdmin').checked;
    
    // 各入力エリアのDOMを取得
    const adminFields = document.getElementById('adminFields');
    const userFields = document.getElementById('userFields');

    if (isAdmin) {
        // 管理者が選ばれたら、管理者用を表示、一般用を非表示
        adminFields.style.display = 'block';
        userFields.style.display = 'none';
    } else {
        // 一般が選ばれたら、一般用を表示、管理者用を非表示
        adminFields.style.display = 'none';
        userFields.style.display = 'block';
    }
}

// 画面読み込み時に初期状態に合わせて表示を切り替える
window.onload = function() {
    switchFields();
};
</script>