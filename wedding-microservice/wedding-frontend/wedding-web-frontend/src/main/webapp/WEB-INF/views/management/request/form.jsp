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
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/form.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/form_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/form_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/management/request/form.js"></script>
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
          <div class="requestFormPanel">
            <p>依頼を入力してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/request/draft/new"
              modelAttribute="newRequestForm">
              <c:if test="${!empty errorMessage}">
                <div class="errorMessage">
                  <c:out value="${errorMessage}" />
                </div>
              </c:if>
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="title">タイトル</form:label></td>
                    <td>
                      <form:input class="long" path="title" />
                      <br>256文字以内
                      <br><form:errors path="title" cssStyle="color:red" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3>依頼内容</h3>
              <div class="editMessagePanel">
                <form:textarea path="requestContents" wrap="hard" rows="20" cols="60"/>
              </div>
              <h3>依頼ユーザ</h3>
              <div class="userListPanel">
                <table>
                  <thead>
                    <tr>
                      <th>No</th>
                      <th>ユーザID</th>
                      <th>ユーザ名</th>
                      <th>追加<br>一括切替：<input type="checkbox" id="allCheckbox" value="off" autocomplete="off" /></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${users}" var="user" varStatus="status">
                      <tr>
                        <td>${status.index + 1}</td>
                        <td>
                          <c:out value="${user.userId}" />
                          <input id="users[${status.index}].userId" type="hidden" name="users[${status.index}].userId" value="${user.userId}" />
                        </td>
                        <td>
                          <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
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
              <form:button class="main-button" name="confirm">依頼登録</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>