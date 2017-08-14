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
<title>Delete Information</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/delete.css">
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
        <p>以下のインフォメーションを削除します。よろしければ、削除ボタンを押下してください。</p>
        <div class="informationFormPanel">
          <form:form action="${pageContext.request.contextPath}/management/information/delete/${informationDetail.information.infoId}" 
                modelAttribute="informationDetail">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="information.infoId">インフォID</form:label></td>
                    <td>
                      <c:out value="${informationDetail.information.infoId}" />
                      <input type="hidden" name="infoId" value="<c:out value='${informationDetail.information.infoId}' />" /><br/>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.title">タイトル</form:label></td>
                    <td>
                      <c:out value="${informationDetail.information.title}" />
                      <input type="hidden" name="title" value="<c:out value='${informationDetail.information.title}' />" /><br/>
                      <form:errors path="information.title" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.registratedDate">登録日時</form:label></td>
                    <td>
                      <c:out value="${informationDetail.information.registratedDate}" />
                      <input type="hidden" name="registratedDate" value="<c:out value='${informationDetail.information.registratedDate}' />" /><br/>
                      <form:errors path="information.registratedDate" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.releaseDate">公開日時</form:label></td>
                    <td>
                      <c:out value="${informationDetail.information.releaseDate}" />
                      <input type="hidden" name="releaseDate" value="<c:out value='${informationDetail.information.releaseDate}' />" /><br/>
                      <form:errors path="information.releaseDate" cssClass="errorMessage" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="messageBody" class="information-panel">
                <c:import url="${informationDetail.messageBodyUrl}"></c:import>
              </div>
              <h3>メッセージ通知先</h3>
             <table>
              <tbody>
                <tr>
                  <th>No</th>
                  <th>ユーザID</th>
                  <th>ユーザ名</th>
                  <th>アクセス状況</th>
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
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="delete" >削除する</form:button>
                <form:button class="alternative-last-button" name="type" value="return">一覧に戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>