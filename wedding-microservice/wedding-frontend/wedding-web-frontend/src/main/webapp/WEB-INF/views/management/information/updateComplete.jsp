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
<title>Information Update Complete</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/updateComplete.css">
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
         <p>以下の通り、インフォメーションが変更されました。</p>
         <div class="diffPanel">
           <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
             <c:choose>
               <c:when test="${updateParam == 'information.title'}">
                 <table>
                   <tbody>
                     <tr>
                       <th colspan="2">タイトル</th>
                     </tr>
                     <tr>
                       <th>更新前</th>
                       <th>更新後</th>
                     </tr>
                     <tr>
                       <td>
                         <spring:nestedPath path="updateResult.beforeEntity">
                           <spring:bind path="information.title">${status.value}</spring:bind>
                         </spring:nestedPath>
                       </td>
                       <td>
                         <spring:nestedPath path="updateResult.afterEntity">
                           <spring:bind path="information.title">${status.value}</spring:bind>
                         </spring:nestedPath>
                       </td>
                     </tr>
                   </tbody>
                 </table>
               </c:when>
               <c:when test="${updateParam == 'information.releaseDate'}">
                 <table>
                   <tbody>
                     <tr>
                       <th colspan="2">公開日時</th>
                     </tr>
                     <tr>
                       <th>更新前</th>
                       <th>更新後</th>
                     </tr>
                     <tr>
                       <td>
                         <fmt:formatDate value="${updateResult.beforeEntity.information.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                       </td>
                       <td>
                         <fmt:formatDate value="${updateResult.afterEntity.information.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                       </td>
                     </tr>
                   </tbody>
                 </table>
               </c:when>
             </c:choose>
           </c:forEach>
         </div>
         <div>
           <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
             <c:choose>
               <c:when test="${updateParam == 'messageBody'}">
                 <hr>
                 <h3>メッセージ本文</h3>
                 <h4>変更前</h4>
                 <div id="messageBody" class="information-panel">
                   <c:out value="${updateResult.beforeMessageBody}" />
                 </div>
                 <hr>
                 <h4>変更後</h4>
                 <div id="messageBody" class="information-panel">
                   <c:out value="${updateResult.afterMessageBody}" />
                 </div>
                 <hr>
                 </c:when>
               </c:choose>
             </c:forEach>
             <c:set var="userCount" value="0"/>
             <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
               <c:choose>
                 <c:when test="${fn:startsWith(updateParam, 'viewUser-')}">
                   <c:if test="${userCount == 0}">
                     <table>
                       <tbody>
                         <tr>
                           <th>No</th>
                           <th>ユーザID</th>
                           <th>ユーザ名</th>
                           <th></th>
                         </tr>
                   </c:if>
                   <c:forEach items="${updateResult.afterEntity.accessedUsers}" var="user" varStatus="status1">
                     <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                         <tr>
                           <td><c:out value="${userCount+1}" /></td>
                           <td><c:out value="${user.userId}" /></td>
                           <td><c:out value="${user.lastName}" /> <c:out value="${user.firstName}" /></td>
                           <td>追加</td>
                         </tr>
                     </c:if>
                   </c:forEach>
                   <c:forEach items="${updateResult.afterEntity.noAccessedUsers}" var="user" varStatus="status1">
                     <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                         <tr>
                           <td><c:out value="${userCount+1}" /></td>
                           <td><c:out value="${user.userId}" /></td>
                           <td><c:out value="${user.lastName}" /> <c:out value="${user.firstName}" /></td>
                           <td>追加</td>
                         </tr>
                     </c:if>
                   </c:forEach>
                   <c:set var="userCount" value="${userCount+1}"/>
                 </c:when>
                 <c:when test="${fn:startsWith(updateParam, 'excludeUser-')}">
                   <c:if test="${userCount == 0}">
                     <table>
                       <tbody>
                         <tr>
                           <th>No</th>
                           <th>ユーザID</th>
                           <th>ユーザ名</th>
                           <th></th>
                         </tr>
                   </c:if>
                   <c:forEach items="${updateResult.beforeEntity.accessedUsers}" var="user" varStatus="status1">
                     <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                         <tr>
                           <td><c:out value="${userCount+1}" /></td>
                           <td><c:out value="${user.userId}" /></td>
                           <td><c:out value="${user.lastName}" /> <c:out value="${user.firstName}" /></td>
                           <td>削除</td>
                         </tr>
                     </c:if>
                   </c:forEach>
                   <c:forEach items="${updateResult.beforeEntity.noAccessedUsers}" var="user" varStatus="status1">
                     <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                         <tr>
                           <td><c:out value="${userCount+1}" /></td>
                           <td><c:out value="${user.userId}" /></td>
                           <td><c:out value="${user.lastName}" /> <c:out value="${user.firstName}" /></td>
                           <td>削除</td>
                         </tr>
                     </c:if>
                   </c:forEach>
                   <c:set var="userCount" value="${userCount+1}"/>
                 </c:when>
               </c:choose>
             </c:forEach>
             <c:if test="${userCount > 0}">
                       </tbody>
                     </table>
             </c:if>            
          <button class="main-button" type="button"
                onclick="location.href='${pageContext.request.contextPath}/management/information/portal'" >インフォメーション一覧に戻る</button>
         </div>
       </div>
    </div>
  </article>
</body>