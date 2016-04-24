document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("infomationDetail");
	var buttonNodeList = formElement.getElementsByTagName("button");
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	for(var i=0 ; i < buttonArray.length ; i++){
		if(buttonArray[i].value == "messageBody"){
			buttonArray[i].addEventListener("click", function(event){
				showTextAreaDialog(event);
			}, false);
		}else if(buttonArray[i].id == "get-not-infomation-viewers-button"){
		}else{
			buttonArray[i].addEventListener("click", function(event){
				showUpdateDialog(event);
			}, false);
		}
	}
	
	var checkboxNodeList = formElement.getElementsByTagName("input");
	var checkboxArray = Array.prototype.slice.call(checkboxNodeList);

	for(var i=0; i < checkboxArray.length ; i++){
		if(checkboxArray[i].type == "checkbox"){
			checkboxArray[i].addEventListener("change", function(event){
				setHiddenParameterForDeleteUserId(event);
			}, false);
		}
	}
	
	var getNotInfomationViewerButtonElement = document.getElementById("get-not-infomation-viewers-button");
	getNotInfomationViewerButtonElement.addEventListener("click", function(event){
		getNotInfomationViewerList(event);
	}, false);
	
}, false)

function showTextAreaDialog(event){
	var value = event.currentTarget.value;
	
	//メッセージ本体を変更するためのパネルを作る。
	var panelElement = document.createElement("div");
	//パラメータを変更するためのパネルの属性をeditMessagePanelとする。
	panelElement.setAttribute("class", "editMessagePanel");
	// Textareaを作成する。
	var textareaElement = document.createElement("textarea");
	// メッセージ本体を取得する。
	textareaElement.value = document.getElementById(value).innerHTML;

	// 改行を送信するよう、wrap属性をhardにする。
	textareaElement.setAttribute("wrap", "hard");
	textareaElement.setAttribute("cols", "60");
	textareaElement.setAttribute("rows", "20");

	textareaElement.setAttribute("id", value + "-edit");
	textareaElement.setAttribute("name", value + "-edit");
	
	panelElement.appendChild(textareaElement);

	//キャンセルボタンを作成する。
	var cancelButtonElement = document.createElement("button");
	var cancelButtonTitle = document.createTextNode("閉じる");
	cancelButtonElement.setAttribute("id", "close");
	cancelButtonElement.addEventListener("click", function(event){
		closeUpdateDialog(event);},
		false
	);

	cancelButtonElement.appendChild(cancelButtonTitle);
	panelElement.appendChild(cancelButtonElement);
	
	//更新ボタンを作成する。
	var submitButtonElement = document.createElement("button");
	var submitButtonTitle = document.createTextNode("変更");
	submitButtonElement.setAttribute("id", "updateParam");
	submitButtonElement.setAttribute("name", value);
	submitButtonElement.setAttribute("type", "button");
	submitButtonElement.addEventListener("click", function(event){
		updateMessage(event);},
		false
	);
	submitButtonElement.appendChild(submitButtonTitle);
	panelElement.appendChild(submitButtonElement);

	document.getElementById(value).parentNode.insertBefore(
			panelElement, document.getElementById("contents3"));

}

function updateMessage(event){

	// メッセージ本文の要素を取得する。
	var messageBodyElement = document.getElementById(event.currentTarget.name);
	// Textareaに入力された更新対象のメッセージ本文を取得する。
	var updateMessageElement = event.currentTarget.parentNode.firstElementChild;
	// 更新されたメッセージ本文に置き換える。
	messageBodyElement.innerHTML = updateMessageElement.value;
	
	// Hidden要素を取得する。
	var hiddenElement = document.getElementById("infomation.messageBody");
	
	// 既に作られたHidden要素がないのであれば、新規作成する。
	if(hiddenElement == null){
		hiddenElement = document.createElement("textarea");
		hiddenElement.setAttribute("id", "infomation.messageBody");
		hiddenElement.setAttribute("name", "infomation.messageBody");
		hiddenElement.setAttribute("style", "display:none");
		document.getElementById("infomationDetail").appendChild(hiddenElement);
	}
	
	// Hidden要素に更新されたメッセージ本文を設定する。
	hiddenElement.value = updateMessageElement.value;
	
	// メッセージ編集パネルを削除する。
	closeUpdateDialog();

	// 警告メッセージを追加する。
	addWarningMessage(event, document.getElementById("warningMessageArea"));

}

