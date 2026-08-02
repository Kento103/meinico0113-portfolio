<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公開画面</title>
<style>
    /* ユーザー1人分の情報の枠線 */
    .user-card { border: 1px solid #ddd; margin: 10px; padding: 10px; border-radius: 8px; width: 300px; }
    
    /* ボタンの共通スタイル */
    .btn { 
        display: inline-block;
        padding: 5px 10px; 
        cursor: pointer; 
        border-radius: 4px; 
        border: none;
        text-decoration: none; /* リンクの下線を消す */
        font-size: 13.333px; /* ボタンのデフォルトフォントサイズに合わせる */
        font-family: Arial;
        color: white;
    }
    
    /* いいねボタン（赤）*/
    .like-btn { background-color: #ff4d4d; }
    
    /* 詳細ボタン（グレー）*/
    .detail-btn { background-color: #888888; }

    /* 横並びに配置 */
    .button-group { display: flex; gap: 10px; margin-top: 10px; }
</style>
</head>
<body>
    <h1>公開画面</h1>

    <div style="margin-top: 10px;">
        <a href="<%= request.getContextPath() %>/index.jsp">ログイン</a>
        <a href="ContactServlet">お問い合わせ</a>
    </div>

    <h2>ユーザーランキング</h2>
    <div id="user-data-store" style="display: none;">
        <c:forEach var="acc" items="${userList}">
            <span class="user-raw-data" 
                  data-id="${acc.id}"
                  data-kana="${acc.kana}"
                  data-gender="${acc.gender}"
                  data-age="${acc.age}"
                  data-profile="${acc.profile}"
                  data-likes="${acc.likes}"></span>
        </c:forEach>
    </div>

    <div id="user-list-container"></div>
        
    <script>
        function renderUserList() {
            // ① 画面の準備とデータの取得
            const container = document.getElementById('user-list-container');
            container.innerHTML = ''; 
            // ② 隠しておいたデータを全部集める
            const dataElements = document.querySelectorAll('.user-raw-data');
            
            // ③ 1人ずつデータを抜き出す（ループ処理）
            dataElements.forEach(el => {
                const id = el.getAttribute('data-id');
                const kana = el.getAttribute('data-kana');
                const gender = el.getAttribute('data-gender');
                const age = el.getAttribute('data-age');
                const profile = el.getAttribute('data-profile');
                const likes = el.getAttribute('data-likes');

                // ④ カードの形に組み立てて、画面に貼り付ける
                const cardHtml = 
                    '<div class="user-card">' +
                        '<strong>ニックネーム: ' + kana + '</strong><br>' +
                        '<span>性別: ' + gender + ' / 年齢: ' + age + '歳</span><br>' +
                        '<p>自己紹介: ' + profile + '</p>' +
                        '<p>❤ 現在のいいね数: <strong id="like-count-' + id + '">' + likes + '</strong></p>' +
                        '<div class="button-group">' +
                            '<a href="UserDetailServlet?id=' + id + '" class="btn detail-btn">詳細を見る</a>' +
                            '<button type="button" class="btn like-btn" onclick="sendLike(\'' + id + '\')">いいね！</button>' +
                        '</div>' +
                    '</div>';

                container.insertAdjacentHTML('beforeend', cardHtml);
            });
        }

        // 非同期でいいねを送信する関数
    function sendLike(targetId) {
        // サーブレットに渡すデータ
        const formData = new URLSearchParams();
        formData.append('targetId', targetId);

        fetch('UserRankingServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('ネットワークエラーが発生しました');
            }
            return response.text();
        })
        .then(data => {
            // 通信成功時画面の「いいね数」の表示を更新する
            const likeCountEl = document.getElementById('like-count-' + targetId);
            if (likeCountEl) {
                // サーブレットから最新のいいね数が返ってくる場合はdataをそのままセット
                // 単純に+1するだけなら以下のように記述
                if (data === "ok") {
                let currentLikes = parseInt(likeCountEl.textContent, 10);
                likeCountEl.textContent = currentLikes + 1;
            } else {
                likeCountEl.textContent = data; // 最新値が文字列で届いた場合
            }
        }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('いいねの送信に失敗しました');
        });
    }

        window.addEventListener('DOMContentLoaded', renderUserList);
    </script>
</body>
</html>