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
            <form:form action="${pageContext.request.contextPath}/management/infomation/new/${infomation.infoId}" 
                modelAttribute="infomation">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="infoId"></form:label></td>
                    <td>
                      <c:out value="${infomation.infoId}" />
                      <form:hidden path="infoId" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="title"></form:label></td>
                    <td>
                      <c:out value="${infomation.title}" />
                      <form:hidden path="title" /><br>
                      <form:errors path="title" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="registratedDate"></form:label></td>
                    <td>
                      <c:out value="${infomation.registratedDate}" />
                      <form:hidden path="registratedDate" />
                      <form:errors path="registratedDate" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId"></form:label></td>
                    <td>
                      <c:out value="${infomation.releaseDate}" />
                      <form:hidden path="releaseDate" /><br>
                      <form:errors path="releaseDate" cssStyle="color:red" />
                    </td>
                  </tr>
                </tbody>
              </table>
              <h3 id="contents2">メッセージ本文</h3>
              <div id="messageBody" class="infomation-panel">
                <c:import url="/WEB-INF/jsp/${infomation.infoPagePath}"></c:import>
              </div>
              <form:hidden path="infoPagePath"/>
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