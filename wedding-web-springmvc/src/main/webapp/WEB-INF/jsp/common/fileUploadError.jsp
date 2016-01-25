<% response.setStatus(HttpServletResponse.SC_BAD_REQUEST); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>FileUpload Error!</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
</head>
<body>
    <header>
        <h1>
            <img src="${pageContext.request.contextPath}/resources/app/img/icon_6m_96.png">
            Web site for wedding
            <img src="${pageContext.request.contextPath}/resources/app/img/icon_6m_96.png">
        </h1>
    </header>
    <div id="flex-container">
        <div class="flex-item-1">
            <d:Menu/>
        </div>
        <div class="flex-item-2">
            <p>ファイルアップロードに失敗しました。</p>
        </div>
    </div>
</body>
</html>