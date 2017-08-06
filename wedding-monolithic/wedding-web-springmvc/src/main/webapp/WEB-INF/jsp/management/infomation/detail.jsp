<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/infomation/detail-flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateTextareaDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/infomation/detail.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
    <div id="flex-container">
      <div class="flex-item-1">
        <c:import url="/WEB-INF/jsp/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="formPanel">
            <form:form 
            action="${pageContext.request.contextPath}/management/infomation/${infomationDetail.infomation.infoId}" 
            modelAttribute="infomationDetail">
            <h3 id="contents1">インフォメーション</h3>
            <table>
              <tbody>
                <tr>
                  <td>インフォメーションID</td>
                  <td>
                    <c:out value="${infomationDetail.infomation.infoId}" />
                    <form:hidden path="infomation.infoId"/>
                  </td>
                  <td></td>
                </tr>
                <tr>
                  <td>タイトル</td>
                  <td>
                    <c:out value="${infomationDetail.infomation.title}" />
              	    <form:errors path="infomation.title" />
                    <form:hidden path="infomation.title"/>
                  </td>
                  <td><button id="infomation.title-button" name="infomation.title-button" type="button" value="infomation.title">変更</button></td>
                </tr>
                <tr>
                  <td>登録日時</td>
              	  <td>
              	    <fmt:formatDate value="${infomationDetail.infomation.registratedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
              	    <form:errors path="infomation.registratedDate" />
                    <input id="infomation.registratedDate" name="infomation.registratedDate" type="hidden" value='<fmt:formatDate value="${infomationDetail.infomation.registratedDate}" pattern="yyyy-MM-dd HH:mm:ss" />'>
              	  </td>
              	  <td></td>
                </tr>
                <tr>
                  <td>公開日時</td>
              	  <td>
              	    <fmt:formatDate value="${infomationDetail.infomation.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />
              	    <form:errors path="infomation.releaseDate" />
                    <input id="infomation.releaseDate" name="infomation.releaseDate" type="hidden" value='<fmt:formatDate value="${infomationDetail.infomation.releaseDate}" pattern="yyyy-MM-dd HH:mm:ss" />'>
              	  </td>
                  <td><button id="infomation.releaseDate-button" name="infomation.releaseDate-button" type="button" value="infomation.releaseDate">変更</button></td>
                </tr>
                <tr>
                  <td>最終更新日時</td>
              	  <td>
              	    <fmt:formatDate value="${infomationDetail.infomation.lastUpdatedDate}" pattern="yyyy/MM/dd hh:mm:ss" />
              	    <form:errors path="infomation.lastUpdatedDate" />
                    <input id="infomation.lastUpdatedDate" name="infomation.lastUpdatedDate" type="hidden" value='<fmt:formatDate value="${infomationDetail.infomation.lastUpdatedDate}" pattern="yyyy-MM-dd HH:mm:ss" />'>
              	  </td>
              	  <td></td>
                </tr>
              </tbody>
            </table>
            <h3 id="contents2">メッセージ本文</h3>
            <div id="warningMessageArea">
            </div>
            <hr>
            <div id="messageBody" class="infomation-panel">
              <c:import url="/WEB-INF/jsp/${infomationDetail.infomation.infoPagePath}"></c:import>
            </div>
            <form:hidden path="infomation.infoPagePath"/>
            <hr>
            <div class="centering">
              <button id="messageBody-button" name="messageBody-button" class="centering-button" type="button" value="messageBody">変更</button>
            </div>
            <h3 id="contents3">ユーザアクセス</h3>
            <table>
              <tbody>
                <tr>
                  <th>No</th>
                  <th>ユーザID</th>
                  <th>ユーザ名</th>
                  <th>アクセス状況</th>
                  <th>削除</th>
                </tr>
                <c:set var="userCount" value="0" />
                <c:forEach items="${infomationDetail.noAccessedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="noAccessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="noAccessedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
                  </td>
                  <td>未</td>
                  <td>
                    <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                  </td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
                <c:forEach items="${infomationDetail.accessedUsers}" var="user" varStatus="status">
                <tr>
                  <td>${userCount+1}</td>
                  <td>
                    <c:out value="${user.userId}" />
                    <input id="users[${userCount}].userId" name="accessedUsers[${status.index}].userId" type="hidden" value='<c:out value="${user.userId}" />'>
                  </td>
                  <td>
                    <c:out value="${user.userName}" />
                    <input id="users[${userCount}].userName" name="accessedUsers[${status.index}].userName" type="hidden" value='<c:out value="${user.userName}" />'>
                  </td>
                  <td>アクセス済</td>
                  <td>
                    <input type="checkbox" name="${userCount}" value="off" autocomplete="off" />
                  </td>
                </tr>
                <c:set var="userCount" value="${userCount+1}" />
                </c:forEach>
              </tbody>
            </table>
            <div class="centering">
              <button id="get-users-button" type="button" class="centering-button" >追加</button>
            </div>
            <form:button class="main-button" name="confirmUpdate" >更新を確定する</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>