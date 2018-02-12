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
								+ '" href="javascript:void(0)">こちらのユーザ</a>から閲覧されています。 フォルダの設定変更は <a id="hyper-link-edit-folder" data-folder-id="' 
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
 	$("#detailPanel").append($('<div id="thumbnailPanel"></div>'));
 	
 	$.get($(this).data("folderRelatedPhotographsUrl"), function(data){
		$.each(data.photographs, 
			function(i, val){
				$("#thumbnailPanel")
					.append($('<img id="thumbnail-image-'
							+ val.photoId
							+ '" class="thumbnail-image" src="' 
							+ data.requestContextPath
							+ '/gallery/photo-thumbnail/'
							+ val.photoId
							+ '" data-photo-id="' 
							+ val.photoId
							+ '" data-photographs-url="' 
							+ data.requestContextPath
							+ '/gallery/media/'
							+ val.photoId
							+ '" data-file-extension="'
							+ getExtension(val.filePath)
							+ '">'));	
			}
		);
		$("[id^=thumbnail-image-]").on("click", selectContents);
	});

 	$.get($(this).data("folderRelatedMoviesUrl"), function(data){
 		$.each(data.movies,
 			function(i, val){
 				$("#thumbnailPanel")
 					.append($('<img id="thumbnail-movie-'
 							+ val.movieId
 							+ '" class="thumbnail-image" src="'
 							+ data.requestContextPath
 							+ '/gallery/movie-thumbnail/'
 							+ val.movieId
 							+ '" data-movie-id="'
 							+ val.movieId
 							+ '" data-movies-url="'
 							+ data.requestContextPath
 							+ '/gallery/media/'
 							+ val.movieId
 							+ '" data-file-extension="'
 							+ getExtension(val.filePath)
 							+ '">'));
 			}
 		);

		$("[id^=thumbnail-movie-]").on("click", selectContents);

		$("#detailPanel").append($('<div class="alternative-button">' 
				+ '<button id="download-button" class="alternative-first-button" value="' 
				+ data.requestContextPath
				+ "/gallery/media"
				+ '" disabled="true">ダウンロードする</button>'
	 			+ '<button id="delete-button" class="alternative-last-button" value="'
				+ data.requestContextPath
				+ "/gallery/media"
	 			+'" disabled="true">削除する</button></div>'));
		$("#download-button").on("click", downloadContents);
		$("#delete-button").on("click", deleteContents);
    });

	$("#thumbnailPanel").append($('<div class="file-upload-button-image"><img id="file-upload-button" src="' 
				+ '/static/resources/app/img/upload.png'
				+ '" />コンテンツをアップロード</div>'));
	$("#file-upload-button").on("click",function(){
			$('#file-upload-input').trigger("click");
    });
	$("#thumbnailPanel").append($('<form id="file-upload-form" action="" method="post" enctype="multipart/form-data"><input type="file" id="file-upload-input" name="uploadFiles" multiple ' 
				+ ' data-folder-id="' 
				+ $(this).data("folderId")
				+ '" data-authorization-url="' 
				+ '/gallery/upload/authorization'
				+ '" /></form>'));
	$("#file-upload-form").on("click", uploadFiles);

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
					$("#progress-" + index).remove();
					$("#errorMessage-" + index).remove();
					$("#progress").append($('<div id="progress-' + index + '"><div class="progress-bar"><span id="progress-ratio-' + index + '">0</span>%</div>'));
					$('#progress-' + index + ' .progress-bar').css('width', 0 + '%');
					$("#progress-" + index).before($('<span id="upload-label-' + index 
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
						                $('#progress-' + index + ' .progress-bar').css({width: ((progre+1)*95/100)+'%'});
						                $("#progress-ratio-" + index).text(progre);
						            }, false);
						        }
						        return XHR;
						    },
						}).done(function(data, textStatus, jqXHR){
							
						}).fail(function(jqXHR, textStatus, errorThrown){
							$("#errorMessage-" + index).remove();
							$("#thumbnailPanel").after($('<span id="errorMessage-' + index + '" class="errorMessage">'
										+ 'ファイルアップロード中にエラーが発生しました。' + data.files[index].name + '</span>'));
						});
					}else{
						$("#errorMessage-" + index).remove();
						$("#thumbnailPanel").before($('<span id="errorMessage-' + index 
								+ '" class="errorMessage">サポートされない形式です。'
								+ data.files[index].name + '</span>'));
					}
				});
			}).fail(function(jqXHR, textStatus, errorThrown){
		});
	},done : function(e, data){
	},fail : function(e, data){
	}});

}