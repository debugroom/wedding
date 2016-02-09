function showUpdateDialog(event){
	
	var value = event.currentTarget.value;
	var target = replaceArrayExpression(value); 
	
	var panelElement = document.createElement("div");
	panelElement.setAttribute("class", "editPanel");
	var inputElement = document.createElement("input");
	
	switch (target){
		case "userName" :
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
	}
	
	if(!inputElement.getAttribute("type") == "file"){
		inputElement.setAttribute("value", document.getElementById(value).value);
	}
	
	panelElement.appendChild(inputElement);
	inputElement.setAttribute("id", value + "-edit");
	inputElement.setAttribute("name", value + "-edit");
	
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

	var cancelButtonElement = document.createElement("button");
	var cancelButtonTitle = document.createTextNode("閉じる");
	cancelButtonElement.setAttribute("id", "close");
	cancelButtonElement.addEventListener("click", function(event){
		closeUpdateDialog(event);},
		false
	);

	cancelButtonElement.appendChild(cancelButtonTitle);
	panelElement.appendChild(cancelButtonElement);
	
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

function closeUpdateDialog(){
	event.currentTarget.parentNode.parentNode.removeChild(
			event.currentTarget.parentNode);
}

function addWarningMessage(event, target){
	if(null == document.getElementById(event.currentTarget.name + "-message")){
		var warningFragment = document.createDocumentFragment();
		var warningPanel = document.createElement("div");
		warningPanel.setAttribute("id", event.currentTarget.name + "-message");
		warningPanel.setAttribute("class", "warningMessage");
		warningPanel.appendChild(document.createTextNode("!下のボタンを押して、更新を確定してください。"));
		warningFragment.appendChild(warningPanel);
		target.appendChild(warningFragment); 
	}
}

function replaceArrayExpression(value){
	var regExp = new RegExp("[0-9a-zA-Z¥.]+[¥[].*");
	if(regExp.test(value)){
		var splitString = value.slice(0, value.search("[¥[]"));
		return splitString;
	}
	return value;	
}