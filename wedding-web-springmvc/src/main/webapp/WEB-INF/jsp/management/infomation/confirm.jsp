<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/infomation/confirm-flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
    <div id="flex-container">
      <div class="flex-item-1">
        <c:import url="/WEB-INF/jsp/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="infomationFormPanel">
            <form:form action="${pageContext.request.contextPath}/management/infomation/new/${infomationDraft.infomation.infoId}" 
                modelAttribute="infomationDraft">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="infomation.infoId">インフォID</form:label></td>
                    <td>
                      <c:out value="${infomationDraft.infomation.infoId}" />
                      <input type="hidden" name="infoId" value="<c:out value='${infomationDraft.infomation.infoId}' />" /><br/>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.title">タイトル</form:label></td>
                    <td>
                      <c:out value="${infomationDraft.infomation.title}" />
                      <input type="hidden" name="title" value="<c:out value='${infomationDraft.infomation.title}' />" /><br/>
                      <form:errors path="infomation.title" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infoName">インフォメーション名</form:label></td>
                    <td>
                      <c:out value="${infomationDraft.infoName}" />
                      <input type="hidden" name="infoName" value="<c:out value='${infomationDraft.infoName}' />" /><br/>
                      <form:errors path="infoName" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.registratedDate">登録日時</form:label></td>
                    <td>
                      <c:out value="${infomationDraft.infomation.registratedDate}" />
                      <input type="hidden" name="registratedDate" value="<c:out value='${infomationDraft.infomation.registratedDate}' />" /><br/>
                      <form:errors path="infomation.registratedDate" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.releaseDate">公開日時</form:label></td>
                    <td>
                      <c:out value="${infomationDraft.infomation.releaseDate}" />
                      <input type="hidden" name="releaseDate" value="<c:out value='${infomationDraft.infomation.releaseDate}' />" /><br/>
                      <form:errors path="infomation.releaseDate" cssStyle="color:red" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="messageBody" class="infomation-panel">
                <c:import url="/WEB-INF/jsp/${infomationDraft.infomation.infoPagePath}"></c:import>
              </div>
              <input type="hidden" name="messageBody" value="<c:out value='${infomationDraft.messageBody}' />" /><br/>
              <input type="hidden" name="infoPagePath" value="<c:out value='${infomationDraft.infomation.infoPagePath}' />" /><br/>
              <h3>メッセージ通知先</h3>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                  </tr>
                  <c:set var="userCount" value="0" />
                  <c:forEach items="${infomationDraft.viewUsers}" var="user" varStatus="status">
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