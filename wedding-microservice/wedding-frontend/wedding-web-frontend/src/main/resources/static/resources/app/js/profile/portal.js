document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("portalResource.user");
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

function updateParam(event){
	//表示内容の変更
	//<td>
	//"表示項目"
	//	<input type="hidden" value="hiddenParam">
	//  <div class="editPanel">
	//    <input type="form" value="inputContents">
	//    <button type="button"> <--event.currentTarget
	var updateRootNode = event.currentTarget.parentNode.parentNode;
	var updatePanel = event.currentTarget.parentNode;
	var displayValue = updateRootNode.firstChild;
	var inputItem = updatePanel.firstElementChild;


	//ファイルアップロード時にform属性にenctype属性を付与し、Hiddenを追加
	if(inputItem.type == "file"){
		// Form要素を取得し、enctypeを付与。
		var formElement = document.getElementById("portalResource.user");
		formElement.setAttribute("enctype", "multipart/form-data");
		//<input type="file">項目を取得し、hidden直下に追加。
		//既に設定されているHiddenイメージファイルがないか確認
		if(null == document.getElementById("newImageFile")){
			updateRootNode.appendChild(inputItem);
		}else{
			var existingFileInputFormFragment = document.getElementById("newImageFile")
			updateRootNode.replaceChild(existingFileInputFormFragment, inputItem);
			
		}
		inputItem.setAttribute("id", "newImageFile");
		inputItem.setAttribute("name", "newImageFile");
		addWarningMessage(event, updateRootNode);
		updateRootNode.removeChild(updatePanel);
	}else if(inputItem.type == "password"){
		var inputItem1 = document.getElementById("user.credentials[0].credentialKey-edit");
		var inputItem2 = document.getElementById("user.credentials[1].credentialKey-edit");
		document.getElementById("user.credentials[0].credentialKey").value = 
			inputItem1.value;
		document.getElementById("user.credentials[1].credentialKey").value = 
			inputItem2.value;
		if(inputItem1.value == inputItem2.value){
			addWarningMessage(event, updateRootNode);
			updateRootNode.removeChild(updatePanel);
		}else{
			addErrorMessage(event, updateRootNode);
		}
	}else if(inputItem.id == "user.lastName-edit"){
		var inputItem1 = document.getElementById("user.lastName-edit");
		var inputItem2 = document.getElementById("user.firstName-edit");
		if(inputItem1.value != ""){
			document.getElementById("user.lastName").value = inputItem1.value;
		}else{
			inputItem1.value = document.getElementById("user.lastName").value;
		}
		if(inputItem2.value != ""){
			document.getElementById("user.firstName").value = inputItem2.value;
		}else{
			inputItem2.value = document.getElementById("user.firstName").value;
		}
		document.getElementById("userName").textContent = inputItem1.value + " " + inputItem2.value;
		addWarningMessage(event, updateRootNode);
		updateRootNode.removeChild(updatePanel);
	}else{
        //ファイルアップロード以外には、Hidden項目の変更
		document.getElementById(event.currentTarget.name).value
			= inputItem.value;
		displayValue.textContent = inputItem.value;
		addWarningMessage(event, updateRootNode);
		updateRootNode.removeChild(updatePanel);
	}
}

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
	
	//インプットフォームを作成する。
	switch (target){
		case "user.lastName" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.imageFilePath" :
			inputElement.setAttribute("type", "file");
			break;
		case "user.loginId" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.authorityLevel" :
			inputElement.setAttribute("type", "number");
			break;
		case "user.address.postCd" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.address.address" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.emails" :
			inputElement.setAttribute("type", "email");
			break;
		case "user.groups" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.credentials" :
			inputElement.setAttribute("type", "password");
			break;
		case "user.infomation.title" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.infomation.releaseDate" :
			inputElement.setAttribute("type", "text");
			break;
		case "user.request.title" :
			inputElement.setAttribute("type", "text");
			break;
	}

	//Formのtype属性がfile以外の場合は、現在入力されているパラメータをhiddenから取得してフォームに設定する。
	if(!inputElement.getAttribute("type") == "file"){
		inputElement.setAttribute("value", document.getElementById(value).value);
	}
	
	panelElement.appendChild(inputElement);
	inputElement.setAttribute("id", value + "-edit");
	inputElement.setAttribute("name", value + "-edit");

	if(inputElement.getAttribute("type") == "text"){
		inputElement.setAttribute("placeholder", document.getElementById(target).value);
	}

	if(inputElement.getAttribute("type") == "email"){
		inputElement.setAttribute("placeholder", document.getElementById(value).value);
	}

	//Targetがuser.lastNameのときは、firstName用のフォームを作成する。
	if(target == "user.lastName"){
		var additionalInputElement = document.createElement("input");
		additionalInputElement.setAttribute("type", "text");
		additionalInputElement.setAttribute("id", "user.firstName-edit");
		additionalInputElement.setAttribute("name", "user.firstName-edit");
		additionalInputElement.setAttribute("placeholder", document.getElementById("user.firstName").value);
		panelElement.appendChild(additionalInputElement);
	}

    //Targetがuser.lastNameのときは、firstName用のフォームを作成する。
	if(target == "lastName"){
		var additionalInputElement = document.createElement("input");
		additionalInputElement.setAttribute("type", "text");
		additionalInputElement.setAttribute("id", "firstName-edit");
		additionalInputElement.setAttribute("name", "firstName-edit");
		additionalInputElement.setAttribute("placeholder", document.getElementById("firstName").value);
		panelElement.appendChild(additionalInputElement);
	}
	
	//パスワード入力の場合は２つ入力させる。
	if(inputElement.getAttribute("type") == "password"){
		var additionalTextElement = document.createElement("p");
		var additionalText = document.createTextNode("確認のため、再入力してください。");
		var additionalInputElement = document.createElement("input");
		additionalInputElement.setAttribute("type", "password");
		additionalInputElement.setAttribute("id", "user.credentials[1].credentialKey-edit");
		additionalInputElement.setAttribute("name", "user.credentials[1].credentialKey-edit");
		additionalTextElement.appendChild(additionalText);
		panelElement.appendChild(additionalTextElement);
		panelElement.appendChild(additionalInputElement);
	}

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
		updateParam(event);},
		false
	);
	submitButtonElement.appendChild(submitButtonTitle);
	panelElement.appendChild(submitButtonElement);

	document.getElementById(value).parentNode.appendChild(panelElement);
	
}