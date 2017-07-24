<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://debugroom.org/tags" prefix="d"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Information Delete Complete</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/deleteComplete.css">
</head>
<body>
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <article>
    <div id="flex-container">
      <div class="flex-item-1">
       <c:import url="/WEB-INF/views/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="resultPanel">
            <p>以下のインフォメーションを削除しました。</p>
            <table>
              <tbody>
                <tr>
                  <td>インフォメーションID</td>
                  <td><c:out value="${informationDetail.information.infoId}" /></td>
                </tr>
                <tr>
                  <td>タイトル</td>
                  <td><c:out value="${informationDetail.information.title}" /></td>
                </tr>
                <tr>
                  <td>登録日時</td>
                  <td><c:out value="${informationDetail.information.registratedDate}" /></td>
                </tr>
                <tr>
                  <td>公開日時</td>
                  <td><c:out value="${informationDetail.information.releaseDate}" /></td>
                </tr>
              </tbody>
            </table>
            <h3>メッセージ本文</h3>
            <hr>
            <div id="messageBody" class="information-panel">
              ${informationDetail.messageBody}
            </div>
            <button class="main-button" type="button"
                  onclick="location.href='/management/information/portal'" >インフォメーション一覧に戻る</button>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>