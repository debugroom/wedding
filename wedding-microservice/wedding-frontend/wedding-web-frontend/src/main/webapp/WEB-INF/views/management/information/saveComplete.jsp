<%@ page contentType="text/html; charset=UTF-8" session="false" %>
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
<title>Complete new information</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/saveComplete.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/saveComplete_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/saveComplete_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
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
            <p>以下の通り、インフォメーションが追加されました。</p>
              <table>
                <tbody>
                  <tr>
                    <td>インフォメーションID</td>
                    <td><c:out value="${informationResource.information.infoId}" /></td>
                  </tr>
                  <tr>
                    <td>タイトル</td>
                    <td><c:out value="${informationResource.information.title}" /></td>
                  </tr>
                  <tr>
                    <td>登録日時</td>
                    <td>
                      <fmt:formatDate value="${informationResource.information.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                    </td>
                  </tr>
                  <tr>
                    <td>公開日時</td>
                    <td>
                      <fmt:formatDate value="${informationResource.information.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3>メッセージ本文</h3>
              <hr>
              <div id="messageBody" class="messageBodyPanel">
                <c:import url="${informationResource.messageBodyUrl}"></c:import>
              </div>
              <h3>閲覧ユーザ</h3>
              <table class="userTable">
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                  </tr>
                <c:forEach items="${users}" var="user" varStatus="status">
                  <tr>
                    <td>${status.index+1}</td>
                    <td>
                      <c:out value="${user.userId}" />
                    </td>
                    <td>
                      <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="alternative-button">
              <button class="alternative-first-button" type="button"
                  onclick="location.href='${pageContext.request.contextPath}/management/information/new'" >続けて新規作成する</button>
              <button class="alternative-last-button" type="button"
                  onclick="location.href='${pageContext.request.contextPath}/management/information/portal'" >一覧に戻る</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>