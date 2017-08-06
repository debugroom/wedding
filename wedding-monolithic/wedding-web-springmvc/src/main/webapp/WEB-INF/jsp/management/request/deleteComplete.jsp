<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/management/request/deleteComplete-flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="resultPanel">
          <p>以下の依頼事項を削除しました。</p>
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
                <td><c:out value="${request.registratedDate}" /></td>
              </tr>
            </tbody>
          </table>
          <h3>依頼内容</h3>
          <hr>
          <div id="messageBody" class="request-panel">
            ${request.requestContents}
          </div>
        </div>
      </div>
    </div>
  </div>
</body>