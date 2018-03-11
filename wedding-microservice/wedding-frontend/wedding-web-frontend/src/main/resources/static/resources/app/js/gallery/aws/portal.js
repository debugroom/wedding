jQuery("[id^=folder-icon-]").on("click", connect)

function getFolderDetail(){
	var folderFormPanel = $("#folderFormPanel")
	var detailPanel = $("#detailPanel");
	if(folderFormPanel != null){
		folderFormPanel.remove();
	}
	if(detailPanel != null){
		detailPanel.remove();
	}
 	$(".contents-panel").append($('<div id="detailPanel" class="right-position"><h4>フォルダ：'
								+ $(this).data("folderName") + '</h4>'
								+ '現在このフォルダは、<a id="hyper-link-get-users" data-get-users-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" href="javascript:void(0)">こちらのユーザ</a>がアクセスできます。 フォルダの設定変更は <a id="hyper-link-edit-folder" data-folder-id="' 
								+ $(this).data("folderId")
								+ '" data-edit-folder-url="' 
								+ $(this).data("editFolderUrl")
								+ '" data-folder-related-users-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" data-folder-related-no-users-url="' 
								+ $(this).data("folderRelatedNoUsersUrl")
								+ '" href="javascript:void(0)" >こちら</a>' 
								+ '</br>' 
								+ '</div>'));
//	$("#hyper-link-get-users").on("mouseenter", getViewers);
	$("#hyper-link-get-users").on("click", getViewers);
	$("#hyper-link-edit-folder").on("click", editFolder);
	

	var thumbnailPanel = $("#thumbnailPanel");
	if(thumbnailPanel != null){
		thumbnailPanel.remove();
	}
 	$("#delailPanel").append($('<div id="messagePanel"><span class="warningMessage"></div>'));
 	$("#detailPanel").append($('<br/><span id="isMultiSelectableMessage">複数選択する場合はこちらをチェック</span><input type="checkbox" id="isMultiSelectableContents" /><br/>'));
 	$("#detailPanel").append($('<div id="thumbnailPanel"></div>'));
 	
	var photoDownloadMessageDisplayPanel = $("#photoDownloadMessageDisplayPanel");
	var movieDownloadMessageDisplayPanel = $("#movieDownloadMessageDisplayPanel");
	if(photoDownloadMessageDisplayPanel != null){
		photoDownloadMessageDisplayPanel.remove();
	}
	if(movieDownloadMessageDisplayPanel != null){
		movieDownloadMessageDisplayPanel.remove();
	}
 	$("#thumbnailPanel").append($('<span id="photoDownloadMessageDisplayPanel" class="warningMessage"><br/>写真データをロードしています...</span>'));
 	$("#thumbnailPanel").append($('<span id="movieDownloadMessageDisplayPanel" class="warningMessage"><br/>動画データをロードしています...</span>'));

 	$.get($(this).data("folderRelatedPhotographsUrl"), function(data){
		$.each(data.photographs, 
			function(i, val){
				$.get(data.requestContextPath + "/gallery/photo/" + val.photoId + "/thumbnail", function(photo){
					$("#thumbnailPanel")
						.prepend($('<img id="thumbnail-image-'
							+ photo.photoId
							+ '" class="thumbnail-image" src="' 
							+ photo.thumbnailPresignedUrl
							+ '" data-photo-id="' 
							+ photo.photoId
							+ '" data-photographs-url="' 
							+ data.requestContextPath
							+ '/gallery/photo/'
							+ photo.photoId
							+ '">'));	
					$("#thumbnail-image-" + photo.photoId).on("click", selectContents);
					if(i == data.photographs.length - 1){
						$("#photoDownloadMessageDisplayPanel").remove();
					}
				});
			}
		);
		if(data.photographs.length == 0){
			$("#photoDownloadMessageDisplayPanel").remove();
		}
	});

 	$.get($(this).data("folderRelatedMoviesUrl"), function(data){
 		$.each(data.movies,
 			function(i, val){
				$.get(data.requestContextPath + "/gallery/movie/" + val.movieId + "/thumbnail", function(movie){
					$("#thumbnailPanel")
 						.prepend($('<img id="thumbnail-movie-'
 							+ movie.movieId
 							+ '" class="thumbnail-image" src="'
 							+ movie.thumbnailPresignedUrl
 							+ '" data-movie-id="'
 							+ movie.movieId
 							+ '" data-movies-url="'
 							+ data.requestContextPath
 							+ '/gallery/movie/'
 							+ movie.movieId
 							+ '">'));
					$("#thumbnail-movie-" + movie.movieId).on("click", selectContents);
					if(i == data.movies.length - 1){
						$("#movieDownloadMessageDisplayPanel").remove();
					}
				});
 			}
 		);
 		if(data.movies.length == 0){
			$("#movieDownloadMessageDisplayPanel").remove();
 		}
		const isIOS = /[ \(]iP/.test(navigator.userAgent);
		if(isIOS){
			$("#detailPanel").append($('<br/><span class="warningMessage">iOSではセキュリティ仕様により、ブラウザからの動画の保存、写真のマルチダウンロード保存ができません。データを保存する場合は、パソコンからご利用ください。</span><br/>'));
		}
		if(navigator.userAgent.indexOf('msie') != -1) {
			$("#detailPanel").append($('<br/><span class="warningMessage">Internet Explorerでは、写真・動画のマルチダウンロード保存をサポートしていません。データを複数同時にダウンロードする場合は、Chrome、Firefox、Edgeをご利用ください。</span><br/>'));
		}
		$("#detailPanel").append($('<div class="alternative-button">' 
				+ '<button id="download-button" class="alternative-first-button" value="' 
				+ data.requestContextPath
				+ "/gallery/download/"
				+ '" disabled="true">ダウンロードする</button>'
	 			+ '<button id="delete-button" class="alternative-last-button" value="'
				+ data.requestContextPath
				+ "/gallery/media"
	 			+'" disabled="true">削除する</button></div>'));
		$("#download-button").on("click", downloadContents);
		$("#delete-button").on("click", displayDeleteContentsModalWindow);

		$("#thumbnailPanel").append($('<div id="file-upload-button-image"><img id="file-upload-button" src="' 
				+ data.requestContextPath
				+ '/static/resources/app/img/upload.png'
				+ '" />コンテンツをアップロード</div>'));
		$("#file-upload-button").on("click",function(){
			$('#file-upload-input').trigger("click");
		});

		$("#thumbnailPanel").append($('<form id="file-upload-form" action="" method="post" enctype="multipart/form-data"><input type="file" id="file-upload-input" name="uploadFiles" multiple ' 
				+ ' data-folder-id="' 
				+ $(this).data("folderId")
				+ '" data-authorization-url="' 
				+ data.requestContextPath
				+ '/gallery/upload/authorization/'
				+ $(this).data("folderId")
				+ '" /></form>'));
		$("#file-upload-form").on("click", uploadFiles);

    });
}

function uploadFiles(){
	var progressPanel = $("#progress");
	if(progressPanel != null){
		progressPanel.remove();
	}
	$("#thumbnailPanel").after('<div id="progress"><span id="pre-message" class="errorMessage">アップロードの準備中…</span></div>' );
	var formData = new FormData;
	$('#file-upload-input').fileupload({
		forceIframeTransport: true, 
//		url: uploadUrl,   //url option is for xhr, not working.
//		type: 'POST',
		acceptFileTypes: /(\.|\/)(gif|jpe?g|png|mp4|wmv|MOV|mov|m4v)$/i,
		formData: formData,
		add : function(e, data){
			$.ajax({
				url: $("#file-upload-input").data("authorizationUrl"),
				type: "GET" ,
				async: false,
			}).done(function(result, textStatus, jqXHR){
				$("#pre-message").remove();
				var isImage = false;
				var isMovie = false;
				var uploadUrl;
				$.each(data.originalFiles, function(index, value){
					var fileName = data.files[index].name.replace(".", "-");
					$("#progress-" + fileName).remove();
					$("#errorMessage-" + fileName).remove();
					$("#progress").append($('<div id="progress-' + fileName + '"><div class="progress-bar"><span id="progress-ratio-' + fileName + '">0</span>%</div>'));
					$('#progress-' + fileName + ' .progress-bar').css('width', 0 + '%');
					$("#progress-" + fileName).before($('<span id="upload-label-' + fileName 
							+ '" class="successMessage">アップロード進行状況： ' + data.files[index].name + '<br></span>'));
					isImage = false;
					isMovie = false;
					switch(data.files[index].type){
					case "image/gif":
						isImage = true;
						break;
					case "image/jpeg":
						isImage = true;
						break;
					case "image/png":
						isImage = true;
						break;
					case "video/mpeg":
						isMovie = true;
						break;
					case "video/mp4":
						isMovie = true;
						break;
					case "video/quicktime":
						isMovie = true;
						break;
					default:
						break;
					}
					formData = new FormData();
					if(isImage){
						uploadUrl = result.imageUploadAuthorization.uploadUrl;
						formData.append('key', result.imageUploadAuthorization.objectKey + data.files[index].name);
						formData.append('x-amz-credential', result.imageUploadAuthorization.credential);
						formData.append('acl', result.imageUploadAuthorization.acl);
						formData.append('x-amz-meta-folder-id', result.imageUploadAuthorization.folderId);
						formData.append('x-amz-meta-user-id', result.imageUploadAuthorization.userId);
						formData.append('x-amz-security-token', result.imageUploadAuthorization.securityToken);
						formData.append('x-amz-algorithm', result.imageUploadAuthorization.algorithm);
						formData.append('x-amz-date', result.imageUploadAuthorization.date);
						formData.append('policy', result.imageUploadAuthorization.policy);
						formData.append('x-amz-signature', result.imageUploadAuthorization.signature);
					}else if(isMovie){
						uploadUrl = result.movieUploadAuthorization.uploadUrl;
						formData.append('key', result.movieUploadAuthorization.objectKey + data.files[index].name);
						formData.append('x-amz-credential', result.movieUploadAuthorization.credential);
						formData.append('acl', result.movieUploadAuthorization.acl);
						formData.append('x-amz-meta-folder-id', result.movieUploadAuthorization.folderId);
						formData.append('x-amz-meta-user-id', result.movieUploadAuthorization.userId);
						formData.append('x-amz-security-token', result.movieUploadAuthorization.securityToken);
						formData.append('x-amz-algorithm', result.movieUploadAuthorization.algorithm);
						formData.append('x-amz-date', result.movieUploadAuthorization.date);
						formData.append('policy', result.movieUploadAuthorization.policy);
						formData.append('x-amz-signature', result.movieUploadAuthorization.signature);
					}
					if(isImage || isMovie){
						formData.append('file', data.files[index]);
						$.ajax({
							url: uploadUrl,
							type: 'POST',
							dataType: 'json',
							data: formData,
							contentType: false,
							processData: false,
							xhr : function(){
						        var XHR = $.ajaxSettings.xhr();
						        if(XHR.upload){
						            XHR.upload.addEventListener('progress',function(e){
						                var progre = parseInt(e.loaded/e.total*100);			
						                $('#progress-' + fileName + ' .progress-bar').css({width: ((progre+1)*95/100)+'%'});
						                $("#progress-ratio-" + fileName).text(progre);
						            }, false);
						        }
						        return XHR;
						    },
						}).done(function(data, textStatus, jqXHR){
							$("#upload-label-" + fileName).html($("#upload-label-" + fileName).html() + " サムネイルを作成しています。<br/>");
							$("#progress-" + fileName).remove();
						}).fail(function(jqXHR, textStatus, errorThrown){
							$("#errorMessage-" + fileName).remove();
							$("#thumbnailPanel").after($('<span id="errorMessage-' + fileName + '" class="errorMessage">'
										+ 'ファイルアップロード中にエラーが発生しました。' + fileName + '</span>'));
						});
					}else{
						$("#errorMessage-" + fileName).remove();
						$("#thumbnailPanel").before($('<span id="errorMessage-' + fileName
								+ '" class="errorMessage">サポートされない形式です。'
								+ fileName + '</span>'));
					}
				});
			}).fail(function(jqXHR, textStatus, errorThrown){
		});
	},done : function(e, data){
	},fail : function(e, data){
	}});

}

var stompClient = null;

function connect(){
	var folderId = $(this).data("folderId");
	var socket = null;
	var endpoint = $(this).data("requestContextPath") + "/notifications/" + folderId;
	var subscription = "/notification/folder-" + folderId;
	if(stompClient != null){
		stompClient.disconnect();
	}
	socket = new SockJS(endpoint);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame){
		console.log("Connected : " + frame);
		stompClient.subscribe(subscription, function(message){
			console.log(JSON.parse(message.body));
			addThumbnail(JSON.parse(message.body));
		});
	});
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function addThumbnail(data){
    var fileName = data.media.originalFilename.replace(".", "-");
	if(data.media.mediaType == "PHOTOGRAPH"){
		$("#file-upload-button-image")
		.before($('<img id="thumbnail-image-'
			+ data.media.mediaId
			+ '" class="thumbnail-image" src="' 
			+ data.thumbnailPresignedUrl
			+ '" data-photo-id="' 
			+ data.media.mediaId
			+ '" data-photographs-url="' 
			+ data.media.requestContextPath
			+ '/gallery/photo/'
			+ data.media.mediaId
			+ '">'));
		$("#thumbnail-image-" + data.media.mediaId).on("click", selectContents);
	}else if(data.media.mediaType == "MOVIE"){
		$("#file-upload-button-image")
			.before($('<img id="thumbnail-movie-'
					+ data.media.mediaId
					+ '" class="thumbnail-image" src="'
					+ data.thumbnailPresignedUrl
					+ '" data-movie-id="'
					+ data.media.mediaId
					+ '" data-movies-url="'
					+ data.media.requestContextPath
					+ '/gallery/movie/'
					+ data.media.mediaId
					+ '">'));
		$("#thumbnail-movie-" + data.media.mediaId).on("click", selectContents);
	}
	$("#upload-label-" + fileName).html(data.media.originalFilename + "  アップロードが完了しました。<br/>");
}

