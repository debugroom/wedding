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
<title>Confirm new request</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/confirm.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/confirm_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/confirm_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
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
            <p>以下の依頼事項を作成します。よろしければ、登録ボタンを押下してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/request/new"
                       modelAttribute="requestDraft">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="request.requestId">リクエストID</form:label></td>
                    <td>
                      <c:out value="${requestDraft.request.requestId}" />
                      <input type="hidden" name="requestId" value='<c:out value="${requestDraft.request.requestId}"></c:out>' /><br>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="request.title">タイトル</form:label></td>
                    <td>
                      <c:out value="${requestDraft.request.title}" />
                      <input type="hidden" name="title" value='<c:out value="${requestDraft.request.title}"></c:out>' /><br>
                      <form:errors path="request.title" cssClass="errorMessage" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="request.registratedDate">登録日時</form:label></td>
                    <td>
                      <fmt:formatDate value="${requestDraft.request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                      <input type="hidden" name="registratedDate" value='<fmt:formatDate value="${requestDraft.request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />' /><br/>
                      <form:errors path="request.registratedDate" cssClass="errorMessage" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">依頼内容</h3>
              <div id="messageBody" class="request-panel">
                ${requestDraft.request.requestContents}
              </div>
              <input type="hidden" name="requestContents" value="<c:out value='${requestDraft.request.requestContents}' />" /><br/>
              <h3>依頼ユーザ</h3>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                  </tr>
                  <c:set var="userCount" value="0" />
                  <c:forEach items="${requestDraft.acceptors}" var="user" varStatus="status">
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
                    <c:set var="userCount" value="${userCount+1}" />
                    </c:if>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="save">登録</form:button>
                <form:button class="alternative-last-button" name="type" value="return">戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>
