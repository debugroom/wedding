<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/infomation/delete-flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        以下のインフォメーションを削除します。よろしければ、登録ボタンを押下してください。
        <div class="infomationFormPanel">
          <form:form action="${pageContext.request.contextPath}/management/infomation/delete/${infomationDetail.infomation.infoId}" 
                modelAttribute="infomationDetail">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="infomation.infoId">インフォID</form:label></td>
                    <td>
                      <c:out value="${infomationDetail.infomation.infoId}" />
                      <input type="hidden" name="infoId" value="<c:out value='${infomationDetail.infomation.infoId}' />" /><br/>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.title">タイトル</form:label></td>
                    <td>
                      <c:out value="${infomationDetail.infomation.title}" />
                      <input type="hidden" name="title" value="<c:out value='${infomationDetail.infomation.title}' />" /><br/>
                      <form:errors path="infomation.title" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.registratedDate">登録日時</form:label></td>
                    <td>
                      <c:out value="${infomationDetail.infomation.registratedDate}" />
                      <input type="hidden" name="registratedDate" value="<c:out value='${infomationDetail.infomation.registratedDate}' />" /><br/>
                      <form:errors path="infomation.registratedDate" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="infomation.releaseDate">公開日時</form:label></td>
                    <td>
                      <c:out value="${infomationDetail.infomation.releaseDate}" />
                      <input type="hidden" name="releaseDate" value="<c:out value='${infomationDetail.infomation.releaseDate}' />" /><br/>
                      <form:errors path="infomation.releaseDate" cssStyle="color:red" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="messageBody" class="infomation-panel">
                <c:import url="/WEB-INF/jsp/${infomationDetail.infomation.infoPagePath}"></c:import>
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
                <c:forEach items="${infomationDetail.noAccessedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="noAccessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="noAccessedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
                  </td>
                  <td>未</td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
                <c:forEach items="${infomationDetail.accessedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="accessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="accessedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
                  </td>
                  <td>アクセス済</td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="delete" >削除</form:button>
                <form:button class="alternative-last-button" name="type" value="return">戻る</form:button>
              </div>
            </form:form>
        </div>
      </div>
    </div>
  </div>
</body>