<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://debugroom.org/tags" prefix="d"%>
<div class="headerPanel">
  <jsp:useBean id="now" class="java.util.Date" />
  <fmt:formatDate var="hour" value="${now}" pattern="HH" />
  <c:if test="${6 <= hour && hour < 18}">
    <img src="${pageContext.request.contextPath}/static/resources/app/img/logo.png" alt="" />
    <i id="menu_button" class="material-icons md-48">menu</i>
  </c:if>
  <c:if test="${hour < 6 || 18 <= hour}">
    <img src="${pageContext.request.contextPath}/static/resources/app/img/logo_white.png" alt="" />
    <i id="menu_button" class="material-icons md-48 white">menu</i>
  </c:if>
</div>
