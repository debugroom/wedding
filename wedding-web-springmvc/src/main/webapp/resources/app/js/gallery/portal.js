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
jQuery("#new-folder").on("click", function(){
	var newFolderIcon = $(this);
	showFolderPanel(true, newFolderIcon.data("imageFolderIconUrl"),
			 "", newFolderIcon.data("getUsersUrl"), newFolderIcon.data("createFolderUrl") );
/*
	var folderFormPanel = $("#folderFormPanel");
	if(folderFormPanel){
		folderFormPanel.remove();
	}
	$(".panel").append($('<div id="folderFormPanel" class="right-position"><h4>フォルダ作成・編集</h4></div>'));
	$("#folderFormPanel").append($('<form id="folderForm" >'
								+   '<img id="folderIcon" src="'+ newFolderIcon.data("imageFolderIconUrl") + '" />'
								+   '<input id="input-folder-name" name="folderName" type="text" placeholder="フォルダ名を入力(必須)"/>'
								+   '<button id="get-users-button" type="button" data-url="' + newFolderIcon.data("getUsersUrl") + '">閲覧ユーザを設定</button>'
								+   '<div class="alternative-button">'
								+     '<button id="create-folder-button" data-url="'+ newFolderIcon.data("createFolderUrl") + '" class="alternative-first-button" type="button" >フォルダを作成</button>'
								+     '<button id="folderFormPanel-close-button" class="alternative-last-button" type="button" >フォルダ作成を中止</button>'
								+   '</div>'
								+ '</form>'));
	$("#folderFormPanel-close-button").on("click", clearFormPanel);
 */
	$("#get-users-button").on("click", getNotViewers);
	$("#submit-folder-button").on("click", createFolder);
	
});

function getFolderDetail(){
	var folderFormPanel = $("#folderFormPanel")
	var detailPanel = $("#detailPanel");
	if(folderFormPanel != null){
		folderFormPanel.remove();
	}
	if(detailPanel != null){
		detailPanel.remove();
	}
 	$(".panel").append($('<div id="detailPanel" class="right-position"><h4>'
								+ $(this).data("folderName") + '</h4>'
								+ 'View limited only these <a id="hyper-link-get-users" data-get-users-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" href="javascript:void(0)">users</a>. You can edit folder property in <a id="hyper-link-edit-folder" data-folder-id="' 
								+ $(this).data("folderId")
								+ '" data-edit-folder-url="' 
								+ $(this).data("editFolderUrl")
								+ '" data-folder-related-users-url="' 
								+ $(this).data("folderRelatedUsersUrl")
								+ '" data-folder-related-no-users-url="' 
								+ $(this).data("folderRelatedNoUsersUrl")
								+ '" href="javascript:void(0)" >this link</a> .' 
								+ '</br>' 
								+ '</div>'));
	$("#hyper-link-get-users").on("mouseenter", getViewers);
	$("#hyper-link-edit-folder").on("click", editFolder);
	$.get($(this).data("folderRelatedPhotographsUrl"), function(data){
		var thumbnailPanel = $("#thumbnailPanel");
		if(thumbnailPanel != null){
			thumbnailPanel.remove();
		}
	 	$("#detailPanel").append($('<div id="thumbnailPanel"></div>'));
		$.each(data.photographs, 
			function(i, val){
				$("#thumbnailPanel")
					.append($('<img class="thumbnail-image" src="' 
							+ data.requestContextPath
							+ '/gallery/photo-thumbnail/'
							+ val.photoId
							+ '">'));
			}
		)
	});
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

function editFolder(){
	var editFolderIcon = $("#folder-icon-" + $(this).data("folderId")) ;
	var newFolderIcon = $("#new-folder");
	showFolderPanel(false, newFolderIcon.data("imageFolderIconUrl"),
			editFolderIcon.data("folderRelatedUsersUrl"), editFolderIcon.data("folderRelatedNoUsersUrl"), editFolderIcon.data("editFolderUrl") );
	$("#get-users-button").on("click", getUsers);
	$("#submit-folder-button").on("click", updateFolder);
	$("#input-folder-name").prop("value", editFolderIcon.data("folderName"));
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
		$(".panel").append($('<div id="folderFormPanel" class="right-position"><h4>フォルダ作成</h4></div>'))	
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
	/* 通常のPostMethodでリクエスト送信する場合
	if($("#input-folder-name").val() == ""){
		$("#input-folder-name").prop("value", "new folder");
	}
	var form = $("#folderForm");
	 */
	var users = [];
//	var hiddenForm = $("#folderForm").find(":hidden");
	var hiddenForm = $("#folderForm input:hidden");
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
//               data : $(form).serialize(),
               data : JSON.stringify(form),
		contentType : 'application/json',
		   dataType : "json",
		   success : function(data){
			   			updateFolderIcon(data);
		   	} 
    });
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
	   dataType : "json",
	   success : function(data){
		    $("#folder-" + data.folder.folderId).remove();
		    updateFolderIcon(data);

	   }
	});
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
   					+ '" data-folder-related-users-url="' 
   					+ data.requestContextPath + '/gallery/folder/viewers/' + data.folder.folderId
   					+ '" data-folder-related-no-users-url="' 
   					+ data.requestContextPath + '/gallery/folder/no-viewers/' + data.folder.folderId
   					+ '" data-edit-folder-url="' 
   					+ data.requestContextPath + '/gallery/folders/' + data.folder.folderId
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
							+    '</tr>' 
							+  '</thead>' 
							+  '<tbody>' 
							+  '</tbody>' 
							+'</table>'));	
	$.get($(this).data("viewersUrl"), function(data){
		if(data.users.length == 0){
			$("#viewers-table tbody").append($('<tr><td></td><td></td><td>no users</td></tr>'));			
		}else{
			$.each(data.users, 
				function(i, val){
					$("#viewers-table tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+     '<input id="checkedDeleteUsers[' + i + ']" type="checkbox" data-delete-user-id="' + val.userId + '" />' 
								+   '</td>' 
								+   '<td>' 
								+ val.userName
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
			+    '</tr>' 
			+  '</thead>' 
			+  '<tbody>' 
			+  '</tbody>' 
			+'</table></div>'));	
	$.get($(this).data("noViewersUrl"), function(data){
		if(data.users.length == 0){
			$("#no-viewers-table tbody").append($('<tr><td></td><td></td><td>no users</td></tr>'));			
		}else{
			$.each(data.users, 
				function(i, val){
					$("#no-viewers-table tbody")
						.append($('<tr>' 
								+   '<td>' 
								+ (i + 1) 
								+   '</td>' 
								+   '<td>' 
								+     '<input id="checkedAddUsers[' + i + ']" type="checkbox" data-add-user-id="' + val.userId + '" />' 
								+   '</td>' 
								+   '<td>' 
								+ val.userName
								+   '</td>' 
								+ '</tr>' ));
				}
			);
			$('#no-viewers-table :checkbox').change(setHiddenParameterForCheckedAddUserId);
		}
	});
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
							+    '</tr>' 
							+  '</thead>' 
							+  '<tbody>' 
							+  '</tbody>' 
							+ '</div>'));
//	moveUsersPanelToCurrentPosition(event);
	$.get($(this).data("getUsersUrl"), function(data){
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
