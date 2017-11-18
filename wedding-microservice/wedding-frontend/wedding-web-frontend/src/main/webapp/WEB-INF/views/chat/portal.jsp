
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
<title>Chat</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/chat/portal.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/sockjs-client/sockjs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/stomp-websocket/stomp.js"></script>
</head>
<body class="back-img">
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <c:import url="/WEB-INF/views/common/lined-menu.jsp" />
  <div id="flex-container">
    <div class="flex-item-2">
      <article>
        <div class="chat-panel">
          <div id="tab" class="tab-panel">
            <ul>
              <c:forEach var="messageBoard" items="${chatPortalResource.messageBoards}" varStatus="status">
                <li id="tab-<c:out value='${messageBoard.messageBoardId}' />" class="tab" 
                  data-user-id="<c:out value='${user.userId}' />" data-request-context-path="${pageContext.request.contextPath}"
                  data-message-board-id="<c:out value='${messageBoard.messageBoardId}' />"
                  data-messages-url="${pageContext.request.contextPath}/chat/message-board/<c:out value='${messageBoard.messageBoardId}' />">
                  <c:out value="${messageBoard.title}" />
                </li>
              </c:forEach>
            </ul>
            <img id="add-group-icon" class="add-group-icon"
                  data-url="${pageContext.request.contextPath}/chat/group/new"
                  data-get-users-url="${pageContext.request.contextPath}/chat/users"
                  src="${pageContext.request.contextPath}/static/resources/app/img/add.png" />
          </div>
          <div id="content" class="content-panel">
            <c:forEach var="messageBoard" items="${chatPortalResource.messageBoards}" varStatus="status">
              <div id="content-${messageBoard.messageBoardId}" class="content">
              </div>
            </c:forEach>
         </div>
        </div>
      </article>
    </div>
  </div>
</body>
<script type="text/javascript" 
  src="${pageContext.request.contextPath}/static/resources/app/js/chat/portal.js"></script>