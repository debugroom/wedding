jQuery( document ).ready(function( $ ) {
    $( '#my-slider' ).sliderPro({
    	width : '100%',
    	aspectRatio: 1.5,
        autoplayDelay : 4000,
        arrows : true,
        forceSize: 'fullWidth',
        buttons : false,
        thumbnailPointer : true,
        thumbnailArrows : true,
        fadeThumbnailArrows : false,
    });
    $("#my-slider").css({'margin':'0'});
});

jQuery("[id^=folder-icon-]").on("click", getFolderDetail);
jQuery("#new-folder").on("click", function(){
	var newFolderIcon = $(this);
	showFolderPanel(true, newFolderIcon.data("imageFolderIconUrl"),
			 "", newFolderIcon.data("getUsersUrl"), newFolderIcon.data("createFolderUrl") );
	$("#get-users-button").on("click", getNotViewers);
	$("#submit-folder-button").on("click", createFolder);
	
});
jQuery("[id^=delete-folder-icon-]").on("click", displayModalWindow);
jQuery("#video_player a").on("click", handler);

function getFolderDetail(){
	var folderFormPanel = $("#folderFormPanel")
	var detailPanel = $("#detailPanel");
	if(folderFormPanel != null){
		folderFormPanel.remove();
	}
	if(detailPanel != null){
		detailPanel.remove();
	}
 	$(".contents-panel").append($('<div id="detailPanel" class="right-position"><h4>'
								+ $(this).data("folderName") + '</h4>'
								+ 'このフォルダは、<a id="hyper-link-get-users" data-get-users-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" href="javascript:void(0)">こちらのユーザ</a>から閲覧されます。 フォルダの設定は <a id="hyper-link-edit-folder" data-folder-id="' 
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

	$("#thumbnailPanel").append($('<img class="file-upload-button-image" id="file-upload-button" src="' 
				+ '/static/resources/app/img/upload.png'
				+ '" />'));
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

function showFolderPanel(isNew, imageFolderIconUrl, viewersUrl, noViewersUrl, folderUrl){
	if(isNew){
		var detailPanel = $("#detailPanel");
		if(detailPanel){
			detailPanel.remove();
		}
	}
	var folderFormPanel = $("#folderFormPanel");
	if(folderFormPanel){
		folderFormPanel.remove();
	}
	if(isNew){
		$(".contents-panel").append($('<div id="folderFormPanel" class="right-position"><h4>フォルダ作成</h4></div>'))	
	}else{
		$("#hyper-link-edit-folder").after($('<div id="folderFormPanel"><h4>フォルダ編集</h4></div>'))	
	}
	$("#folderFormPanel").append($('<form id="folderForm" >'
			+   '<img id="folderIcon" src="'+ imageFolderIconUrl + '" />'
			+   '<input id="input-folder-id" name="folderId" type="hidden" value="' + $("#hyper-link-edit-folder").data("folderId") + '"/>'
			+   '<input id="input-folder-name" name="folderName" type="text" placeholder="フォルダ名を入力(必須)"/>'
			+   '<button id="get-users-button" type="button" data-viewers-url="' + viewersUrl + '" data-no-viewers-url="' + noViewersUrl + '">閲覧ユーザを設定</button>'
			+   '<div class="alternative-button">'
			+     '<button id="submit-folder-button" data-url="'+ folderUrl+ '" class="alternative-first-button" type="button" >フォルダ作成・編集</button>'
			+     '<button id="folderFormPanel-close-button" class="alternative-last-button" type="button" >キャンセル</button>'
			+   '</div>'
			+ '</form>'));
	$("#folderFormPanel-close-button").on("click", clearFormPanel);
}

function createFolder(){
	var users = [];
	var hiddenForm = $("#users-table input:hidden");
	for(var i = 0; i < hiddenForm.length ; i++){
		users[i] = { "userId" : $(hiddenForm[i]).val() }
	}
	var folderName = $("#input-folder-name").val();
	if(folderName == ""){
		folderName = "新しいフォルダ"
	}
	var form = {
		"folder" : {
			"folderName" : folderName,
		},
		"users" : users,
	};
	$.ajax({
		       type : "post",
		        url : $(this).data("url"), 
               data : JSON.stringify(form),
		contentType : 'application/json',
		   dataType : "json",
    }).then(
    		function(data){
	   			updateFolderIcon(data);
    		},
    		function(data){
    			$(".errorMessage").remove();
    			$.each(data.responseJSON.messages, function(index, message){
    				$("#input-folder-name").after($('<span class="errorMessage">'
    						+ message
    						+ '</span>'));
    			});
    		}
    );
}

function editFolder(){
	var editFolderIcon = $("#folder-icon-" + $(this).data("folderId")) ;
	var newFolderIcon = $("#new-folder");
	showFolderPanel(false, newFolderIcon.data("imageFolderIconUrl"),
			editFolderIcon.data("folderRelatedUsersUrl"), editFolderIcon.data("folderRelatedNoUsersUrl"), editFolderIcon.data("editFolderUrl") );
	$("#get-users-button").on("click", getUsers);
	$("#submit-folder-button").on("click", updateFolder);
	$("#input-folder-name").prop("value", editFolderIcon.data("folderName"));
}

function updateFolder(){
	var checkedDeleteUsers = [];
	var checkedAddUsers = [];
	var hiddenForm = $("#folderForm input:hidden");
	for(var i = 0; i < hiddenForm.length ; i++){
		var test = $(hiddenForm[i]).prop("id"); 
		if($(hiddenForm[i]).prop("id").match(new RegExp('^checkedAddUsers', 'g'))){
			checkedAddUsers[i] = { "userId" : $(hiddenForm[i]).val() }
		}else if($(hiddenForm[i]).prop("id").match(new RegExp('^checkedDeleteUsers', 'g'))){
			checkedDeleteUsers[i] = { "userId" : $(hiddenForm[i]).val() }
		}
	}
	var folderId = $("#input-folder-id").val();
	var folderName = $("#input-folder-name").val();
	var form = {
		"folder" : {
			"folderId"   : folderId,
			"folderName" : folderName,
		},
		"checkedAddUsers" : checkedAddUsers,
		"checkedDeleteUsers" : checkedDeleteUsers,
	};
	$.ajax({
	       type : "put",
	        url : $(this).data("url"), 
        data : JSON.stringify(form),
	contentType : 'application/json',
	   dataType : "json"
	}).then(
    		function(data){
    			$("#folder-" + data.folder.folderId).remove();
	   			updateFolderIcon(data);
    		},
    		function(data){
    			$(".errorMessage").remove();
    			$.each(data.responseJSON.messages, function(index, message){
    				$("#input-folder-name").after($('<span class="errorMessage">'
    						+ message
    						+ '</span>'));
    			});
    		}
    );
}

function deleteFolder(){
	$.ajax({
	       type : "delete",
	        url : $(this).data("url"), 
	   dataType : "json",
	   success : function(data){
		   $("#folder-" + data.folder.folderId).remove();
		   $("#detailPanel").remove();
		   clearModalPanel();
	   }
	})
}

function updateFolderIcon(data){
		$("#new-folder").before($('<div id="folder-'
				+ data.folder.folderId
				+ '" >'
				+ '<img id="folder-icon-'
				+ data.folder.folderId
				+ '" data-folder-id="' 
				+ data.folder.folderId
				+ '" data-folder-name="' 
				+ data.folder.folderName
				+ '" data-folder-related-photographs-url="' 
				+ data.requestContextPath + '/gallery/photographs/' + data.folder.folderId + '?folderName=' + data.folder.folderName
				+ '" data-folder-related-movies-url="' 
				+ data.requestContextPath + '/gallery/movies/' + data.folder.folderId + '?folderName=' + data.folder.folderName
				+ '" data-folder-related-users-url="' 
				+ data.requestContextPath + '/gallery/folder/viewers/' + data.folder.folderId
				+ '" data-folder-related-no-users-url="' 
				+ data.requestContextPath + '/gallery/folder/no-viewers/' + data.folder.folderId
				+ '" data-edit-folder-url="' 
				+ data.requestContextPath + '/gallery/folders/' + data.folder.folderId
				+ '" src="'
				+ data.requestContextPath + '/static/resources/app/img/Pictures.png" />'
				+ '<img id="delete-folder-icon-' 
				+ data.folder.folderId
				+ '" class="delete-folder-icon" data-folderId="' 
				+ data.folder.folderId
				+ '" data-url="' 
				+ data.requestContextPath + '/gallery/folders/' + data.folder.folderId
				+ '" src="' 
				+ data.requestContextPath + '/static/resources/app/img/delete.png'
				+ '" />'
				+ '<br/></div>'));
		$("#folder-" + data.folder.folderId).append(data.folder.folderName + "<br/>");
		$("#folder-icon-" + data.folder.folderId).on("click", getFolderDetail);
		$("#delete-folder-icon-" + data.folder.folderId).on("click", displayModalWindow);
		clearFormPanel();
}

function getViewers(event){
	var usersPanel = $("#users-panel");
	if(usersPanel != null){
		usersPanel.remove();
	}
	$("#hyper-link-edit-folder").after($('<div id="users-panel">'
							+ '<table>'
							+  '<thead>' 
							+    '<tr>' 
							+      '<th>No</th>' 
							+      '<th>ユーザ</th>' 
							+      '<th>列席</th>' 
							+    '</tr>' 
							+  '</thead>' 
							+  '<tbody>' 
							+  '</tbody>' 
							+ '</div>'));
//	moveUsersPanelToCurrentPosition(event);
	$.get($(this).data("getUsersUrl"), function(data){
		$.each(data.users, 
				function(i, val){
					var status = null;
					if(val.brideSide == true){
						status = "新婦側";
					}else{
						status = "新郎側"
					}
					$("#users-panel tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+ val.lastName + " " + val.firstName
								+   '</td>' 
								+   '<td>' 
								+ status
								+   '</td>' 
								+ '</tr>' ));
				}
		);
		$("#users-panel").on("mouseleave", function(){
			$("#users-panel").remove();
		})
	});
}

function getNotViewers(){
	$.get($(this).data("noViewersUrl"), function(data){
		var usersTable = $("#users-table");
		if(usersTable != null){
			usersTable.remove();
		}
		$("#get-users-button").after($('<table id="users-table">' 
								+  '<thead>' 
								+    '<tr>' 
								+      '<th>No</th>' 
								+      '<th>追加</th>' 
								+      '<th>ユーザ名</th>' 
								+      '<th>列席</th>' 
								+    '</tr>' 
								+  '</thead>' 
								+  '<tbody>' 
								+  '</tbody>' 
								+'</table>'));
		$.each(data.users, 
			function(i, val){
				var status = null;
				if(val.brideSide == true){
					status = "新婦側";
				}else{
					status = "新郎側";
				}
			$("#users-table tbody")
					.append($('<tr>' 
							+   '<td>' 
							+ (i + 1) 
							+   '</td>' 
							+   '<td>' 
							+     '<input id="checkedUsers[' + i + ']" type="checkbox" data-user-id="' + val.userId + '" />' 
							+   '</td>' 
							+   '<td>' 
							+ val.lastName + ' ' + val.firstName
							+   '</td>' 
							+   '<td>' 
							+ status
							+   '</td>' 
							+ '</tr>' ));
			}
		);
		$(':checkbox').change(setHiddenParameterForCheckedUserId);
	})
}

function getUsers(event){
	var usersTable = $("#users-table");
	if(usersTable != null){
		usersTable.remove();
	}
	$("#get-users-button").after($('<div id="users-table"><table id="viewers-table">' 
							+  '<thead>' 
							+    '<tr>' 
							+      '<th>No</th>' 
							+      '<th>削除</th>' 
							+      '<th>ユーザ名</th>' 
							+      '<th>列席</th>' 
							+    '</tr>' 
							+  '</thead>' 
							+  '<tbody>' 
							+  '</tbody>' 
							+'</table>'));	
	$.get($(this).data("viewersUrl"), function(data){
		if(data.users.length == 0){
			$("#viewers-table tbody").append($('<tr><td></td><td></td><td>no users</td><td></td></tr>'));			
		}else{
			$.each(data.users, 
				function(i, val){
					var status = null;
					if(val.brideSide == true){
						status = "新婦側";
					}else{
						status = "新郎側"
					}
					$("#viewers-table tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+     '<input id="checkedDeleteUsers[' + i + ']" type="checkbox" data-delete-user-id="' + val.userId + '" />' 
								+   '</td>' 
								+   '<td>' 
								+ val.lastName + " " + val.firstName
								+   '</td>' 
								+   '<td>' 
								+ status
								+   '</td>' 
								+ '</tr>' ));
				}
			);
			$('#viewers-table :checkbox').change(setHiddenParameterForCheckedDeleteUserId);
		}
	});
	$("#viewers-table").after($('<table id="no-viewers-table">' 
			+  '<thead>' 
			+    '<tr>' 
			+      '<th>No</th>' 
			+      '<th>追加</th>' 
			+      '<th>ユーザ名</th>' 
			+      '<th>列席</th>' 
			+    '</tr>' 
			+  '</thead>' 
			+  '<tbody>' 
			+  '</tbody>' 
			+'</table></div>'));	
	$.get($(this).data("noViewersUrl"), function(data){
		if(data.users.length == 0){
			$("#no-viewers-table tbody").append($('<tr><td></td><td></td><td>no users</td><td></td></tr>'));			
		}else{
			$.each(data.users, 
				function(i, val){
					var status = null;
					if(val.brideSide == true){
						status = "新婦側";
					}else{
						status = "新郎側"
					}
					$("#no-viewers-table tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+     '<input id="checkedAddUsers[' + i + ']" type="checkbox" data-add-user-id="' + val.userId + '" />' 
								+   '</td>' 
								+   '<td>' 
								+ val.lastName + " " + val.firstName
								+   '</td>' 
								+   '<td>' 
								+ status
								+   '</td>' 
								+ '</tr>' ));
				}
			);
			$('#no-viewers-table :checkbox').change(setHiddenParameterForCheckedAddUserId);
		}
	});
}


