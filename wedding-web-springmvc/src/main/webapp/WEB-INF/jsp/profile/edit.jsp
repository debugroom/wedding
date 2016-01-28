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
    <header>
        <h1>
            <img src="${pageContext.request.contextPath}/resources/app/img/icon_6m_96.png">
            Web site for wedding
            <img src="${pageContext.request.contextPath}/resources/app/img/icon_6m_96.png">
        </h1>
    </header>
    <div id="flex-container">
    	<div class="flex-item-1">
            <d:Menu/>
    	</div>
    	<div class="flex-item-2">
    	以下の通り、更新されました。
    	<div class="resultPanel">
    	<c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
    	<table>
    	   <tbody>
    	           <c:choose>
    	               <c:when test="${updateParam == 'userName'}">
    	       <tr>
    	           <th colspan="2">ユーザ名</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
                           <td>
    	                       <spring:nestedPath path="updateResult.beforeEntity" >
    	                           <spring:bind path="userName">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
                           <td>
    	                       <spring:nestedPath path="updateResult.afterEntity" >
    	                           <spring:bind path="userName">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
    	               </c:when>
    	               <c:when test="${updateParam == 'loginId'}">
    	       <tr>
    	           <th colspan="2">ログインID</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
                           <td>
    	                       <spring:nestedPath path="updateResult.beforeEntity" >
    	                           <spring:bind path="loginId">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
                	       <td>
    	                       <spring:nestedPath path="updateResult.afterEntity" >
                                   <spring:bind path="loginId">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
    	               </c:when>
    	               <c:when test="${updateParam == 'imageFile'}">
    	       <tr>
    	           <th colspan="2">ピクチャ</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
                	       <td>
    	                       <spring:nestedPath path="updateResult.beforeEntity" >
    	                           <spring:bind path="imageFilePath">
        	                           <img src="${pageContext.request.contextPath}/${status.value}">
    	                           </spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
                	       <td>
    	                       <spring:nestedPath path="updateResult.afterEntity" >
    	                           <spring:bind path="imageFilePath">
        	                           <img src="${pageContext.request.contextPath}/${status.value}">
    	                           </spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
    	               </c:when>
    	               <c:when test="${updateParam == 'address.postCd'}">
    	       <tr>
    	           <th colspan="2">郵便番号</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
                	       <td>
    	                       <spring:nestedPath path="updateResult.beforeEntity" >
    	                           <spring:bind path="address.postCd">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
                	       <td>
    	                       <spring:nestedPath path="updateResult.afterEntity" >
    	                           <spring:bind path="address.postCd">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
    	               </c:when>
    	               <c:when test="${updateParam == 'address.address'}">
    	       <tr>
    	           <th colspan="2">住所</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
                	       <td>
    	                       <spring:nestedPath path="updateResult.beforeEntity" >
    	                           <spring:bind path="address.address">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
                	       <td>
    	                       <spring:nestedPath path="updateResult.afterEntity" >
    	                           <spring:bind path="address.address">${status.value}</spring:bind>
    	                       </spring:nestedPath>
    	                   </td>
    	               </c:when>
    	               <c:when test="${fn:startsWith(updateParam, 'emails')}">
    	       <tr>
    	           <th colspan="2">Email # ${fn:substringAfter(updateParam, '#')}</th>
    	       </tr>
    	       <tr>
    	           <th>更新前</th>
    	           <th>更新後</th>
    	       </tr>
    	           <tr>
    	                   <td>
    	                       <c:forEach var="email" items="${updateResult.beforeEntity.emails}">
    	                           <c:if test="${email.id.emailId == fn:substringAfter(updateParam, '#')}">
    	                           ${email.email}
    	                           </c:if>
    	                       </c:forEach>
    	                   </td>
    	                   <td>
    	                       <c:forEach var="email" items="${updateResult.afterEntity.emails}">
    	                           <c:if test="${email.id.emailId == fn:substringAfter(updateParam, '#')}">
    	                           ${email.email}
    	                           </c:if>
    	                       </c:forEach>
    	                   </td>
    	               </c:when>
    	           </c:choose>
    	       </tr>
    	   </tbody>
    	</table>
    	</c:forEach>
    	</div>
    	</div>
    </div>
</body>