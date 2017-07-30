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
<title>Delete request</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/delete.css">
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
        <p>以下の依頼事項を削除します。よろしければ、登録ボタンを押下してください。</p>
        <div class="formPanel">
          <form:form action="${pageContext.request.contextPath}/management/request/delete/${requestDetail.request.requestId}" 
                modelAttribute="requestDetail">
            <table>
              <tbody>
                <tr>
                  <td><form:label path="request.requestId">リクエストID</form:label></td>
                  <td>
                    <c:out value="${requestDetail.request.requestId}" />
                    <input type="hidden" name="requestId" value='<c:out value="${requestDetail.request.requestId}"></c:out>' /><br>
                  </td>
                </tr>
                <tr>
                  <td><form:label path="request.title">タイトル</form:label></td>
                  <td>
                    <c:out value="${requestDetail.request.title}" />
                    <input type="hidden" name="title" value='<c:out value="${requestDetail.request.title}"></c:out>' /><br>
                    <form:errors path="request.title" cssClass="errorMessage" />
                  </td>
                </tr>
                <tr>
                  <td><form:label path="request.registratedDate">登録日時</form:label></td>
                  <td>
                    <fmt:formatDate value="${requestDetail.request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                    <input type="hidden" name="registratedDate" value='<fmt:formatDate value="${requestDetail.request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />' /><br/>
                    <form:errors path="request.registratedDate" cssClass="errorMessage" />
                  </td>
                  </tr>
              </tbody>
            </table>
             <h3 id="contents2">依頼内容</h3>
              <div id="messageBody" class="request-panel">
                ${requestDetail.request.requestContents}
              </div>
              <input type="hidden" name="requestContents" value="<c:out value='${requestDetail.request.requestContents}' />" /><br/>
              <h3>依頼ユーザ</h3>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th>回答ステータス</th>
                    <th>回答</th>
                  </tr>
                  <c:set var="userCount" value="0" />
                  <c:forEach items="${requestDetail.approvedUsers}" var="user" varStatus="status">
                    <c:if test="${!empty user.userId}">
                    <tr>
                      <td>${userCount + 1}</td>
                      <td>
                        <c:out value="${user.userId}" />
                      </td>
                      <td>
                        <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                      </td>
                      <td>
                        <c:forEach items="${user.requestStatuses}" var="requestStatus" varStatus="status1">
                          <c:choose>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == true}">あり</c:when>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == false}">未</c:when>
                         </c:choose>
                      </c:forEach>
                      </td>
                      <td>
                        <c:forEach items="${user.requestStatuses}" var="requestStatus" varStatus="status1">
                          <c:choose>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == true}">○</c:when>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == false}">-</c:when>
                          </c:choose>
                        </c:forEach>
                      </td>
                    </tr>
                    <c:set var="userCount" value="${userCount + 1}" />
                    </c:if>
                  </c:forEach>
                  <c:forEach items="${requestDetail.deniedUsers}" var="user" varStatus="status">
                    <c:if test="${!empty user.userId}">
                    <tr>
                      <td>${userCount + 1}</td>
                      <td>
                        <c:out value="${user.userId}" />
                      </td>
                      <td>
                        <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                      </td>
                      <td>
                        <c:forEach items="${user.requestStatuses}" var="requestStatus" varStatus="status1">
                          <c:choose>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == true}">あり</c:when>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == false}">未</c:when>
                         </c:choose>
                      </c:forEach>
                      </td>
                      <td>
                        <c:forEach items="${user.requestStatuses}" var="requestStatus" varStatus="status1">
                          <c:choose>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == true}">○</c:when>
                            <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == false}">-</c:when>
                          </c:choose>
                        </c:forEach>
                      </td>
                    </tr>
                    <c:set var="userCount" value="${userCount + 1}" />
                    </c:if>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="delete" >削除する</form:button>
                <form:button class="alternative-last-button" name="type" value="return">依頼事項一覧に戻る</form:button>
              </div>
          </form:form>
        </div>
        </div>
      </div>
    </div>
  </article>
</body>