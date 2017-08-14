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
<title>Create new information</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/form.css">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/management/information/form.js"></script>
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
            <p>通知する新規インフォメーションを入力してください。</p>
            <h3>概要</h3>
            <form:form action="${pageContext.request.contextPath}/management/information/draft/new"
                modelAttribute="newInformationForm" >
              <table>
                <c:if test="${!empty errorMessage}">
                  <div class="errorMessage">
                    <c:out value="${errorMessage}" />
                  </div>
                </c:if>
                <tbody>
                  <tr>
                    <td>
                      <form:label path="title">
                        タイトル
                      </form:label>
                    </td>
                    <td>
                      <form:input class="long" path="title" />
                      <br>(256文字以内)
                      <br><form:errors path="title" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <form:label path="infoName">
                      インフォメーション名
                      </form:label></td>
                    <td>
                      <form:input class="middle" path="infoName" />
                      <br>(半角英数字50文字以内)
                      <br><form:errors path="infoName" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <form:label path="releaseDate">
                      公開日時
                      </form:label></td>
                    <td>
                    <form:input class="middle" path="releaseDate" type="datetime" />
                    <br>(50文字以内)
                    <br><form:errors path="releaseDate" cssClass="errorMessage" />
                  </td>
                </tr>
              </tbody>
            </table>
            <h3>メッセージ本文</h3>
            <div class="editMessagePanel">
              <form:textarea path="messageBody" wrap="hard" rows="20" cols="60" />
            </div>
            <h3>メッセージ通知先</h3>
            <div class="userListPanel">
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th>
                    通知対象<br>
                    一括切替：<input type="checkbox" id="allCheckbox" value="off" autocomplete="off" />
                    </th>
                  </tr>
                <c:forEach items="${users}" var="user" varStatus="status">
                  <tr>
                    <td>${status.index + 1}</td>
                    <td>
                      <c:out value="${user.userId}" />
                      <input id="users[${status.index}].userId" type="hidden" name="users[${status.index}].userId" value="${user.userId}" />
                    </td>
                    <td>
                      <c:out value="${user.lastName}" /> <c:out value="${user.firstName}"/>
                      <input id="users[${status.index}].lastName" type="hidden" name="users[${status.index}].lastName" value="${user.lastName}" />
                      <input id="users[${status.index}].firstName" type="hidden" name="users[${status.index}].firstName" value="${user.firstName}" />
                    </td>
                    <td>
                      <input type="checkbox" name="${status.index}" value="off" autocomplete="off" />
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            <form:button class="main-button" name="confrim" >情報登録</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>