<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${user.kana}さんの詳細</title>
<style>
    .detail-card { border: 1px solid #ddd; padding: 20px; border-radius: 10px; max-width: 500px; margin: 20px auto; font-family: sans-serif; }
    .profile-img { width: 100%; max-width: 300px; height: auto; border-radius: 8px; margin-bottom: 15px; display: block; margin-left: auto; margin-right: auto; }
    .like-btn { background-color: #ff4d4d; color: white; border: none; padding: 12px; cursor: pointer; border-radius: 5px; width: 100%; font-size: 16px; font-weight: bold; }
</style>
</head>
<body>
    <div id="user-raw-data" style="display: none;"
         data-id="${user.id}"
         data-name="${user.name}"
         data-kana="${user.kana}"
         data-gender="${user.gender}"
         data-age="${user.age}"
         data-image-path="${user.imagePath}"
         data-profile="${user.profile}"
         data-likes="${user.likes}">
    </div>

    <div id="detail-card-container"></div>

    <script>
        function renderUserDetail() {
            const container = document.getElementById('detail-card-container');
            container.innerHTML = ''; // 初期化

            // 隠しタグから属性データを一気に回収
            const dataEl = document.getElementById('user-raw-data');
            const id = dataEl.getAttribute('data-id');
            const name = dataEl.getAttribute('data-name');
            const kana = dataEl.getAttribute('data-kana');
            const gender = dataEl.getAttribute('data-gender');
            const age = dataEl.getAttribute('data-age');
            const imagePath = dataEl.getAttribute('data-image-path');
            const profile = dataEl.getAttribute('data-profile');
            const likes = dataEl.getAttribute('data-likes');

            // 画像があるかどうかの条件分岐
            let imageHtml = '';
            if (imagePath && imagePath.trim() !== '') {
                imageHtml = '<img src="images/' + imagePath + '" class="profile-img">';
            } else {
                imageHtml = '<div style="background: #eee; height: 200px; text-align: center; line-height: 200px;">No Image</div>';
            }

            // 元のJSPと全く同じHTML・クラス名・構造を文字列結合で組み立てる
            const cardHtml = 
                '<div class="detail-card">' +
                    '<h1>プロフィール詳細</h1>' +
                    
                    // 判定済みの画像HTMLを差し込む
                    imageHtml +

                    '<p><strong>名前：</strong> ' + name + '</p>' +
                    '<p><strong>フリガナ：</strong> ' + kana + '</p>' +
                    '<p><strong>性別：</strong> ' + gender + ' / <strong>年齢：</strong> ' + age + '歳</p>' +
                    '<hr>' +
                    '<p><strong>自己紹介：</strong><br>' + profile + '</p>' +
                    '<p><strong>❤ 現在のいいね数：</strong> ' + likes + '</p>' +

                    // いいねボタン
                    '<form action="LikeServlet" method="post">' +
                        '<input type="hidden" name="targetId" value="' + id + '">' +
                        '<button type="submit" class="like-btn">いいね！を送る</button>' +
                    '</form>' +
                    
                    '<div style="margin-top: 20px; text-align: center;">' +
                        '<a href="UserListServlet">← ランキングに戻る</a>' +
                    '</div>' +
                '</div>';

            // コンテナに流し込む
            container.insertAdjacentHTML('beforeend', cardHtml);
        }

        // ページが読み込まれたら実行
        window.addEventListener('DOMContentLoaded', renderUserDetail);
    </script>
</body>
</html>