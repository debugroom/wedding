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
      <div class="panel">
      <div class="resultPanel">
        <p>以下の通り、ユーザが追加されました。</p>
        <spring:nestedPath path="user">
          <table>
            <tbody>
              <tr>
                <td>ユーザID</td>
                <td><c:out value="${user.userId}" /></td>
              </tr>
              <tr>
                <td>ユーザ名</td>
                <td><c:out value="${user.userName}" /></td>
              </tr>
              <tr>
                <td>ピクチャ</td>
                <td><img src="${pageContext.request.contextPath}/${user.imageFilePath}"></td>
              </tr>
              <tr>
                <td>ログインID</td>
                <td><c:out value="${user.loginId}" /></td>
              </tr>
              <tr>
                <td>権限レベル</td>
                <td><c:out value="${user.authorityLevel}" /></td>
              </tr>
              <tr>
                <td>パスワード(セキュリティのため、非表示としています)</td>
                <td>*****</td>
              </tr>
              <tr>
                <td>郵便番号</td>
                <td><c:out value="${user.address.postCd}" /></td>
              </tr>
              <tr>
                <td>住所</td>
                <td><c:out value="${user.address.address}" /></td>
              </tr>
              <c:forEach items="${user.emails}" var="email" varStatus="status">
                <tr>
                  <td>Email - ${status.index+1}</td>
                  <td><c:out value="${email.email}" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </spring:nestedPath>
      </div>
      </div>
    </div>
  </div>
</body>