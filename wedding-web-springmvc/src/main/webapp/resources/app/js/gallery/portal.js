jQuery( document ).ready(function( $ ) {
    $( '#my-slider' ).sliderPro({
        width: "80%",
        height: 500,
        aspectRatio: 2,
        autoplayDelay : 4000,
        arrows : true,
        forceSize: 'fullWidth',
        thumbnailPointer : true,
        thumbnailArrows : true,
        fadeThumbnailArrows : false,
    });
});

jQuery("[id^=folder-icon-]").on("click", getFolderDetail);
jQuery("[id^=delete-folder-icon-]").on("click", displayModalWindow);

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

function getFolderDetail(){
	var detailPanel = $("#detailPanel");
	if(detailPanel != null){
		detailPanel.remove();
	}
 	$(".panel").append($('<div id="detailPanel" class="right-position"><h4>'
								+ $(this).data("folderName") + '</h4>'
								+ 'View limited only these <a id="hyper-link-getUsers" data-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" href="#">users</a>.</br>' 
								+ '</div>'));
	$("#hyper-link-getUsers").on("mouseenter", getViewers);
	$.get($(this).data("folderRelatedPhotographsUrl"), function(data){
		$.each(data.photographs, 
			function(i, val){
				$("#detailPanel")
					.append($('<img class="thumbnail-image" src="' 
							+ data.requestContextPath
							+ '/gallery/photo-thumbnail/'
							+ val.photoId
							+ '">'));
			}
		)
	});
}

jQuery("#new-folder").on("click", function(){
	var newFolderIcon = $(this);
	var folderFormPanel = $("#folderFormPanel");
	if(folderFormPanel){
		folderFormPanel.remove();
	}
	$(".panel").append($('<div id="folderFormPanel" class="right-position"><h4>フォルダ作成・編集</h4></div>'));
	$("#folderFormPanel").append($('<form id="folderForm" >'
								+   '<img id="folderIcon" src="'+ newFolderIcon.data("imageFolderIconUrl") + '" />'
								+   '<input id="folderName" name="folderName" type="text" placeholder="フォルダ名を入力(必須)"/>'
								+   '<button id="get-users-button" type="button" data-url="' + newFolderIcon.data("getUsersUrl") + '">閲覧ユーザを設定</button>'
								+   '<div class="alternative-button">'
								+     '<button id="create-folder-button" data-url="'+ newFolderIcon.data("createFolderUrl") + '" class="alternative-first-button" type="button" >フォルダを作成</button>'
								+     '<button id="folderFormPanel-close-button" class="alternative-last-button" type="button" >フォルダ作成を中止</button>'
								+   '</div>'
								+ '</form>'));
	$("#get-users-button").on("click", getNotViewers);
	$("#create-folder-button").on("click", createFolder);
	$("#folderFormPanel-close-button").on("click", clearFormPanel);
	
});

function createFolder(){
	/* 通常のPostMethodでリクエスト送信する場合
	if($("#folderName").val() == ""){
		$("#folderName").prop("value", "new folder");
	}
	var form = $("#folderForm");
	 */
	var users = [];
//	var hiddenForm = $("#folderForm").find(":hidden");
	var hiddenForm = $("#folderForm input:hidden");
	for(var i = 0; i < hiddenForm.length ; i++){
		users[i] = { "userId" : $(hiddenForm[i]).val() }
	}
	var folderName = $("#folderName").val();
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
//               data : $(form).serialize(),
               data : JSON.stringify(form),
		contentType : 'application/json',
		   dataType : "json",
		   success : function(data){
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
			   					+ '" data-folder-related-users-url="' 
			   					+ data.requestContextPath + '/gallery/folder/viewers/' + data.folder.folderId
			   					+ '" src="'
			   					+ data.requestContextPath + '/resources/app/img/Pictures.png" />'
			   					+ '<img id="delete-folder-icon-' 
			   					+ data.folder.folderId
			   					+ '" class="delete-folder-icon" data-folderId="' 
			   					+ data.folder.folderId
			   					+ '" data-url="' 
			   					+ data.requestContextPath + '/gallery/folders/' + data.folder.folderId
			   					+ '" src="' 
			   					+ data.requestContextPath + '/resources/app/img/delete.png'
			   					+ '" />'
			   					+ '<br/></div>'));
			   			$("#folder-" + data.folder.folderId).append(data.folder.folderName + "<br/>");
			   			$("#folder-icon-" + data.folder.folderId).on("click", getFolderDetail);
			   			$("#delete-folder-icon-" + data.folder.folderId).on("click", displayModalWindow);
			   			clearFormPanel();
		   			  }, 
    });
}

function getViewers(event){
	var usersPanel = $("#users-panel");
	if(usersPanel != null){
		usersPanel.remove();
	}
	$("#hyper-link-getUsers").after($('<div id="users-panel">'
							+ '<table>'
							+  '<thead>' 
							+    '<tr>' 
							+      '<th>No</th>' 
							+      '<th>ユーザ</th>' 
							+    '</tr>' 
							+  '</thead>' 
							+  '<tbody>' 
							+  '</tbody>' 
							+ '</div>'));
//	moveUsersPanelToCurrentPosition(event);
	$.get($(this).data("url"), function(data){
		$.each(data.users, 
				function(i, val){
					$("#users-panel tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+ val.userName
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
	$.get($(this).data("url"), function(data){
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
								+    '</tr>' 
								+  '</thead>' 
								+  '<tbody>' 
								+  '</tbody>' 
								+'</table>'));
		$.each(data.users, 
			function(i, val){
				$("#users-table tbody")
					.append($('<tr>' 
							+   '<td>' 
							+ (i + 1) 
							+   '</td>' 
							+   '<td>' 
							+     '<input id="checkedUsers[' + i + ']" type="checkbox" data-user-id="' + val.userId + '" />' 
							+   '</td>' 
							+   '<td>' 
							+ val.userName
							+   '</td>' 
							+ '</tr>' ));
			}
		);
		$(':checkbox').change(setHiddenParameterForCheckedUserId);
	})
}

function setHiddenParameterForCheckedUserId(){
	if($(this).prop('checked')){
		$(this).after('<input id="'+ $(this).prop('id') + '.userId" name="userId" type="hidden" value="'+ $(this).data("userId") + '" />');
	}else{
		$("#" + addEscapeSequence($(this).prop('id') + "\\.userId")).remove();
	}
}

function addEscapeSequence(value){
	return value.split("\[").join("\\[").split("\]").join("\\]");
}

function clearFormPanel(){
	$("#folderFormPanel").remove();
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
