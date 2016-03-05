<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
</head>
<body>
    <c:import url="/WEB-INF/jsp/common/header.jsp" />
    <div id="flex-container">
        <div class="flex-item-1">
            <c:import url="/WEB-INF/jsp/common/menu.jsp" />
        </div>
        <div class="flex-item-2">
        <article>
            <p><spring:message code="error.common.message" arguments="${errorCode}"/></p>
        </article>
        </div>
    </div>
</body>