function selectContents(event){
	var isMultiSelectableContentsCheckbox = $("#isMultiSelectableContents")[0].checked;
	var selectedContents = $(".selectedContents");
	if(!isMultiSelectableContentsCheckbox && selectedContents.length == 0){
	    if(!((event.ctrlKey && !event.metaKey) 
	    		|| (!event.ctrlKey && event.metaKey))){
		// チェックボックスが選択されておらず、何もコンテンツが選択おらず、ctlキーが押下されていない場合はコンテンツパネルを表示する。
	    	if($(this).data("photographsUrl") != null){
	    		showContentsPanel("photo", $(this).data("photographsUrl"), $(this).data("photoId"));
	    	}else if($(this).data("moviesUrl") != null){
	    		showContentsPanel("movie",$(this).data("moviesUrl"), $(this).data("movieId"));
	    	}
		}
		$(this).toggleClass("selectedContents");
		$("#download-button").toggleClass("activate");
		$("#download-button").prop("disabled", false);
		$("#delete-button").toggleClass("activate");
		$("#delete-button").prop("disabled", false);
	}else{
		// チェックボックスが選択されておらず、既にコンテンツが選択済みでctlキーが押下されていない場合→新たに選択し直し、コンテンツパネルを表示する。
	    if(!isMultiSelectableContentsCheckbox && !((event.ctrlKey && !event.metaKey) 
	    		|| (!event.ctrlKey && event.metaKey))){
			for(var i = 0 ; i < selectedContents.length ; i++){
				$(selectedContents[i]).toggleClass("selectedContents");
			}
	    	if($(this).data("photographsUrl") != null){
	    		showContentsPanel("photo", $(this).data("photographsUrl"), $(this).data("photoId"));
	    	}else if($(this).data("moviesUrl") != null){
	    		showContentsPanel("movie", $(this).data("moviesUrl"), $(this).data("movieId"));
	    	}
			$(this).toggleClass("selectedContents");
		}else{
		// 既にコンテンツが選択済みでctlキーが押下されている場合→選択を解除し、コンテンツパネルを非表示にする。
			var contentsPanel = $("#contentsPanel");
			if(contentsPanel){
				contentsPanel.remove();
			}
			$(this).toggleClass("selectedContents");
		}
		$("#download-button").toggleClass("activate");
		$("#delete-button").toggleClass("activate");
	}
	if($(".selectedContents").length == 0){
		$("#download-button").prop("disabled", true);
		$("#delete-button").prop("disabled", true);
	}else{
		$("#download-button").prop("disabled", false);
		$("#delete-button").prop("disabled", false);
	}
}

