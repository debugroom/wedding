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
	$("#hyper-link-get-users").on("mouseenter", getViewers);
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
	 			+'" disabled="true">削除する</button></div>'));$("#file-upload-input").on("click", uploadFiles);
	$("#download-button").on("click", downloadContents);
	$("#delete-button").on("click", deleteContents);
    });

	$("#thumbnailPanel").append($('<div class="file-upload-button-image"><img id="file-upload-button" src="' 
				+ '/static/resources/app/img/upload.png'
				+ '" />コンテンツをアップロード</div>'));
	$("#file-upload-button").on("click",function(){
			$('#file-upload-input').trigger("click");
    });
	$("#thumbnailPanel").append($('<input type="file" id="file-upload-input" name="uploadFileForms[]" multiple ' 
				+ ' data-folder-id="' 
				+ $(this).data("folderId")
				+ '" data-url="' 
				+ '/gallery/upload'
				+ '" />'));
	$("#thumbnailPanel").before($('<div id="progress">' 
				+ '<div id="progress-bar" class="progress-bar" style="width: 0%;"></div>'
				+ '</div>'));

}

function uploadFiles(){
	var progressPanel = $("#progress");
	if(progressPanel != null){
		progressPanel.remove();
	}
	$('#file-upload-input').fileupload({
		url : $(this).data("url"),
		dataType : 'json',
		acceptFileTypes: /(\.|\/)(gif|jpe?g|png|mp4|wmv|MOV|mov|m4v)$/i,
        maxFileSize: 10000000000,
        formData : {folderId : $(this).data("folderId")},
        submit : function(e, data){
        	$("#upload-label").remove();
            $('#progress .progress-bar').css(
                    'width',
                    0 + '%'
                );
        	$("#progress-bar").before($('<span id="upload-label" class="successMessage">アップロード進行状況：<br></span>'));
			$.each(data.originalFiles, function(index, value){
				if(data.files[0].name == value.name){
					data.paramName = "uploadFile";
				}
			})        	
        },
		done : function(e, data){
			if(data.result.media.mediaType == "PHOTOGRAPH"){
				$("#file-upload-button").before($(
				'<img id="thumbnail-image-'
					+ data.result.media.mediaId
					+ '" class="thumbnail-image" src="'
					+ data.result.requestContextPath
					+ '/gallery/photo-thumbnail/'
					+ data.result.media.mediaId
					+ '" data-photo-id="'
					+ data.result.media.mediaId
					+ '" data-photographs-url="'
					+ data.result.requestContextPath
					+ '/gallery/photo/'
					+ data.result.media.mediaId
					+ '" data-file-extension=".'
					+ data.result.media.extension
					+ '" />'));
				$("#thumbnail-image-" + data.result.media.mediaId).on("click", selectContents);
			}else if(data.result.media.mediaType == "MOVIE"){
				$("#file-upload-button").before($(
				'<img id="thumbnail-movie-'
					+ data.result.media.mediaId
					+ '" class="thumbnail-image" src="'
					+ data.result.requestContextPath
					+ '/gallery/movie-thumbnail/'
					+ data.result.media.mediaId
					+ '" data-movie-id="'
					+ data.result.media.mediaId
					+ '" data-movies-url="'
					+ data.result.requestContextPath
					+ '/gallery/movie/'
					+ data.result.media.mediaId
					+ '" data-file-extension=".'
					+ data.result.media.extension
					+ '" />'));
				$("#thumbnail-movie-" + data.result.media.mediaId).on("click", selectContents);
			}
		},
		fail : function(e, data){
			$(".errorMessage").remove();
			$.each(data.jqXHR.responseJSON.messages, function(index, message){
				$("#thumbnailPanel").before($('<span class="errorMessage">'
						+ message
						+ '</span>'));
			});
		}
	}).on('fileuploadprogressall', function (e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css(
            'width',
            progress + '%'
        );
    });
}