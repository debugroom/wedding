document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("infomationDetail");
	var buttonNodeList = formElement.getElementsByTagName("button");
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	for(var i=0 ; i < buttonArray.length ; i++){
		if(buttonArray[i].value == "messageBody"){
			buttonArray[i].addEventListener("click", function(event){
				showTextAreaDialog(event);
			}, false);
		}else{
			buttonArray[i].addEventListener("click", function(event){
				showUpdateDialog(event);
			}, false);
		}
	}
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

	
	
	
	

