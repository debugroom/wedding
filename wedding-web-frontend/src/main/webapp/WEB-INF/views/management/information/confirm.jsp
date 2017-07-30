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
<title>Confirm new information</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/confirm.css">
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
          <div class="informationFormPanel">
            <p>以下のインフォメーションを作成します。よろしければ、下の作成ボタンを押してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/information/new/" 
                modelAttribute="informationDraft">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="information.infoId">インフォID</form:label></td>
                    <td>
                      <c:out value="${informationDraft.information.infoId}" />
                      <input type="hidden" name="infoId" value="<c:out value='${informationDraft.information.infoId}' />" /><br/>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.title">タイトル</form:label></td>
                    <td>
                      <c:out value="${informationDraft.information.title}" />
                      <input type="hidden" name="title" value="<c:out value='${informationDraft.information.title}' />" /><br/>
                      <form:errors path="information.title" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infoName">インフォメーション名</form:label></td>
                    <td>
                      <c:out value="${informationDraft.infoName}" />
                      <input type="hidden" name="infoName" value="<c:out value='${informationDraft.infoName}' />" /><br/>
                      <form:errors path="infoName" cssClass="errorMessage"/>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.registratedDate">登録日時</form:label></td>
                    <td>
                      <fmt:formatDate value="${informationDraft.information.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                      <input type="hidden" name="registratedDate" value='<fmt:formatDate value="${informationDraft.information.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />' /><br/>
                      <form:errors path="information.registratedDate" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="information.releaseDate">公開日時</form:label></td>
                    <td>
                      <fmt:formatDate value="${informationDraft.information.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                      <input type="hidden" name="releaseDate" value='<fmt:formatDate value="${informationDraft.information.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />'  /><br/>
                      <form:errors path="information.releaseDate" cssClass="errorMessage" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="messageBody" class="messageBodyPanel">
                <c:import url="${informationDraft.tempInfoUrl}"></c:import>
              </div>
              <input type="hidden" name="infoPagePath" value="<c:out value='${informationDraft.information.infoPagePath}' />" /><br/>
              <h3>メッセージ通知先</h3>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                  </tr>
                  <c:set var="userCount" value="0" />
                  <c:forEach items="${informationDraft.viewUsers}" var="user" varStatus="status">
                    <c:if test="${!empty user.userId}">
                  <tr>
                    <td>${userCount + 1}</td>
                    <td>
                      <c:out value="${user.userId}" />
                      <input type="hidden" name="checkedUsers[${userCount}].userId" value="${user.userId}" />
                    </td>
                    <td>
                      <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                      <input type="hidden" name="checkedUsers[${userCount}].lastName" value="${user.lastName}" />
                      <input type="hidden" name="checkedUsers[${userCount}].firstName" value="${user.firstName}" />
                    </td>
                  </tr>
                    <c:set var="userCount" value="${userCount + 1}" />
                    </c:if>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="save">登録</form:button>
                <form:button class="alternative-last-button" name="type" value="return">入力画面に戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>