function updateParam(event){

	var updateRootNode = event.currentTarget.parentNode.parentNode;
	var updatePanel = event.currentTarget.parentNode;
	var displayValue = updateRootNode.firstChild;
	var inputItem = updatePanel.firstElementChild;

	addWarningMessage(event, updateRootNode);

	if(inputItem.id == "infomation.releaseDate-edit"){
		var reg = /[¥/]/g;
		inputItem.value = inputItem.value.replace(reg, "-");
	}

	document.getElementById(event.currentTarget.name).value = inputItem.value;
	displayValue.textContent = inputItem.value;
	updateRootNode.removeChild(updatePanel);
}	

function getNotInfomationViewerList(event){
	var infoIdFormElement = document.getElementById("infomation.infoId");
	var infoId = infoIdFormElement.value;
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			var tableElement = document.getElementById("no-viewer-list-table");
			if(tableElement == null){
				var buttonElement = document.getElementById("get-not-infomation-viewers-button");
				tableElement = document.createElement("table");
				tableElement.setAttribute("id", "no-viewer-list-table");
				tableElement.innerHTML = '<tbody><tr id="no-viewer-list-table-header"><th>No</th><th>ユーザID</th><th>ユーザ名</th><th>追加</th></tr></tbody>'; 
				buttonElement.parentNode.insertBefore(tableElement, buttonElement.nextSibling);
			}
			if(xhr.status == 200){
				var json = JSON.parse(xhr.responseText);
				var users = json.users;
				var count = 1;
				for(var user in users){
					var trElement = document.createElement("tr");
					tableElement.firstElementChild.appendChild(trElement);
					trElement.setAttribute("id", "no-viewer-list-table-tr-" + count);
					var tdElementForNo = document.createElement("td");
					trElement.appendChild(tdElementForNo);
					tdElementForNo.appendChild(document.createTextNode(count))
					var tdElementForUserId = document.createElement("td");
					tdElementForUserId.setAttribute("id", "no-viewer-list-table-td-userId-" + count);
					trElement.appendChild(tdElementForUserId);
					tdElementForUserId.appendChild(document.createTextNode(users[user].userId));
					var tdElementForUserName = document.createElement("td");
					trElement.appendChild(tdElementForUserName);
					tdElementForUserName.appendChild(document.createTextNode(users[user].userName));
					var tdElementForAddCheckBox = document.createElement("td");
					trElement.appendChild(tdElementForAddCheckBox);
					var checkBoxElement = document.createElement("input")
					checkBoxElement.setAttribute("type", "checkbox");
					checkBoxElement.setAttribute("name", count);
					checkBoxElement.setAttribute("value", "off");
					checkBoxElement.setAttribute("autocomplete", "off");
					checkBoxElement.addEventListener("change", function(event){setHiddenParameterForAddUserId(event);}, false);
					tdElementForAddCheckBox.appendChild(checkBoxElement);
					count++
				}
			}else if(xhr.status == 400){
				var text = xhr.responseText;
			}
		}
	}
	
	xhr.open('GET', 'http://localhost:8080/wedding/not-infomation-viewers?infoId=' + infoId);
	xhr.setRequestHeader('If-Modified-Since', 'Thu, 01 Jun 1970 00:00:00 GMT');
	xhr.send(null);

}
	
function setHiddenParameterForAddUserId(event){

	var checkbox = event.currentTarget;
	var parentNode = event.currentTarget.parentNode;
	var tdForUserIdElement = document.getElementById("no-viewer-list-table-td-userId-" + checkbox.name);
	
	if(checkbox.value == "on"){
        var userIdHiddenElement = document.getElementById("checkedAddUsers[" + checkbox.name + "].userId");
        parentNode.removeChild(userIdHiddenElement);
        checkbox.value = "off";
	}else{
		var userIdHiddenElement = document.createElement("input");
		userIdHiddenElement.setAttribute("type", "hidden");
		userIdHiddenElement.setAttribute("id", "checkedAddUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("name", "checkedAddUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("value", tdForUserIdElement.innerText);
		
		parentNode.appendChild(userIdHiddenElement);
        checkbox.value = "on";
	}
}

function setHiddenParameterForDeleteUserId(event){

	var checkbox = event.currentTarget;
	var parentNode = event.currentTarget.parentNode;
	var hiddenUserIdElement = document.getElementById("viewUsers[" + checkbox.name + "].userId");
	
	if(checkbox.value == "on"){
        var userIdHiddenElement = document.getElementById("checkedDeleteUsers[" + checkbox.name + "].userId");
        parentNode.removeChild(userIdHiddenElement);
        checkbox.value = "off";
	}else{
		var userIdHiddenElement = document.createElement("input");
		userIdHiddenElement.setAttribute("type", "hidden");
		userIdHiddenElement.setAttribute("id", "checkedDeleteUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("name", "checkedDeleteUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("value", hiddenUserIdElement.value);
		
		parentNode.appendChild(userIdHiddenElement);
        checkbox.value = "on";
	}
}

