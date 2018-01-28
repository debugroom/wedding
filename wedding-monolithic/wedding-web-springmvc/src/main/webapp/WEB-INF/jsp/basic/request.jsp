<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/basic/request-flex.css">
</head>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <h1>ご依頼事項</h1>
        <h2><c:out value="${request.title}" /></h2>
        最終更新日時：<fmt:formatDate value="${request.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
        <form:form action="${pageContext.request.contextPath}/request/${request.requestId}/response" modelAttribute="request">
          <div id="messageBody" class="message-panel">
            ${request.requestContents}
          </div>
        </form:form>
      </div>
    </div>
  </div>
</body>