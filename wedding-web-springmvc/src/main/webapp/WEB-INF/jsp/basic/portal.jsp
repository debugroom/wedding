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
    	   <div class="panel">
           <div class="imgPanel">
           <img src="${pageContext.request.contextPath}${portalInfoOutput.user.imageFilePath}" alt="" />
           </div>
 <!--  
           <div class="circlePanel">
           </div>
 -->
    	       <h2>${portalInfoOutput.user.userName} さん</h2>
    	       <p>前回ログイン日時：<fmt:formatDate value="${portalInfoOutput.user.lastLoginDate}" pattern="yyyy-MM-dd hh:mm:ss" /> </p>
        <div class="infomationPanel">
    	       <h3>お知らせ</h3>
    	   <table>
    	       <thead>
    	           <tr>
    	               <th>更新</th>
    	               <th>お知らせ</th>
    	           </tr>
    	       </thead>
    	       <tbody>
    	   <c:forEach var="infomation" items="${portalInfoOutput.infomationList}" varStatus="status">
    	           <tr>
    	               <td><fmt:formatDate value="${portalInfoOutput.user.lastLoginDate}" pattern="yyyy-MM-dd" /> </td>
    	               <td><a href="${pageContext.request.contextPath}/${infomation.infoPagePath}">${infomation.title}</a></td>
    	           </tr>
    	   </c:forEach>
    	       </tbody>
    	   </table>
    	   </div>
        </div>
    	</article>
    	</div>
    	<div class="flex-item">
    	</div>
    </div>
</body>
</html>