var stompClient = null;

jQuery("[id^=tab-]").on("click", switchTab);
jQuery("[id^=tab-]").on("click", getMessages);
jQuery("[id^=tab-]").on("click", connect);
jQuery("[id=add-group-icon]").on("click", showGroupPanel);

function switchTab(){
	var messageBoardId = $(this).data("messageBoardId");
	$("[id^=content-]").css("display", "none");
	$("#content-" + messageBoardId).css("display", "block");
}

function getMessages(){
	var groupPanel = $("#group-panel");
	if(groupPanel != null){
		groupPanel.remove();
	}
	var messageBoardId = $(this).data("messageBoardId");
	var userId = $(this).data("userId");
	$("#content-" + $(this).data("messageBoardId")).empty();
	$.get($(this).data("messagesUrl"), function(data){
		$.each(data.messages, function(i, val){
   		    var userId = $("#tab-" + val.messagepk.messageBoardId).data("userId");
			var classType;
			if(userId == val.user.userId){
				classType = "message self";
			}else{
				classType = "message";
			}
			$("#content-" + val.messagepk.messageBoardId)
					.append($('<p class="'+ classType + '"><span class="icon"><img src="' 
							+ data.requestContextPath
							+ '/chat/profile/image/'
							+ val.user.userId + "/xxx" + getExtension(val.user.imageFilePath)
							+ '"><span class="name">'
							+ val.user.lastName + " " + val.user.firstName
							+ '</span></span><span class="body">'
							+ val.comment
							+ '</span></p>'));
		});
		$("#content-" + messageBoardId)
				.append($('<div id="form-' 
						+ messageBoardId
						+ '" class="form"><label for="comment">Type message.</label><input type="text" id="input-' 
						+ messageBoardId
						+ '" class="comment" name="comment" placeholder="message here..."/><button id="send-' 
						+ messageBoardId
						+ '" data-message-board-id="' 
						+ messageBoardId
						+ '" data-user-id="' 
						+ userId
						+ '" data-request-context-path="'
						+ data.requestContextPath
						+ '" type="button">Send</button></div>'));
        $("#content-" + messageBoardId).append(
			'<div class="group-update-link">グループの変更・削除は<a id="group-update-link-' 
					+ messageBoardId
					+ '" href="javascript:void(0)" data-url="'
					+ data.requestContextPath
					+ '/chat/update/message-board/' 
					+ messageBoardId
					+ ' " onclick="getMessageBoard(this)">こちら</a></div>');
        $("#send-" + messageBoardId).on("click", sendMessage);

	});
}

