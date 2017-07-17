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
<title>Complete new user</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/saveComplete.css">
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
            <p>以下の通り、ユーザが作成されました。</p>
              <spring:nestedPath path="newUser">
                <table>
                  <tbody>
                    <tr>
                      <td>ユーザID</td>
                      <td><c:out value="${newUser.userId}" /></td>
                    </tr>
                    <tr>
                      <td>ユーザ名</td>
                      <td><c:out value="${newUser.lastName}" /> <c:out value="${newUser.firstName}" /></td>
                    </tr>
                    <tr>
                       <td>ピクチャ</td>
                       <td>
                         <div class="imgPanel">
                           <c:choose>
                             <c:when test='${fn:endsWith(newUser.imageFilePath,"png")}'>
                               <img src="${pageContext.request.contextPath}/profile/image/${newUser.userId}/xxxx.png">
                             </c:when>
                             <c:when test='${fn:endsWith(newUser.imageFilePath,"jpeg")}'>
                               <img src="${pageContext.request.contextPath}/profile/image/${newUser.userId}/xxxx.jpeg">
                             </c:when>
                             <c:when test='${fn:endsWith(newUser.imageFilePath,"jpg")}'>
                               <img src="${pageContext.request.contextPath}/profile/image/${newUser.userId}/xxxx.jpg">
                             </c:when>
                             <c:when test='${fn:endsWith(newUser.imageFilePath,"gif")}'>
                               <img src="${pageContext.request.contextPath}/profile/image/${newUser.userId}/xxxx.gif">
                             </c:when>
                           </c:choose>
                         </div>
                       </td>
                    </tr>
                    <tr>
                       <td>ログインID</td>
                       <td><c:out value="${newUser.loginId}" /></td>
                    </tr>
                    <tr>
                       <td>権限レベル</td>
                       <td><c:out value="${newUser.authorityLevel}" /></td>
                    </tr>
                    <tr>
                       <td>パスワード</td>
                       <td>*****<br>(セキュリティのため、非表示としています)</td>
                    </tr>
                    <tr>
                      <td>郵便番号</td>
                      <td><c:out value="${newUser.address.postCd}" /></td>
                    </tr>
                    <tr>
                      <td>住所</td>
                      <td><c:out value="${newUser.address.address}" /></td>
                    </tr>
                    <c:forEach items="${newUser.emails}" var="email" varStatus="status">
                      <tr>
                         <td>Email - ${status.index+1}</td>
                         <td><c:out value="${email.email}" /></td>
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
                      </td>
                    </tr>
                  </tbody>
               </table>
             </spring:nestedPath>
             <div class="alternative-button">
               <button class="alternative-first-button" type="button"
                  onclick="location.href='/management/user/new'" >続けて新規作成する</button>
               <button class="alternative-last-button" type="button"
                  onclick="location.href='/management/user/portal'" >ユーザ一覧に戻る</button>
             </div>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>