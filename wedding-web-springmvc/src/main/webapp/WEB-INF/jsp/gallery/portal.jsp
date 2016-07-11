<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/lined-menu.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/gallery/portal.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/vendor/jquery/slider-pro.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/vendor/jquery/jquery.sliderPro.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/vendor/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/vendor/jquery/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/vendor/jquery/jquery.fileupload.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/linedMenu.jsp" />
  <div id="flex-container">
    <div class="flex-item-2">
      <article>
        <div class="slider-pro" id="my-slider">
          <div class="sp-slides">
            <div id="slider">
            <c:forEach items="${portalOutput.randomPhotographs}" var="photo" varStatus="status">
              <div class="sp-slide"><img class="sp-image" src="${pageContext.request.contextPath}/gallery/photo/${photo.photoId}" /></div>
            </c:forEach>
            </div>
            <div class="sp-thumbnails">
            <c:forEach items="${portalOutput.randomPhotographs}" var="photo" varStatus="status">
              <div class="sp-thumbnail">
                <img class="sp-thumbnail-image" src="${pageContext.request.contextPath}/gallery/photo-thumbnail/${photo.photoId}"/>
                <p class="sp-thumbnail-text">test-${status.index+1}</p>
              </div>
            </c:forEach>
            </div>
          </div>
        </div>
        <div class="panel">
          <h3>Gallery Contents</h3>
          <div class="left-position">
            <c:forEach items="${portalOutput.folders}" var="folder" varStatus="status">
              <div id="folder-<c:out value='${folder.folderId}' />" >
                <img id="folder-icon-<c:out value='${folder.folderId}' />" 
                    data-folder-id="<c:out value='${folder.folderId}' />" 
                    data-folder-name="<c:out value='${folder.folderName}' />" 
                    data-human-icon-url="${pageContext.request.contextPath}/resources/app/img/human-icon.png"
                    data-folder-related-users-url="${pageContext.request.contextPath}/gallery/folder/viewers/<c:out value='${folder.folderId}' />"
                    data-folder-related-no-users-url="${pageContext.request.contextPath}/gallery/folder/no-viewers/<c:out value='${folder.folderId}' />"
                    data-folder-related-photographs-url="${pageContext.request.contextPath}/gallery/photographs/<c:out value='${folder.folderId}' />?folderName=<c:out value='${folder.folderName}' />"
                    data-edit-folder-url="${pageContext.request.contextPath}/gallery/folders/<c:out value='${folder.folderId}' />"
                    src="${pageContext.request.contextPath}/resources/app/img/Pictures.png" />
                <c:if test="${folder.folderId != '000000000000'}">
                    <img id="delete-folder-icon-<c:out value='${folder.folderId}' />" class="delete-folder-icon"
                        data-folderId="<c:out value='${folder.folderId}' />" 
                        data-url="${pageContext.request.contextPath}/gallery/folders/<c:out value='${folder.folderId}' />"
                        src="${pageContext.request.contextPath}/resources/app/img/delete.png" />
                </c:if>
                <br/>
                <c:out value="${folder.folderName}" />
              <br>
              </div>
            </c:forEach>
          <img id="new-folder" 
            data-image-folder-icon-url="${pageContext.request.contextPath}/resources/app/img/Pictures.png" 
            data-get-users-url="${pageContext.request.contextPath}/gallery/folder/no-viewers/999999999999" 
            data-create-folder-url="${pageContext.request.contextPath}/gallery/folder/new" 
            src="${pageContext.request.contextPath}/resources/app/img/custom-folder.png"/><br>
          </div>
        </div>
      </article>
    </div>
  </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/gallery/portal.js"></script>
