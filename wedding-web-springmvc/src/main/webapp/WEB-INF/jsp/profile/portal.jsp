<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/profile.js"></script>
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
    	<article>
    		<div class="panel">
    			<div class="formPanel">
    				<form:form action="${pageContext.request.contextPath}/profile/edit"
						modelAttribute="user">
						<form:hidden path="userId"/>
						<table>
							<tbody>
								<tr>
									<td><form:label path="userName">ユーザ名</form:label> : </td>
									<td>${user.userName}<form:hidden path="userName"/></td>
									<td><form:button name="updateParam" value="userName" >更新</form:button></td>
								</tr>
								<tr>
									<td><form:label path="imageFilePath">ピクチャ</form:label> : </td>
									<td><img src="${pageContext.request.contextPath}${user.imageFilePath}"><form:hidden path="imageFilePath"/></td>
									<td><form:button name="updateParam" value="imageFile" >更新</form:button></td>
								</tr>
								<tr>
									<td><form:label path="loginId">ログインID</form:label> : </td>
									<td>${user.loginId}<form:hidden path="loginId"/></td>
									<td><form:button name="updateParam" value="loginId" >更新</form:button></td>
								</tr>
								<tr>
									<td><form:label path="address.postCd">郵便番号</form:label> : </td>
									<td>${user.address.postCd}<form:hidden path="address.postCd"/></td>
									<td><form:button name="updateParam" value="address.postCd" >更新</form:button></td>
								</tr>
								<tr>
									<td><form:label path="address.address">住所</form:label> : </td>
									<td>${user.address.address}<form:hidden path="address.address"/></td>
									<td><form:button name="updateParam" value="address.address" >更新</form:button></td>
								</tr>
    	   						<c:forEach items="${user.emails}" var="email" varStatus="status">
								<tr>
									<td><label for="emails[${status.index}].email">Email</label>${status.index+1} : </td>
									<td>${email.email}<input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value="${email.email}">
									<td><form:button name="updateParam" value="email_${status.index}" >更新</form:button></td>
								</tr>
    							</c:forEach>
							</tbody>
						</table>
					</form:form>
    			</div>
    	   </div>
    	</article>
    	</div>
    	<div class="flex-item">
    	</div>
    </div>
</body>
</html>