function getMessageBoard(event){
	var test = $(event);
	var groupUpdatePanel = $(".group-edit-panel");
	if(groupUpdatePanel != null){
		groupUpdatePanel.remove();
	}
	$.get($(event).data("url"), function(data){
		$("#content-" + data.messageBoard.messageBoardId).append(
				$('<div id="groupEditForm" class="group-edit-panel">' 
						+ '<h4>このメッセージグループを編集</h4><input id="input-edit-group-name" name="groupName" type="text" placeholder="グループ名を入力" value="' 
						+ data.messageBoard.title
						+ '">'
						+ '<div class="selective-button">'
						+ '<button id="submit-edit-group-button-' 
						+ data.messageBoard.messageBoardId
						+ '" class="selective-first-button" type="button" data-url="' 
						+ data.requestContextPath
						+ '/chat/update/message-board/' 
						+ data.messageBoard.messageBoardId
						+ '" data-message-board-id="' 
						+ data.messageBoard.messageBoardId
						+ '">変更</button>'
						+ '<button id="submit-delete-group-button-' 
						+ data.messageBoard.messageBoardId
						+ '" class="selective-median-button" type="button" data-url="' 
						+ data.requestContextPath
						+ '/chat/delete/message-board/' 
						+ data.messageBoard.messageBoardId
						+ '" data-message-board-id="'
						+ data.messageBoard.messageBoardId
						+ '" >グループを削除</button>'
						+ '<button id="close-edit-button-' 
						+ data.messageBoard.messageBoardId
						+ '" class="selective-last-button" type="button" data-message-board-id="' 
						+ data.messageBoard.messageBoardId
						+ '">キャンセル</button>' 
						+ '</div>'
						+ '</div>'));
		$("#input-edit-group-name").after($('<table id="delete-users-table">'
				+ '<thead>'
				+ '<tr>'
				+ '<th>No</th>'
				+ '<th>削除</th>'
				+ '<th>ユーザ</th>'
				+ '<th>列席</th>'
				+ '</tr>'
				+ '</thead>'
				+ '<tbody>'
				+ '</tbody>'
				+ '</table>'));

		$("#input-edit-group-name").after($('<table id="add-users-table">'
				+ '<thead>'
				+ '<tr>'
				+ '<th>No</th>'
				+ '<th>追加</th>'
				+ '<th>ユーザ</th>'
				+ '<th>列席</th>'
				+ '</tr>'
				+ '</thead>'
				+ '<tbody>'
				+ '</tbody>'
				+ '</table>'));
		$.each(data.messageBoard.group.users, function(i, val){
			var status = null;
			if(val.brideSide == true){
				status = "新婦側";
			}else{
				status = "新郎側";
			}
			$("#delete-users-table tbody").append(
					$('<tr>'
							+ '<td>'
							+ (i + 1)
							+ '</td>'
							+ '<td>'
							+ '<input id="checkedDeleteUsers[' + i + ']" type="checkbox" data-user-id="' + val.userId + '" />'
							+ '</td>'
							+ '<td>'
							+ val.lastName + ' ' + val.firstName
							+ '</td>'
							+ '<td>'
							+ status
							+ '</td>'
							+ '</tr>'));
			
		});
		$.each(data.notBelongUsers, function(i, val){
			var status = null;
			if(val.brideSide == true){
				status = "新婦側";
			}else{
				status = "新郎側";
			}
			$("#add-users-table tbody").append(
					$('<tr>'
							+ '<td>'
							+ (i + 1)
							+ '</td>'
							+ '<td>'
							+ '<input id="checkedAddUsers[' + i + ']" type="checkbox" data-user-id="' + val.userId + '" />'
							+ '</td>'
							+ '<td>'
							+ val.lastName + ' ' + val.firstName
							+ '</td>'
							+ '<td>'
							+ status
							+ '</td>'
							+ '</tr>'));
			
		});
		$(':checkbox').change(setHiddenParameterForCheckedUserId);
		$("#submit-edit-group-button-" + data.messageBoard.messageBoardId).on("click", updateGroup);
		$("#submit-delete-group-button-" + data.messageBoard.messageBoardId).on("click", deleteGroup);
		$("#close-edit-button-" + data.messageBoard.messageBoardId).on("click", closeEditGroup);

	});
}

function showMessage(message){
    var userId = $("#tab-" + message.messagepk.messageBoardId).data("userId");
    var requestContextPath = $("#tab-" + message.messagepk.messageBoardId).data("requestContextPath");
	if(userId == message.user.userId){
		classType = "message self";
	}else{
		classType = "message";
	}
	$("#form-" + message.messagepk.messageBoardId)
		.before($('<p class="'+ classType + '"><span class="icon"><img src="' 
			+ requestContextPath
			+ '/chat/profile/image/'
			+ message.user.userId + "/xxx" + getExtension(message.user.imageFilePath)
			+ '"><span class="name">'
			+ message.user.lastName + " " + message.user.firstName
			+ '</span></span><span class="body">'
			+ message.comment
			+ '</span></p>'));
    $("#input-" + message.messagepk.messageBoardId).val("");
}


