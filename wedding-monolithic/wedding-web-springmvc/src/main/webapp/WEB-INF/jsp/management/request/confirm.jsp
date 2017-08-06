<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/request/confirm-flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        以下の依頼事項を作成します。よろしければ、登録ボタンを押下してください。
        <div class="requestFormPanel">
          <form:form action="${pageContext.request.contextPath}/management/request/new/${requestDraft.request.requestId}"
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
                    <form:errors path="request.title" cssStyle="color:red" />
                  </td>
                </tr>
                <tr>
                  <td><form:label path="request.registratedDate">登録日時</form:label></td>
                  <td>
                    <c:out value="${requestDraft.request.registratedDate}" />
                    <input type="hidden" name="registratedDate" value="<c:out value='${requestDraft.request.registratedDate}' />" /><br/>
                    <form:errors path="request.registratedDate" cssStyle="color:red" />
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
                        <c:out value="${user.userName}" />
                        <input type="hidden" name="checkedUsers[${userCount}].userName" value="${user.userName}" />
                      </td>
                    </tr>
                    <c:set var="userCount" value="${userCount + 1}" />
                    </c:if>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="save" >登録</form:button>
                <form:button class="alternative-last-button" name="type" value="return">戻る</form:button>
              </div>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</body>