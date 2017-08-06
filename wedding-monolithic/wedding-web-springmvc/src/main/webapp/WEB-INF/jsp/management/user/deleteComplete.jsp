<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/user/deleteComplete-flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="resultPanel">
          <p>以下のユーザを削除しました。</p>
          <c:import url="/WEB-INF/jsp/common/userComplete.jsp" />
        </div>
      </div>
    </div>
  </div>
</body>