<%@ page contentType="text/html; charset=UTF-8" %>
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
<title>User Delete Complete</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/deleteComplete.css">
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
          <div class="resultPanel">
            <p>以下のユーザを削除しました。</p>
              <spring:nestedPath path="deleteUser">
                <table>
                  <tbody>
                    <tr>
                      <td>ユーザID</td>
                      <td><c:out value="${deleteUser.userId}" /></td>
                    </tr>
                    <tr>
                      <td>ユーザ名</td>
                      <td><c:out value="${deleteUser.lastName}" /> <c:out value="${deleteUser.firstName}" /></td>
                    </tr>
                    <tr>
                      <td>ピクチャ</td>
                      <td>
                        <div class="imgPanel">
                          <c:choose>
                            <c:when test='${fn:endsWith(deleteUser.imageFilePath,"png")}'>
                              <img src='/image/<c:out value="${deleteUser.userId}" />/xxxx.png?imageFilePath=<c:out value="${deleteUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(deleteUser.imageFilePath,"jpeg")}'>
                            <img src='/image/<c:out value="${deleteUser.userId}" />/xxxx.jpeg?imageFilePath=<c:out value="${deleteUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(deleteUser.imageFilePath,"jpg")}'>
                            <img src='/image/<c:out value="${deleteUser.userId}" />/xxxx.jpg?imageFilePath=<c:out value="${deleteUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(deleteUser.imageFilePath,"gif")}'>
                            <img src='/image/<c:out value="${deleteUser.userId}" />/xxxx.gif?imageFilePath=<c:out value="${deleteUser.imageFilePath}" />'>
                          </c:when>
                          </c:choose>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>ログインID</td>
                      <td><c:out value="${deleteUser.loginId}" /></td>
                    </tr>
                    <tr>
                      <td>権限レベル</td>
                      <td><c:out value="${deleteUser.authorityLevel}" /></td>
                    </tr>
                    <tr>
                      <td>パスワード</td>
                      <td>*****<br>(セキュリティのため、非表示としています)</td>
                    </tr>
                    <tr>
                      <td>郵便番号</td>
                      <td><c:out value="${deleteUser.address.postCd}" /></td>
                    </tr>
                    <tr>
                      <td>住所</td>
                      <td><c:out value="${deleteUser.address.address}" /></td>
                    </tr>
                  <c:forEach items="${deleteUser.emails}" var="email" varStatus="status">
                    <tr>
                      <td>Email - ${status.index+1}</td>
                      <td><c:out value="${email.email}" /></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </spring:nestedPath>
            <button class="main-button" type="button"
                  onclick="location.href='/management/user/portal'" >ユーザ一覧に戻る</button>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>