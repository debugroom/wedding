document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("editUser");
	var buttonNodeList = formElement.getElementsByTagName("button");
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	for(var i=0 ; i < buttonArray.length ; i++){
		buttonArray[i].addEventListener("click", function(event){
			showUpdateDialog(event);
		}, false);
	}
}, false)

function updateParam(event){
	
	var updateRootNode = event.currentTarget.parentNode.parentNode;
	var updatePanel = event.currentTarget.parentNode;
	var displayValue = updateRootNode.firstChild;
	var inputItem = updatePanel.firstElementChild;

	addWarningMessage(event, updateRootNode);

	//ファイルアップロード時にform属性にenctype属性を付与し、Hiddenを追加
	if(inputItem.type == "file"){
		// Form要素を取得し、enctypeを付与。
		var formElement = document.getElementById("editUser");
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
   	    updateRootNode.removeChild(updatePanel);
	}else if(inputItem.type == "password"){
		var inputItem1 = document.getElementById("credentials[0].credentialKey-edit");
		var inputItem2 = document.getElementById("credentials[1].credentialKey-edit");
		document.getElementById("credentials[0].credentialKey").value = 
			inputItem1.value;
		document.getElementById("credentials[1].credentialKey").value = 
			inputItem2.value;
		if(inputItem1.value == inputItem2.value){
			addWarningMessage(event, updateRootNode);
			updateRootNode.removeChild(updatePanel);
		}else{
			addErrorMessage(event, updateRootNode);
		}
	}else if(inputItem.id == "lastName-edit"){
		var inputItem1 = document.getElementById("lastName-edit");
		var inputItem2 = document.getElementById("firstName-edit");
		if(inputItem1.value != ""){
			document.getElementById("lastName").value = inputItem1.value;
		}else{
			inputItem1.value = document.getElementById("lastName").value;
		}
		if(inputItem2.value != ""){
			document.getElementById("firstName").value = inputItem2.value;
		}else{
			inputItem2.value = document.getElementById("firstName").value;
		}
		document.getElementById("userName").textContent = inputItem1.value + " " + inputItem2.value;
		addWarningMessage(event, updateRootNode);
   	    updateRootNode.removeChild(updatePanel);
	}else{
        //ファイルアップロード以外には、Hidden項目の変更
		document.getElementById(event.currentTarget.name).value
			= inputItem.value;
		displayValue.textContent = inputItem.value;
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
		case "lastName" :
			inputElement.setAttribute("type", "text");
			break;
		case "imageFilePath" :
			inputElement.setAttribute("type", "file");
			break;
		case "loginId" :
			inputElement.setAttribute("type", "text");
			break;
		case "authorityLevel" :
			inputElement.setAttribute("type", "number");
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
		case "groups" :
			inputElement.setAttribute("type", "text");
			break;
		case "credentials" :
			inputElement.setAttribute("type", "password");
			break;
		case "infomation.title" :
			inputElement.setAttribute("type", "text");
			break;
		case "infomation.releaseDate" :
			inputElement.setAttribute("type", "text");
			break;
		case "request.title" :
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

	if(inputElement.getAttribute("type") == "number"){
		inputElement.setAttribute("placeholder", document.getElementById(target).value);
	}

	if(inputElement.getAttribute("type") == "email"){
		inputElement.setAttribute("placeholder", document.getElementById(value).value);
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
		additionalInputElement.setAttribute("id", "credentials[1].credentialKey-edit");
		additionalInputElement.setAttribute("name", "credentials[1].credentialKey-edit");
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