document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("user");
	//IE8以前ではサポート外。
	var buttonNodeList = formElement.getElementsByTagName("button");
	//パフォーマンスのために、Arrayに変換、
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	//for in文の方が書きやすいが、パフォーマンス問題と、キー・値の混同が生じやすいので使わない。
	for(var i=0 ; i < buttonArray.length ; i++ ){
		//各ボタンにリスナーを設定。イベント発火タイミングでリスナーに設定された関数が評価されるため、
		//パラメータに変数を指定する場合は注意。パラメータは定数値を設定しておくか、Event経由で取得した方が確実。
		buttonArray[i].addEventListener("click", function(event){
			showUpdateDialog(event);
		}, false);
	}
}, false)

function showUpdateDialog(event){
	//UPDATE対象のパラメータを取得する。
	var value = event.currentTarget.value;
	//配列を含む場合のパラメータ表現を変更する。
	var target = replaceArrayExpression(value);

	//パラメータを変更するためのパネルを作る。
	var panelElement = document.createElement("div");
	//パラメータを変更するためのパネルの属性をmodelPanelとする。
	panelElement.setAttribute("class", "editPanel");
	//インプットフォームを作成する。
	var inputElement = document.createElement("input");
	//パラメータによってフォームの種類を変更する。
	switch (target)	{
		case "userName" : 
			inputElement.setAttribute("type", "text");
			break;
		case "imageFile" : 
			inputElement.setAttribute("type", "text");
			break;
		case "loginId" : 
			inputElement.setAttribute("type", "text");
			break;
		case "address.postCd" : 
			inputElement.setAttribute("type", "text");
			break;
		case "address.address" : 
			inputElement.setAttribute("type", "text");
			break;
		case "emails" : 
			inputElement.setAttribute("type", "email");
			break;
	}
	//現在入力されているパラメータをhiddenから取得してフォームに設定する。
	inputElement.setAttribute("value", document.getElementById(value).value);
	panelElement.appendChild(inputElement);

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
	submitButtonElement.setAttribute("type", "button");
	submitButtonElement.addEventListener("click", function(event){
		updateParam(event);},
		false
	);
	submitButtonElement.appendChild(submitButtonTitle);
	panelElement.appendChild(submitButtonElement);
	
	document.getElementById(value).parentNode.appendChild(panelElement);

}

function closeUpdateDialog(event){
	event.currentTarget.parentNode.parentNode.removeChild(
			event.currentTarget.parentNode);
}

function updateParam(event){
	//表示内容の変更
	//<td>
	//"表示項目"
	//	<input type="hidden" value="hiddenParam">
	//  <div class="panel">
	//    <input type="form" value="inputContents">
	//    <button type="button"> <--event.currentTarget
	event.currentTarget.parentNode.parentNode.firstChild.textContent = 
		event.currentTarget.parentNode.firstElementChild.value;
	//警告文を作成する。
	var test = document.getElementById(event.currentTarget.name + "-message");
	if(null == document.getElementById(event.currentTarget.name + "-message")){
		var warningFragment = document.createDocumentFragment();
		var warningPanel = document.createElement("div");
		warningPanel.setAttribute("id", event.currentTarget.name + "-message");
		warningPanel.setAttribute("class", "warningMessage");
		warningPanel.appendChild(document.createTextNode("!下のボタンを押して、更新を確定してください。"));
		warningFragment.appendChild(warningPanel);
		event.currentTarget.parentNode.parentNode.appendChild(warningFragment); 
	}

	//Hidden項目の変更
	event.currentTarget.parentNode.parentNode.firstElementChild.value = 
		event.currentTarget.parentNode.firstElementChild.value;
	//編集パネルを削除
	event.currentTarget.parentNode.parentNode.removeChild(
			event.currentTarget.parentNode);
}

function replaceArrayExpression(value){
	var regExp = new RegExp("[0-9a-zA-Z¥.]+[¥[].*");
	if(regExp.test(value)){
		var splitString = value.slice(0, value.search("[¥[]"));
		return splitString;
	}
	return value;
}