function selectContents(event){
	var selectedContents = $(".selectedContents");
	if(selectedContents.length == 0){
	    if(!((event.ctrlKey && !event.metaKey) 
	    		|| (!event.ctrlKey && event.metaKey))){
		// 何もコンテンツが選択おらず、ctlキーが押下されていない場合はコンテンツパネルを表示する。
	    	if($(this).data("photographsUrl") != null){
	    		showContentsPanel("photo", $(this).data("photographsUrl"), $(this).data("fileExtension"), $(this).data("photoId"));
	    	}else if($(this).data("moviesUrl") != null){
	    		showContentsPanel("movie",$(this).data("moviesUrl"), $(this).data("fileExtension"), $(this).data("movieId"));
	    	}
		}
		$(this).toggleClass("selectedContents");
		$("#download-button").toggleClass("activate");
		$("#download-button").prop("disabled", false);
		$("#delete-button").toggleClass("activate");
		$("#delete-button").prop("disabled", false);
	}else{
		// 既にコンテンツが選択済みでctlキーが押下されていない場合→新たに選択し直し、コンテンツパネルを表示する。
	    if(!((event.ctrlKey && !event.metaKey) 
	    		|| (!event.ctrlKey && event.metaKey))){
			for(var i = 0 ; i < selectedContents.length ; i++){
				$(selectedContents[i]).toggleClass("selectedContents");
			}
	    	if($(this).data("photographsUrl") != null){
	    		showContentsPanel("photo", $(this).data("photographsUrl"), $(this).data("fileExtension"), $(this).data("photoId"));
	    	}else if($(this).data("moviesUrl") != null){
	    		showContentsPanel("movie", $(this).data("moviesUrl"), $(this).data("fileExtension"), $(this).data("movieId"));
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
	}
}

function showContentsPanel(type, contentsUrl, extension, id){
	var contentsPanel = $("#contentsPanel");
	if(contentsPanel){
		contentsPanel.remove();
	}
	if(type == "photo"){
		$("#thumbnail-image-" + id).before($('<div id="contentsPanel">'
			+ '<img class="" src="'
			+ contentsUrl + "/xxx" + extension
			+ '" />'
			+ '</div>'));
	} else if(type == "movie"){
		$("#thumbnail-movie-" + id).before($('<div id="contentsPanel">'
			+ '<video controls ><source src="'
			+ contentsUrl + "/xxx" + extension
			+ '"/></video>'
			+ '</div>'));
	}
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

function downloadContents(){
	var selectedContents = $(".selectedContents");
	if(selectedContents.length != 0){
		var photographs = [];
		var movies = [];
		var photoCount = 0;
		var movieCount = 0;
		for(var i = 0; i < selectedContents.length ; i++){
			var photoId = $(selectedContents[i]).data("photoId");
			var movieId = $(selectedContents[i]).data("movieId");
			if(photoId != null){
				photographs[photoCount] = { "photoId" : photoId };
				photoCount++;
			}
			if(movieId != null){
				movies[movieCount] = { "movieId" : movieId };
				movieCount++;
			}
		}
		var media = { "photographs" : photographs, "movies" : movies };
		for(var i = 0; i < selectedContents ; i++){
			photo[i] = { "photoId" : $(selectedContents[i].data("photoId")) }
		}
		var form = {
			"media" : media
		}
		$.ajax({
			type : "post",
			url : $(this).val(),
			data : JSON.stringify(media),
			dataType : "json",
			contentType : 'application/json',
		}).then(
				function(data){
					window.location = data.responseText;
				},
				function(data){
					window.location = data.responseText;
				}
		);
	}
}

function deleteContents(){
	var selectedContents = $(".selectedContents");
	if(selectedContents.length != 0){
		var photographs = [];
		var movies = [];
		var photoCount = 0;
		var movieCount = 0;
		for(var i = 0; i < selectedContents.length ; i++){
			var photoId = $(selectedContents[i]).data("photoId");
			var movieId = $(selectedContents[i]).data("movieId");
			if(photoId != null){
				photographs[photoCount] = { "photoId" : photoId };
				photoCount++;
			}
			if(movieId != null){
				movies[movieCount] = { "movieId" : movieId };
				movieCount++;
			}
		}
		var media = { "photographs" : photographs, "movies" : movies }
		var url = $(this).val();
		$.ajax({
	   	   type : "delete",
			url : url,
    	   data : JSON.stringify(media),
    contentType : 'application/json',
   	   dataType : "json",
		}).then(
	    	function(data){
	    		$.each(data.photographs, function(i, val){
	    			$("#thumbnail-image-" + val.photoId).remove();
	    		});
		    	$.each(data.movies, function(i, val){
	    			$("#thumbnail-movie-" + val.movieId).remove();
	    		});
		    	var contentsPanel = $("#contentsPanel");
		    	if(contentsPanel){
		    		contentsPanel.remove();
		    	}
	    	},
	    	function(data){
	    		$(".errorMessage").remove();
	    		$.each(data.responseJSON.messages, function(index, message){
	    			$("#thumbnailPanel").after($('<span class="errorMessage">'
	    					+ message
	    					+ '</span>'));
	    		});
	    	}
	    );
	}	
}


function setHiddenParameterForCheckedUserId(){
	if($(this).prop('checked')){
		$(this).after('<input id="'+ $(this).prop('id') + '.userId" name="userId" type="hidden" value="'+ $(this).data("userId") + '" />');
	}else{
		$("#" + addEscapeSequence($(this).prop('id') + "\\.userId")).remove();
	}
}

function setHiddenParameterForCheckedAddUserId(){
	if($(this).prop('checked')){
		$(this).after('<input id="'+ $(this).prop('id') + '.userId" name="userId" type="hidden" value="'+ $(this).data("addUserId") + '" />');
	}else{
		$("#" + addEscapeSequence($(this).prop('id') + "\\.userId")).remove();
	}
}

function setHiddenParameterForCheckedDeleteUserId(){
	if($(this).prop('checked')){
		$(this).after('<input id="'+ $(this).prop('id') + '.userId" name="userId" type="hidden" value="'+ $(this).data("deleteUserId") + '" />');
	}else{
		$("#" + addEscapeSequence($(this).prop('id') + "\\.userId")).remove();
	}
}

function getExtension(value){
	var extension = value.split(".");
	return "." + extension[1];
}

function addEscapeSequence(value){
	return value.split("\[").join("\\[").split("\]").join("\\]");
}

function clearFormPanel(){
	$("#folderFormPanel").remove();
}

function displayModalWindow(){
	var deleteIcon = $(this);
	$("#new-folder").after($('<div id="modal-panel">'
			            + 	'<p>フォルダを削除してよろしいですか？</p>'
						+   '<div class="alternative-button">'
						+     '<button id="delete-folder-button" data-url="'+ deleteIcon.data("url") + '" class="alternative-first-button" type="button" >フォルダを削除</button>'
						+     '<button id="modalPanel-close-button" class="alternative-last-button" type="button" >キャンセル</button>'
						+   '</div>'
						+'</div>'));
	$("#delete-folder-button").on("click", deleteFolder);
	$(this).blur();	
	if($("#modal-overlay")[0]) $("#modal-overlay").remove() ;
	$("body").append('<div id="modal-overlay"></div>');
	$("#modal-overlay").fadeIn("slow");
	centeringModalSyncer() ;
	$("#modal-panel").fadeIn("slow");
	$("#modal-overlay, #modalPanel-close-button").on("click", clearModalPanel);
}

function clearModalPanel(){
	$("#modal-panel,#modal-overlay").fadeOut("slow",function(){
		$("#modal-overlay").remove();
		$("#modal-panel").remove();
	});
}

function centeringModalSyncer(){
	var w = $(window).width();
	var h = $(window).height();
	var cw = $( "#modal-panel" ).outerWidth();
	var ch = $( "#modal-panel" ).outerHeight();
	var pxleft = ((w - cw) / 2 );
	var pxtop  = ((h - ch) / 5 * 2);
	$("#modal-panel").css({"left": pxleft + "px"});
	$("#modal-panel").css({"top": pxtop + "px"});
}

function moveUsersPanelToCurrentPosition(event){
	$("#users-panel").css({"left": $(event).clientX + "px"});
	$("#users-panel").css({"top": $(event).clientY + "px"});
}

$(window).resize(centeringModalSyncer);

function handler(e) {
	e.preventDefault();
	$("#video_player video source")[0].src = this.getAttribute("href");
	$("#video_player video").load();
	document.querySelector("#video_player video").play();
}