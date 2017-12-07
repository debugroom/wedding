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
<title>Information Detail</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/detail.css">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UpdateTextareaDialog.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/management/information/detail.js"></script>
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
          <div class="formPanel">
            <form:form action="${pageContext.request.contextPath}/management/information/${informationDetail.information.infoId}" 
              modelAttribute="informationDetail">
              <h3 id="contents1">インフォメーション</h3>
              <table>
                <tbody>
                  <tr>
                    <td>インフォメーションID</td>
                    <td>
                      <c:out value="${informationDetail.information.infoId}" />
                      <form:hidden path="information.infoId"/>
                    </td>
                    <td></td>
                  </tr>
                  <tr>
                    <td>タイトル</td>
                    <td>
                      <c:out value="${informationDetail.information.title}" />
                      <form:errors path="information.title" cssClass="errorMessage"/>
                      <form:hidden path="information.title"/>
                    </td>
                    <td>
                      <button id="information.title-button" name="information.title-button" type="button" value="information.title">変更</button>
                    </td>
                  </tr>
                  <tr>
                    <td>登録日時</td>
                    <td>
                      <fmt:formatDate value="${informationDetail.information.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                      <form:errors path="information.registratedDate" cssClass="errorMessage"/>
                      <input id="information.registratedDate" name="information.registratedDate" type="hidden" value='<fmt:formatDate value="${informationDetail.information.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />'>
                    </td>
                    <td></td>
                  </tr>
                  <tr>
                    <td>公開日時</td>
                    <td>
                      <fmt:formatDate value="${informationDetail.information.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                      <form:errors path="information.releaseDate" cssClass="errorMessage"/>
                      <input id="information.releaseDate" name="information.releaseDate" type="hidden" value='<fmt:formatDate value="${informationDetail.information.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />'>
                    </td>
                    <td><button id="information.releaseDate-button" name="information.releaseDate-button" type="button" value="information.releaseDate">変更</button></td>
                  </tr>
                  <tr>
                    <td>最終更新日時</td>
                    <td>
                      <fmt:formatDate value="${informationDetail.information.lastUpdatedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                      <form:errors path="information.lastUpdatedDate" cssClass="errorMessage"/>
                      <input id="information.lastUpdatedDate" name="information.lastUpdatedDate" type="hidden" value='<fmt:formatDate value="${informationDetail.information.lastUpdatedDate}" pattern="yyyy/MM/dd HH:mm:ss" />'>
                    </td>
                    <td></td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="warningMessageArea">
              </div>
              <hr>
              <div id="messageBody" class="information-panel">
                <c:import url="${informationDetail.messageBodyUrl}" charEncoding="UTF-8"></c:import>
              </div>
              <form:hidden path="information.infoPagePath"/>
              <hr>
              <div class="centering">
                <button id="messageBody-button" name="messageBody-button" class="centering-button" type="button" value="messageBody">変更</button>
              </div>
              <h3 id="contents3">ユーザアクセス</h3>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th>アクセス状況</th>
                    <th>削除</th>
                  </tr>
                  <c:set var="userCount" value="0" />
                  <c:forEach items="${informationDetail.noAccessedUsers}" var="user" varStatus="status">
                    <tr>
                      <td>${userCount+1}</td>
                      <td>
                        <c:out value="${user.userId}" />
                        <input id="users[${userCount}].userId" name="noAccessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                      </td>
                      <td>
                        <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                        <input id="users[${userCount}].lastName" name="noAccessedUsers[${status.index}].lastName" type="hidden" value='<c:out value="${user.lastName}" />'>
                        <input id="users[${userCount}].firstName" name="noAccessedUsers[${status.index}].firstName" type="hidden" value='<c:out value="${user.firstName}" />'>
                      </td>
                      <td>未</td>
                      <td>
                        <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                      </td>
                    </tr>
                    <c:set var="userCount" value="${userCount+1}" />
                  </c:forEach>
                  <c:forEach items="${informationDetail.accessedUsers}" var="user" varStatus="status">
                    <tr>
                      <td>${userCount+1}</td>
                      <td>
                        <c:out value="${user.userId}" />
                        <input id="users[${userCount}].userId" name="accessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                      </td>
                      <td>
                        <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                        <input id="users[${userCount}].lastName" name="accessedUsers[${status.index}].lastName" type="hidden" value='<c:out value="${user.lastName}" />'>
                        <input id="users[${userCount}].firstName" name="accessedUsers[${status.index}].firstName" type="hidden" value='<c:out value="${user.firstName}" />'>
                      </td>
                      <td>アクセス済</td>
                      <td>
                        <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                      </td>
                    </tr>
                    <c:set var="userCount" value="${userCount+1}" />
                  </c:forEach>
                </tbody>
              </table>
              <div class="centering">
                <button id="get-users-button" type="button" class="centering-button" value="${informationDetail.noAccessedUsersUrl}">追加</button>
              </div>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="update">更新を確定する</form:button>
                <form:button class="alternative-last-button" name="type" value="return">インフォメーション一覧へ戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>