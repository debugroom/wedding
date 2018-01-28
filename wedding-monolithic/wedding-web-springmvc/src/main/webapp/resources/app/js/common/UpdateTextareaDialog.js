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