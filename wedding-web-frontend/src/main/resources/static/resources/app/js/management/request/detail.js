document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("requestDetail");
	var buttonNodeList = formElement.getElementsByTagName("button");
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	for(var i=0 ; i < buttonArray.length ; i++){
		if(buttonArray[i].value == "messageBody"){
			buttonArray[i].addEventListener("click", function(event){
				showTextAreaDialog(event);
			}, false);
		}else if(buttonArray[i].id == "get-users-button"){
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
	
	var param = "request.requestId";
	var getNotRequestUsersButtonElement = document.getElementById("get-users-button");
	var url = getNotRequestUsersButtonElement.value;
	getNotRequestUsersButtonElement.addEventListener("click", function(event){
		getUsers(event, param, url);
	}, false);
	
}, false)

function updateMessage(event){
	// メッセージ本文の要素を取得する。
	var messageBodyElement = document.getElementById(event.currentTarget.name);
	// Textareaに入力された更新対象のメッセージ本文を取得する。
	var updateMessageElement = event.currentTarget.parentNode.firstElementChild;
	// 更新されたメッセージ本文に置き換える。
	messageBodyElement.innerHTML = updateMessageElement.value;
	
	// Hidden要素を取得する。
	var hiddenElement = document.getElementById("request.requestContents");
	
	// 既に作られたHidden要素がないのであれば、新規作成する。
	if(hiddenElement == null){
		hiddenElement = document.createElement("textarea");
		hiddenElement.setAttribute("id", "request.requestContents");
		hiddenElement.setAttribute("name", "request.requestContents");
		hiddenElement.setAttribute("style", "display:none");
		document.getElementById("requestDetail").appendChild(hiddenElement);
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

	document.getElementById(event.currentTarget.name).value = inputItem.value;
	displayValue.textContent = inputItem.value;
	updateRootNode.removeChild(updatePanel);
}