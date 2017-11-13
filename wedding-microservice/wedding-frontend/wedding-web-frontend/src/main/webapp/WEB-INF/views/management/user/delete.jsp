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
<title>Delete User</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/delete.css">
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
            <p>以下のユーザを削除します。よろしければ、確定ボタンを押してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/user/delete/${deleteUser.userId}" 
                modelAttribute="deleteUser">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="userId">ユーザID</form:label></td>
                    <td>
                      <c:out value="${deleteUser.userId}" />
                      <form:hidden path="userId" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="firstName">ユーザ名</form:label></td>
                    <td>
                      <c:out value="${deleteUser.lastName}" /> <c:out value="${deleteUser.firstName}" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td>
                      <div class="imgPanel">
                          <c:choose>
                            <c:when test='${fn:endsWith(deleteUser.imageFilePath,"png")}'>
                              <img src='${pageContext.request.contextPath}/profile/image/<c:out value="${deleteUser.userId}/xxxx.png" />'>
                            </c:when>
                            <c:when test='${fn:endsWith(deleteUser.imageFilePath,"jpg")}'>
                              <img src='${pageContext.request.contextPath}/profile/image/<c:out value="${deleteUser.userId}/xxxx.jpg" />'>
                            </c:when>
                            <c:when test='${fn:endsWith(deleteUser.imageFilePath,"jpeg")}'>
                              <img src='${pageContext.request.contextPath}/profile/image/<c:out value="${deleteUser.userId}/xxxx.jpg" />'>
                            </c:when>
                            <c:when test='${fn:endsWith(deleteUser.imageFilePath,"gif")}'>
                              <img src='${pageContext.request.contextPath}/profile/image/<c:out value="${deleteUser.userId}/xxxx.gif" />'>
                            </c:when>
                          </c:choose>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>
                      <c:out value="${deleteUser.loginId}" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>
                      <c:out value="${deleteUser.authorityLevel}" />
                    </td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>
                      <c:out value="${deleteUser.address.postCd}" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>
                      <c:out value="${deleteUser.address.address}" />
                    </td>
                  </tr>
                  <c:forEach items="${deleteUser.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                      </td>
                    </tr>
                  </c:forEach>
                  <c:forEach items="${deleteUser.grps}" var="group" varStatus="status">
                    <tr>
                      <td><label for="groups[${status.index}]">所属グループ</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${group.groupName}" />
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="delete">このユーザを削除する</form:button>
                <form:button class="alternative-last-button" name="type" value="return">ユーザ一覧へ戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>