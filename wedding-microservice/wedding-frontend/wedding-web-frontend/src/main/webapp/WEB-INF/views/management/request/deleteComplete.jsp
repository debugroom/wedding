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
<title>Request Delete Complete</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/deleteComplete.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/deleteComplete_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/deleteComplete_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/menu.js"></script>
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
            <p>以下の依頼事項を削除しました。</p>
            <table>
              <tbody>
                <tr>
                  <td>リクエストID</td>
                  <td><c:out value="${requestDetail.request.requestId}" /></td>
                </tr>
                <tr>
                  <td>タイトル</td>
                  <td><c:out value="${requestDetail.request.title}" /></td>
                </tr>
                <tr>
                  <td>登録日時</td>
                  <td>
                    <fmt:formatDate value="${requestDetail.request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                  </td>
                </tr>
              </tbody>
            </table>
            <h3>依頼内容</h3>
            <hr>
            <div id="messageBody" class="request-panel">
              ${requestDetail.request.requestContents}
            </div>
            <button class="main-button" type="button"
                  onclick="location.href='${pageContext.request.contextPath}/management/request/portal'" >依頼事項一覧に戻る</button>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>