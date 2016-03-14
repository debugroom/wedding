<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/infomation/portal-flex.css">
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
          <d:Page page="${page}" requestPath="/management/infomation/portal" pageSize="10"/>
          <table>
            <tbody>
              <tr>
                <th>No</th>
                <th>ID</th>
                <th>タイトル</th>
                <th>公開日時</th>
                <th>アクション</th>
              </tr>
              <c:forEach items="${page.content}" var="infomation" varStatus="status">
              <tr>
              	<td>${status.index + 1}</td>
              	<td><c:out value="${infomation.infoId}" /></td>
              	<td><c:out value="${infomation.title}" /></td>
              	<td><fmt:formatDate value="${infomation.releaseDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
              	<td>
                    <form id="infomation_${infomation.infoId}" action="${pageContext.request.contextPath}/management/infomation/${infomation.infoId}" >
              	      <button id="edit-delete-button-${infomation.infoId}" name="type" type="submit" value="detail" >詳細</button>
              	      <button id="edit-delete-button-${infomation.infoId}" name="type" type="submit" value="delete" >削除</button>
                    </form>
              	</td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
     </div>
   </div>
  </div>
</body>
</html>