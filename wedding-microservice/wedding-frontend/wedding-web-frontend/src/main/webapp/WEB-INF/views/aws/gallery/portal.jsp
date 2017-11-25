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
<title>Gallery</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/gallery/portal.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/gallery/portal_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/gallery/portal_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/vendor/jquery/slider-pro.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/vendor/jquery/jquery.sliderPro.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/vendor/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/vendor/jquery/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/vendor/jquery/jquery.fileupload.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/menu.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <div class="header-menu">
    <c:import url="/WEB-INF/views/common/lined-menu.jsp" />
  </div>
  <div id="flex-container">
    <div class="flex-item-2">
      <article>
        <div class="gallery-panel">
          <c:if test="${!empty galleryPortalResource.randomPhotographs}">
          <div class="slider-pro" id="my-slider">
            <div class="sp-slides">
              <div id="slider">
                <c:forEach items="${galleryPortalResource.randomPhotographs}" var="photo" varStatus="status">
                  <div class="sp-slide">
                    <img class="sp-image" src="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/image/${photo.filePath}" />
                  </div>
                </c:forEach>
              </div>
              <div class="sp-thumbnails">
                <c:forEach items="${galleryPortalResource.randomPhotographs}" var="photo" varStatus="status">
                  <div class="sp-thumbnail">
                    <img class="sp-thumbnail-image" src="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/image/${photo.thumbnailFilePath}" />
                    <p class="sp-thumbnail-text">test-${status.index+1}</p>
                  </div>
                </c:forEach>
              </div>
            </div>
          </div>
          </c:if>
          <c:if test="${!empty galleryPortalResource.randomMovies}">
          <div class="playerPanel">
            <figure id="video_player">
              <video controls poster="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/movie/${galleryPortalResource.randomMovies[0].thumbnailFilePath}">
                <c:if test="${fn:substringAfter(galleryPortalResource.randomMovies[0].filePath, '.')=='MOV'}">
                  <source src="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/movie/${galleryPortalResource.randomMovies[0].filePath}" type="video/mp4">
                </c:if>
                <c:if test="${fn:substringAfter(galleryPortalResource.randomMovies[0].filePath, '.')!='MOV'}">
                  <source src="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/movie/${galleryPortalResource.randomMovies[0].filePath}" type="video/${fn:substringAfter(galleryPortalResource.randomMovies[0].filePath, '.')}">
                </c:if>
              </video>
              <figcaption>
                <c:forEach items="${galleryPortalResource.randomMovies}" var="movie" varStatus="status">
                  <a href="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/movie/${movie.filePath}">
                    <img src="${awsResource.distributionServerUrl}/${awsResource.galleryRootPath}/movie/${movie.thumbnail}" />
                  </a>
                </c:forEach>
              </figcaption>
            </figure>
          </div>
          </c:if>
<!--  
          <video id="my-video" class="video-js" controls preload="auto" width="640" height="264" 
            poster="${pageContext.request.contextPath}/gallery/movie-thumbnail/${galleryPortalResource.randomMovies[0].movieId}/xxx.${fn:substringAfter(galleryPortalResource.randomMovies[0].thumbnailFilePath, '.')}" 
            data-setup='{ "controlBar": { "muteToggle": false } }'>
            <c:forEach items="${galleryPortalResource.randomMovies}" var="movie" varStatus="status">
              <c:if test="${fn:substringAfter(movie.filePath, '.')=='MOV'}">
                <source src="${pageContext.request.contextPath}/gallery/movie/${movie.movieId}/xxx.mp4" type="video/mp4">
              </c:if>
              <c:if test="${fn:substringAfter(movie.filePath, '.')!='MOV'}">
                <source src="${pageContext.request.contextPath}/gallery/movie/${movie.movieId}/xxx.${fn:substringAfter(movie.filePath, '.')}" type="video/${fn:substringAfter(movie.filePath, '.')}">
              </c:if>
            </c:forEach>
            <p class="vjs-no-js">
              To view this video please enable JavaScript, and consider upgrading to a web browser that
              <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
            </p>
          </video>
          <video src="/gallery/movie/${galleryPortalResource.randomMovies[0].movieId}/xxx.${fn:substringAfter(galleryPortalResource.randomMovies[0].filePath, '.')}"></video>
-->
        </div>
        <div class="contents-panel">
          <h3>Gallery Contents</h3>
          <div class="left-position">
            <c:forEach items="${galleryPortalResource.folders}" var="folder" varStatus="status">
              <div id="folder-<c:out value='${folder.folderId}' />">
                <img id="folder-icon-<c:out value='${folder.folderId}' />" 
                    src="${pageContext.request.contextPath}/static/resources/app/img/Pictures.png" 
                    data-folder-id="<c:out value='${folder.folderId}' />" 
                    data-folder-name="<c:out value='${folder.folderName}' />" 
                    data-humnan-icon-url="${pageContext.request.contextPath}/static/resources/app/img/humna-icon.png" 
                    data-folder-related-users-url="${pageContext.request.contextPath}/gallery/folder/viewers/<c:out value='${folder.folderId}' />" 
                    data-folder-related-no-users-url="${pageContext.request.contextPath}/gallery/folder/no-viewers/<c:out value='${folder.folderId}' />" 
                    data-folder-related-photographs-url="${pageContext.request.contextPath}/gallery/photographs/<c:out value='${folder.folderId}' />?folderName=<c:out value='${folder.folderName}' />" 
                    data-folder-related-movies-url="${pageContext.request.contextPath}/gallery/movies/<c:out value='${folder.folderId}' />?folderName=<c:out value='${folder.folderName}' />" 
                    data-edit-folder-url="${pageContext.request.contextPath}/gallery/folders/<c:out value='${folder.folderId}' />"
                    />
                 <c:if test="${folder.folderId != '000000000000'}">
                   <img id="delete-folder-icon-<c:out value='${folder.folderId}' />" class="delete-folder-icon"
                        data-folderId="<c:out value='${folder.folderId}' />" 
                        data-url="${pageContext.request.contextPath}/gallery/folders/<c:out value='${folder.folderId}' />"
                        src="${pageContext.request.contextPath}/static/resources/app/img/delete.png" />
                 </c:if>
                 <br/>
                 <c:out value="${folder.folderName}" />
                 <br/>
              </div>
            </c:forEach>
            <img id="new-folder" 
              data-image-folder-icon-url="${pageContext.request.contextPath}/static/resources/app/img/Pictures.png" 
              data-get-users-url="${pageContext.request.contextPath}/gallery/folder/no-viewers/999999999999" 
              data-create-folder-url="${pageContext.request.contextPath}/gallery/folder/new" 
             src="${pageContext.request.contextPath}/static/resources/app/img/custom-folder.png"/><br>
             新しいフォルダを作成
             <br/>
          </div>
          <br/>
        </div>
      </article>
    </div>
  </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/resources/app/js/gallery/portal.js"></script>
<script src="http://vjs.zencdn.net/6.2.4/video.js"></script>