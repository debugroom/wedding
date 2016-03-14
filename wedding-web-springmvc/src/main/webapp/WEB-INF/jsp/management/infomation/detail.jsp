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
            <table>
              <tbody>
                <tr>
                  <td>インフォメーションID</td>
                  <td><c:out value="${infomationDetail.infomation.infoId}"></c:out></td>
                </tr>.
                <tr>
                  <td>タイトル</td>
                  <td><c:out value="${infomationDetail.infomation.title}"></c:out></td>
                </tr>
                <tr>
                  <td>登録日時</td>
              	  <td><fmt:formatDate value="${infomationDetail.infomation.registratedDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
                </tr>
                <tr>
                  <td>公開日時</td>
              	  <td><fmt:formatDate value="${infomationDetail.infomation.releaseDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
                </tr>
                <tr>
                  <td>最終更新日時</td>
              	  <td><fmt:formatDate value="${infomationDetail.infomation.lastUpdatedDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
                </tr>
              </tbody>
            </table>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>