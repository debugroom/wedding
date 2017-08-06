<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/request/detail-flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateTextareaDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/request/detail.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
    <div id="flex-container">
      <div class="flex-item-1">
        <c:import url="/WEB-INF/jsp/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="formPanel">
            <form:form 
            action="${pageContext.request.contextPath}/management/request/${requestDetail.request.requestId}" 
            modelAttribute="requestDetail">
            <h3 id="contents1">依頼事項</h3>
            <table>
              <tbody>
                <tr>
                  <td>依頼ID</td>
                  <td>
                    <c:out value="${requestDetail.request.requestId}" />
                    <form:hidden path="request.requestId"/>
                  </td>
                  <td></td>
                </tr>
                <tr>
                  <td>タイトル</td>
                  <td>
                    <c:out value="${requestDetail.request.title}" />
                    <form:hidden path="request.title"/>
                  </td>
                  <td>
                    <button id="request.title-button" name="request.title-button" type="button" value="request.title">変更</button>
                  </td>
                </tr>
                <tr>
                  <td>登録日時</td>
                  <td>
                    <fmt:formatDate value="${requestDetail.request.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                    <form:errors path="request.registratedDate" />
                    <input id="request.registratedDate" name="request.registratedDate" type="hidden" value='<fmt:formatDate value="${requestDetail.request.registratedDate}" pattern="yyyy-MM-dd hh:mm:ss" />'>
                  </td>
                  <td></td>
                </tr>
                <tr>
                  <td>最終更新日時</td>
                  <td>
                    <fmt:formatDate value="${requestDetail.request.lastUpdatedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                    <form:errors path="request.lastUpdatedDate" />
                    <input id="request.lastUpdatedDate" name="request.lastUpdatedDate" type="hidden" value='<fmt:formatDate value="${requestDetail.request.lastUpdatedDate}" pattern="yyyy-MM-dd hh:mm:ss" />'>
                  </td>
                  <td></td>
                </tr>
              </tbody>
            </table>
            <h3 id="contents2">メッセージ本文</h3>
            <div id="warningMessageArea">
            </div>
            <hr>
            <div id="messageBody" class="infomation-panel">
              ${requestDetail.request.requestContents}
            </div>
            <form:hidden path="request.requestContents"/>
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
                  <th>回答ステータス</th>
                  <th>回答</th>
                  <th>削除</th>
                </tr>
                <c:set var="userCount" value="0" />
                <c:forEach items="${requestDetail.approvedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="approvedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="approvedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
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
                  <td>
                    <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                  </td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
                <c:forEach items="${requestDetail.deniedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="deniedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="deniedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
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
                        <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == true}">×</c:when>
                        <c:when test="${requestStatus.id.requestId == requestDetail.request.requestId && requestStatus.isAnswered == false}">-</c:when>
                      </c:choose>
                    </c:forEach>
                  </td>
                  <td>
                    <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                  </td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
              </tbody>
            </table>
            <div class="centering">
              <button id="get-users-button" type="button" class="centering-button" >追加</button>
            </div>
            <form:button class="main-button" name="confirmUpdate" >更新を確定する</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>