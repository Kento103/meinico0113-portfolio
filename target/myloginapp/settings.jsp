<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>設定</title>
</head>
<body>
    <h2>設定変更</h2>
    
    <div id="settings-data-store" style="display: none;"
         data-message="${fn:escapeXml(message)}"
         data-current-name="${fn:escapeXml(currentName)}">
    </div>

    <div id="settings-container"></div>
    
    <br>
    <a href="user.jsp">戻る</a>

    <script>
        function renderSettings() {
            const container = document.getElementById('settings-container');
            container.innerHTML = ''; // 初期化

            // 隠しタグから属性データを回収
            const dataEl = document.getElementById('settings-data-store');
            const message = dataEl.getAttribute('data-message');
            const currentName = dataEl.getAttribute('data-current-name');

            // エラーメッセージがある場合だけ <p> タグを作成
            let messageHtml = '';
            if (message && message.trim() !== '') {
                messageHtml = '<p style="color: red;">' + message + '</p>';
            }

            // value属性に回収したcurrentNameを挟み込んで初期値を表示する
            const formHtml = 
                messageHtml + 
                '<form action="SettingsServlet" method="post">' +
                    '名前：<br>' +
                    '<input type="text" name="userName" value="' + currentName + '"><br><br>' +

                    'メールアドレス：<br>' +
                    '<input type="text" name="email"><br><br>' +

                    '新しいパスワード：<br>' +
                    '<input type="password" name="password"><br>' +
                    '<small>8~32文字の半角英数字と_-のみ</small><br><br>' +

                    '<input type="submit" value="変更を保存する">' +
                '</form>';

            // コンテナに流し込む
            container.insertAdjacentHTML('beforeend', formHtml);
        }

        // ページが読み込まれたら実行
        window.addEventListener('DOMContentLoaded', renderSettings);
    </script>
</body>
</html>