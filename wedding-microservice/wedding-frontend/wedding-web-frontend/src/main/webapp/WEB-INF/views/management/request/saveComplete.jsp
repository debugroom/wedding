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
<title>Complete new request</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/saveComplete.css">
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
            <p>以下の通り、依頼が追加されました。</p>
            <table>
              <tbody>
                <tr>
                  <td>リクエストID</td>
                  <td><c:out value="${request.requestId}" /></td>
                </tr>
                <tr>
                  <td>タイトル</td>
                  <td><c:out value="${request.title}" /></td>
                </tr>
                <tr>
                  <td>登録日時</td>
                  <td><fmt:formatDate value="${request.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" /></td>
                </tr>
              </tbody>
            </table>
            <h3>依頼内容</h3>
            <hr>
            <div id="messageBody" class="request-panel">
            ${request.requestContents}
            </div>
            <h3>依頼対象ユーザ</h3>
            <table class="userTable">
              <tbody>
                <tr>
                  <th>No</th>
                  <th>ユーザID</th>
                  <th>ユーザ名</th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="status">
                  <tr>
                    <td>${status.index+1}</td>
                    <td>
                      <c:out value="${user.userId}" />
                    </td>
                    <td>
                      <c:out value="${user.lastName}" /> <c:out value="${user.firstName}" />
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="alternative-button">
              <button class="alternative-first-button" type="button"
                  onclick="location.href='/management/request/new'" >続けて新規作成する</button>
              <button class="alternative-last-button" type="button"
                  onclick="location.href='/management/request/portal'" >依頼事項一覧に戻る</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>