function connect() {
	var messageBoardId = $(this).data("messageBoardId");
	var userId = $(this).data("userId");
    var socket = null;
    var endpoint = null;
    var subscription = null;
	if(messageBoardId === 0){
		endpoint = $(this).data("requestContextPath") + "/messages/broadcast";
		subscription = "/topic/broadcast";
	}else{
		endpoint = $(this).data("requestContextPath") + "/messages/" + messageBoardId;
		subscription = "/topic/user-" + userId;
	}
    if(stompClient !== null){
        stompClient.disconnect();
    }
	socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(subscription, function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function showGroupPanel(event){
	$("[id^=content-]").css("display", "none");
	var groupPanel = $("#group-panel");
	if(groupPanel != null){
		groupPanel.remove();
	}
	$("#tab").after($('<div id="group-panel"><h4>新しいメッセージグループ作成</h4></div>'))
	$("#group-panel").append('<div id="groupForm">' 
			+ '<input id="input-group-name" name="groupName" type="text" placeholder="グループ名を入力(必須)" />'
			+ '<div class="alternative-button">'
			+ '<button id="submit-group-button" class="alternative-first-button" type="button" data-url="' + $(this).data("url") + '">グループ作成</button>'
			+ '<button id="close-button" class="alternative-last-button" type="button" >キャンセル</button>'
			+ '</div>'
			+ '</div>');
	$.get($(this).data("getUsersUrl"), function(data){
		var usersTable = $("#users-table");
		if(usersTable != null){
			usersTable.remove();
		}
		$("#input-group-name").after($('<table id="users-table">'
				+ '<thead>'
				+ '<tr>'
				+ '<th>No</th>'
				+ '<th>追加</th>'
				+ '<th>ユーザ</th>'
				+ '<th>列席</th>'
				+ '</tr>'
				+ '</thead>'
				+ '<tbody>'
				+ '</tbody>'
				+ '</table>'));
		$.each(data.users, function(i, val){
			var status = null;
			if(val.brideSide == true){
				status = "新婦側";
			}else{
				status = "新郎側";
			}
			$("#users-table tbody").append(
					$('<tr>'
							+ '<td>'
							+ (i + 1)
							+ '</td>'
							+ '<td>'
							+ '<input id="checkedUsers[' + i + ']" type="checkbox" data-user-id="' + val.userId + '" />'
							+ '</td>'
							+ '<td>'
							+ val.lastName + ' ' + val.firstName
							+ '</td>'
							+ '<td>'
							+ status
							+ '</td>'
							+ '</tr>'));
			
		});
		$(':checkbox').change(setHiddenParameterForCheckedUserId);
	});

	$("#submit-group-button").on("click", addGroup);
	$("#close-button").on("click", function(){$("#group-panel").remove();});
	
}

function sendMessage() {
	var endpoint = null;
	var messageBoardId = $(this).data("messageBoardId");
	var userId = $(this).data("userId");
	if(messageBoardId === 0){
		endpoint = $(this).data("requestContextPath") + "/chat/messages/broadcast";
	}else{
		endpoint = $(this).data("requestContextPath") + "/chat/messages/" + messageBoardId;
	}
    stompClient.send(endpoint, {}, JSON.stringify(
    		{'comment': $("#input-" + messageBoardId).val(),
    			'user': { 'userId' : userId },
    		'messagepk': { 'messageBoardId' : messageBoardId }}));
    

}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function addGroup(){
	var users = [];
	var hiddenForm = $("#users-table input:hidden");
	for(var i=0 ; i < hiddenForm.length ; i++){
		users[i] = { "userId" : $(hiddenForm[i]).val() }
	}
	var groupName = $("#input-group-name").val();
	if(groupName == ""){
		groupName = "新規グループ"
	}
	var group = {
			"groupName" : groupName,
			"users" : users,
	};
	disconnect();
	$.ajax({
		     type : "post",
		     url  :  $(this).data("url"),
		     data : JSON.stringify(group),
      contentType : "application/json",
         dataType : "json",
	}).then(
			function(data){
				updateMessageBoard(data);
			},
			function(data){
				$(".errorMessage").remove();
				$.each(data.responseJSON.messages, function(index, message){
					$("#input-group-name").after($('<span class="errorMessage">'
							+ message + '</span>'));
				});
			}
	);
}

function updateGroup(){
	var messageBoardId = $(this).data("messageBoardId")
	var checkedAddUsers = [];
	var checkedDeleteUsers = [];
	var addUsersHiddenForm = $("#add-users-table input:hidden");
	var deleteUsersHiddenForm = $("#delete-users-table input:hidden");
	
	for(var i=0 ; i < addUsersHiddenForm.length ; i++){
		checkedAddUsers[i] = { "userId" : $(addUsersHiddenForm[i]).val() }
	}

	for(var i=0 ; i < deleteUsersHiddenForm.length ; i++){
		checkedDeleteUsers[i] = { "userId" : $(deleteUsersHiddenForm[i]).val() }
	}

	var title = $("#input-edit-group-name").val();
	if(title == ""){
		title = "　";
	}
	
	var updateMessageBoardForm = {
			"messageBoard" : { 
				"messageBoardId" : messageBoardId,
				"title" : title 
			},
			"checkedAddUsers" : checkedAddUsers,
			"checkedDeleteUsers" : checkedDeleteUsers,
	}
	$.ajax({
	     type : "post",
	     url  :  $(this).data("url"),
	     data : JSON.stringify(updateMessageBoardForm),
 contentType : "application/json",
    dataType : "json",
	}).then(
		function(data){
			disconnect();
			updateMessageBoard(data);
			$.get(data.requestContextPath 
					+ "/chat/message-board/" 
					+ data.messageBoard.messageBoardId, function(result){
				$.each(result.messages, function(i, val){
		   		    var userId = $("#tab-" + val.messagepk.messageBoardId).data("userId");
					var classType;
					if(userId == val.user.userId){
						classType = "message self";
					}else{
						classType = "message";
					}
					$("#form-" + val.messagepk.messageBoardId)
							.before($('<p class="'+ classType + '"><span class="icon"><img src="' 
									+ data.requestContextPath
									+ '/chat/profile/image/'
									+ val.user.userId + "/xxx" + getExtension(val.user.imageFilePath)
									+ '"><span class="name">'
									+ val.user.lastName + " " + val.user.firstName
									+ '</span></span><span class="body">'
									+ val.comment
									+ '</span></p>'));
				});
			});
		},
		function(data){
			$(".errorMessage").remove();
			$.each(data.responseJSON.messages, function(index, message){
				$("#input-edit-group-name").after($('<span class="errorMessage">'
						+ message + '</span>'));
			});
		}
		);
}

function deleteGroup(){
	var messageBoardId = $(this).data("messageBoardId");
	var deleteMessageBoardForm = {
			"messageBoard" : {
				"messageBoardId" : messageBoardId,
			}
	}
	$.ajax({
	     type : "delete",
	     url  :  $(this).data("url"),
	     data : JSON.stringify(deleteMessageBoardForm),
contentType : "application/json",
   dataType : "json",
	}).then(
		function(data){
			deleteMessageBoard(data);
		},
		function(data){}
		);
}

function closeEditGroup(){
	$(".group-edit-panel").remove();
}

function updateMessageBoard(data){
	var tab = $("#tab-" + data.messageBoard.messageBoardId);
	var content = $("#content-" + data.messageBoard.messageBoardId);
	if(tab != null){
		tab.remove();
	}
	if(content != null){
		content.remove();
	}
	$("#tab ul").append($(
			'<li id="tab-' 
			+ data.messageBoard.messageBoardId 
			+ '" class="tab" data-user-id="' 
			+ data.userId 
			+ '" data-request-context-path="' 
			+ data.requestContextPath 
			+ '" data-message-board-id="' 
			+ data.messageBoard.messageBoardId 
			+ '" data-messages-url="'
			+ data.requestContextPath
			+ '/chat/message-board/'
			+ data.messageBoard.messageBoardId 
			+ '">'
			+ data.messageBoard.title
			+ '</li>'));
	$("#content").append($(
			'<div id="content-' 
			+ data.messageBoard.messageBoardId 
			+ '" class="content"><div id="form-' 
			+ data.messageBoard.messageBoardId 
			+ '" class="form"><label for="comment">Type message.</label><input type="text" id="input-' 
			+ data.messageBoard.messageBoardId 
			+ '" class="comment" name="comment" placeholder="message here..."><button id="send-'
			+ data.messageBoard.messageBoardId 
			+ '" data-message-board-id="'
			+ data.messageBoard.messageBoardId 
			+ '" data-user-id="' 
			+ data.userId
			+ '" data-request-context-path="'
			+ data.requestContextPath
			+ '" type="button">Send</button></div>'));
    $("#content-" + data.messageBoard.messageBoardId).append(
			'<div class="group-update-link">グループの変更・削除は<a id="group-update-link-' 
					+ data.messageBoard.messageBoardId
					+ '" href="javascript:void(0)" data-url="' 
					+ data.requestContextPath
					+ '/chat/update/message-board/' 
					+ data.messageBoard.messageBoardId
					+ ' " onclick="getMessageBoard(this)">こちら</a></div>');
	$("[id^=content-]").css("display", "none");
	$("#content-" + data.messageBoard.messageBoardId).css("display", "block");
	$("#group-panel").remove();
    $("#tab-" + data.messageBoard.messageBoardId).on("click", switchTab);
    $("#tab-" + data.messageBoard.messageBoardId).on("click", getMessages);
    $("#tab-" + data.messageBoard.messageBoardId).on("click", connect);
    $("#send-" + data.messageBoard.messageBoardId).on("click", sendMessage);
    var endpoint = data.requestContextPath + "/messages/" + data.messageBoard.messageBoardId;
    var subscription = "/topic/user-" + data.userId;
	socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(subscription, function (message) {
            showMessage(JSON.parse(message.body));
        });
    });

}

function deleteMessageBoard(data){
	$("#tab-" + data.messageBoard.messageBoardId).remove();
	$("#content-" + data.messageBoard.messageBoardId).remove();
}

function setHiddenParameterForCheckedUserId(){
	if($(this).prop('checked')){
		$(this).after('<input id="' + $(this).prop('id') + '.userId" name="userId" type="hidden" value="' + $(this).data("userId") + '" />');
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