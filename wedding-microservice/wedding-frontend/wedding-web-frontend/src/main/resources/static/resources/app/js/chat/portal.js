var stompClient = null;

jQuery("[id^=tab-]").on("click", switchTab);
jQuery("[id^=tab-]").on("click", getMessages);
jQuery("[id^=tab-]").on("click", connect);

function switchTab(){
	var messageBoardId = $(this).data("messageBoardId");
	$("[id^=content-]").css("display", "none");
	$("#content-" + messageBoardId).css("display", "block");
}

function getMessages(){
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
					.append($('<p class="'+ classType + '"><span class="icon"><img src="/profile/image/'
							+ val.user.userId + "/xxx" + getExtension(val.user.imageFilePath)
							+ '"><span class="name">'
							+ val.user.firstName + " " + val.user.lastName
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
						+ '"type="button">Send</button></div>'));
        $("#send-" + messageBoardId).on("click", sendMessage);
	});
}

function showMessage(message){
    var userId = $("#tab-" + message.messagepk.messageBoardId).data("userId");
	if(userId == message.user.userId){
		classType = "message self";
	}else{
		classType = "message";
	}
	$("#form-" + message.messagepk.messageBoardId)
		.before($('<p class="'+ classType + '"><span class="icon"><img src="/profile/image/'
			+ message.user.userId + "/xxx" + getExtension(message.user.imageFilePath)
			+ '"><span class="name">'
			+ message.user.firstName + " " + message.user.lastName
			+ '</span></span><span class="body">'
			+ message.comment
			+ '</span></p>'));
}


function connect() {
	var messageBoardId = $(this).data("messageBoardId");
	var userId = $(this).data("userId");
    var socket = null;
    var endpoint = null;
    var subscription = null;
	if(messageBoardId === 0){
		endpoint = "/messages/broadcast";
		subscription = "/topic/broadcast";
	}else{
		endpoint = "/messages/" + messageBoardId;
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

function sendMessage() {
	var endpoint = null;
	var messageBoardId = $(this).data("messageBoardId");
	var userId = $(this).data("userId");
	if(messageBoardId === 0){
		endpoint = "/chat/messages/broadcast";
	}else{
		endpoint = "/chat/messages/" + messageBoardId;
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

function getExtension(value){
	var extension = value.split(".");
	return "." + extension[1];
}