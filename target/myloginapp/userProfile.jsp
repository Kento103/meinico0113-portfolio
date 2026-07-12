<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>プロフィール詳細</title>
<style>
    body { font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #f5f7fa; color: #333; margin: 0; padding: 20px; }
    .profile-box { width: 450px; margin: 40px auto; border: 1px solid #e1e4e8; padding: 30px; border-radius: 15px; background-color: #fff; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
    .profile-header { text-align: center; margin-bottom: 25px; }
    .profile-header h2 { margin: 10px 0 5px 0; color: #222; }
    .prof-img { width: 130px; height: 130px; border-radius: 50%; object-fit: cover; border: 3px solid #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); display: block; margin: 0 auto 15px; }
    .item { margin-bottom: 15px; border-bottom: 1px solid #f0f2f5; padding-bottom: 8px; display: flex; align-items: center; }
    .label { font-weight: bold; color: #6a737d; font-size: 0.9em; width: 120px; flex-shrink: 0; }
    .value { color: #24292e; font-size: 1em; }
    .profile-text { background: #f8f9fa; padding: 12px; border-radius: 6px; border: 1px solid #e1e4e8; margin-top: 8px; min-height: 50px; word-break: break-all; }
    .back-link { display: block; text-align: center; margin-top: 25px; text-decoration: none; color: #007bff; font-weight: bold; }
    .back-link:hover { text-decoration: underline; }
    .error-text { color: red; font-size: 0.85em; margin-top: 4px; display: block; }
</style>
</head>
<body>

<div class="profile-box">
    <div class="profile-header">
        <c:choose>
            <%-- 【条件：画像パスが空（未登録）の場合】--%>
            <c:when test="${empty user.imagePath}">
                <img src="uploads/default.png" class="prof-img" alt="デフォルト画像">
            </c:when>

            <%-- 【条件：それ以外（画像パスがちゃんと入っている場合）】 --%>
            <c:otherwise>
                <%-- uploads/ → フォルダの配下にある、登録された画像ファイル名を表示
                     c:out → を使うことで、ファイル名に変なスクリプトが含まれていても安全に無害化（サニタイズ）する
                    onerror属性 → 万が一、DBに名前はあるのにサーバーから画像ファイルが消えていた（404エラー）場合、JavaScriptが自動で動き、その場でデフォルト画像に差し替えて画面が壊れるのを防ぐ --%>
                <img src="uploads/<c:out value='${user.imagePath}' />" class="prof-img" onerror="this.src='uploads/default.png';" alt="プロフィール画像">
            </c:otherwise>
        </c:choose>
        <h2><c:out value="${user.nickname}" default="名無し" /> のプロフィール</h2>
    </div>

    <div class="item">
        <span class="label">名前:</span>
        <span class="value">
            <c:choose>
                <c:when test="${empty user.name}"><span style="color: #999;">（名前未登録）</span></c:when>
                <c:otherwise><c:out value="${user.name}" /></c:otherwise>
            </c:choose>
        </span>
    </div>

    <div class="item">
        <span class="label">ニックネーム:</span> 
        <span class="value"><c:out value="${user.nickname}" default="ー" /></span>
    </div>

    <div class="item">
        <span class="label">フリガナ:</span> 
        <span class="value">
            <c:choose>
                <c:when test="${empty user.kana}"><span style="color: #999;">ー</span></c:when>
                <c:otherwise><c:out value="${user.kana}" /></c:otherwise>
            </c:choose>
        </span>
    </div>

    <div class="item">
        <span class="label">性別:</span> 
        <span class="value"><c:out value="${user.gender}" default="未選択" /></span>
    </div>

    <div class="item">
        <span class="label">年齢:</span> 
        <span class="value">
            <c:choose>
                <c:when test="${empty user.age || user.age <= 0}"><span style="color: #999;">非公開</span></c:when>
                <c:otherwise><c:out value="${user.age}" /> 歳</c:otherwise>
            </c:choose>
        </span>
    </div>
    
    <div style="margin-top: 15px;">
        <span class="label">自己紹介:</span>
        <div class="profile-text" style="white-space: pre-wrap;"><c:out value="${user.profile}" default="自己紹介はまだ未設定です。" /></div>
    </div>

    <a href="user.jsp" class="back-link">← マイページへ戻る</a>
</div>

</body>
</html>