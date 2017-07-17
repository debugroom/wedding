<%@ page contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://debugroom.org/tags" prefix="d"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Confirm new user</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/confirm.css">
</head>
<body>
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <article>
    <div id="flex-container">
      <div class="flex-item-1">
       <c:import url="/WEB-INF/views/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="formPanel">
            <p>以下の内容でユーザ情報を作成します。よろしければ、下の作成ボタンを押下してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/user/new/${newUser.userId}" 
                modelAttribute="newUser">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="userId">ユーザID</form:label></td>
                    <td>
                      <c:out value="${newUser.userId}" />
                      <form:hidden path="userId" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <form:label path="lastName">苗字</form:label>
                      <form:label path="firstName">名前</form:label>
                    </td>
                    <td>
                      <c:out value="${newUser.lastName}" />
                      <c:out value="${newUser.firstName}" />
                      <form:hidden path="lastName" />
                      <form:hidden path="firstName" />
                      <br>
                      <form:errors path="firstName" cssStyle="color:red" />
                      <form:errors path="lastName" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td>
                      <div class="imgPanel">
                        <c:choose>
                          <c:when test='${fn:endsWith(newUser.imageFilePath,"png")}'>
                            <img src='/image/<c:out value="${newUser.userId}" />/xxxx.png?imageFilePath=<c:out value="${newUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(newUser.imageFilePath,"jpeg")}'>
                            <img src='/image/<c:out value="${newUser.userId}" />/xxxx.jpeg?imageFilePath=<c:out value="${newUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(newUser.imageFilePath,"jpg")}'>
                            <img src='/image/<c:out value="${newUser.userId}" />/xxxx.jpg?imageFilePath=<c:out value="${newUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(newUser.imageFilePath,"gif")}'>
                            <img src='/image/<c:out value="${newUser.userId}" />/xxxx.gif?imageFilePath=<c:out value="${newUser.imageFilePath}" />'>
                          </c:when>
                        </c:choose>
                      </div>
                      <form:hidden path="imageFilePath" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>
                      <c:out value="${newUser.loginId}" />
                      <form:hidden path="loginId" /><br>
                      <form:errors path="loginId" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>
                      <c:out value="${newUser.authorityLevel}" />
                      <form:hidden path="authorityLevel" /><br>
                      <form:errors path="authorityLevel" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br>
                      <form:errors path="credentials[0].credentialKey" cssStyle="color:red" />
                      <form:hidden path="credentials[0].credentialKey" />
                      <form:hidden path="credentials[1].credentialKey" />
                      <form:hidden path="credentials[0].id.credentialType" />
                      <form:hidden path="credentials[1].id.credentialType" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>
                      <c:out value="${newUser.address.postCd}" />
                      <form:hidden path="address.postCd" /><br>
                      <form:errors path="address.postCd" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>
                      <c:out value="${newUser.address.address}" />
                      <form:hidden path="address.address" /><br>
                      <form:errors path="address.address" cssStyle="color:red" />
                    </td>
                  </tr>
                  <c:forEach items="${newUser.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                        <form:errors path="emails[${status.index}].email" cssStyle="color:red" />
                        <input id="emails[${status.index}].id.emailId" name="emails[${status.index}].id.emailId" type="hidden" value='<c:out value="${email.id.emailId}" />' />
                        <input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value='<c:out value="${email.email}" />' />
                      </td>
                    </tr>
                 </c:forEach>
                    <tr>
                      <td>列席</td>
                      <td>
                        <c:choose>
                          <c:when test='${newUser.brideSide == true}'>
                            新婦側
                          </c:when>
                          <c:when test='${newUser.brideSide == false}'>
                            新郎側
                          </c:when>
                        </c:choose>
                      <form:hidden path="brideSide" />
                      </td>
                    </tr>
               </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="save" >ユーザ作成</form:button>
                <form:button class="alternative-last-button" name="type" value="return">入力画面に戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>