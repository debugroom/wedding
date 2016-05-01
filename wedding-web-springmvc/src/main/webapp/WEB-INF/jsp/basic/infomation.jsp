<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/basic/infomation-flex.css">
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
        <h1>お知らせ</h1>
        <h2><c:out value="${infomation.title}" /></h2>
        最終更新日時：<fmt:formatDate value="${infomation.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
        <div>
          <div id="messageBody" class="infomation-panel">
            <c:import url="/WEB-INF/jsp/${infomation.infoPagePath}"></c:import>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>