function showContentsPanel(type, contentsUrl, id){
	var contentsPanel = $("#contentsPanel");
	if(contentsPanel){
		contentsPanel.remove();
	}
	if(type == "photo"){
		$("#thumbnail-image-" + id).before($('<div id="contentsPanel"><span id="progress-message" class="warningMessage">コンテンツをロードしています...</span></div>'));
	} else if(type == "movie"){
		$("#thumbnail-movie-" + id).before($('<div id="contentsPanel"><span id="progress-message" class="warningMessage">コンテンツをロードしています...</span></div>'));
	}
	
	$.get(contentsUrl, function(data){
		if(type == "photo"){
			$("#contentsPanel").append($('<img class="" src="'
			+ data.presignedUrl
			+ '" />'));
		} else if(type == "movie"){
			$("#contentsPanel").append($('<video controls ><source src="'
			+ data.presignedUrl
			+ '"/></video>'));
		}
		var progressMessage = $("#progress-message");
		if(progressMessage){
			progressMessage.remove();
		}
	});

}

function downloadContents(){
	var selectedContents = $(".selectedContents");
	if(selectedContents.length != 0){
		var photoCount = 0;
		var movieCount = 0;
		const isIOS = /[ \(]iP/.test(navigator.userAgent)
		for(var i = 0; i < selectedContents.length ; i++){
			var photoId = $(selectedContents[i]).data("photoId");
			var movieId = $(selectedContents[i]).data("movieId");
			if(photoId != null){
				$.get($(this).val() + "photo/" + photoId, function(data){
					if(!isIOS){
						var link = document.createElement("a");
						var fileSplit = data.filePath.split(/[\s/]+/)
						link.download = fileSplit[fileSplit.length-1];
						link.href = data.presignedUrl;
						link.click();
					}else{
						window.location.href = data.presignedUrl;
					}
				});
			}else if(movieId != null){
				$.get($(this).val() + "movie/" + movieId, function(data){
					var link = document.createElement("a");
					var fileSplit = data.filePath.split(/[\s/]+/)
					link.download = fileSplit[fileSplit.length-1];
					link.href = data.presignedUrl;
					link.click();
				});
			}
		}
	}
}