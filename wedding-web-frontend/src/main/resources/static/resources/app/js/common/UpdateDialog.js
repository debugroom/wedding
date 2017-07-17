function closeUpdateDialog(){
	event.currentTarget.parentNode.parentNode.removeChild(
			event.currentTarget.parentNode);
}

function addWarningMessage(event, target){
	var errorFragment = document.getElementById(event.currentTarget.name + "-error-message");
	var warningFragment = document.getElementById(event.currentTarget.name + "-warning-message");
	if(null == warningFragment || null != errorFragment){
		if(null != errorFragment){
			target.removeChild(errorFragment); 
		}
		var newWarningFragment = document.createDocumentFragment();
		var warningPanel = document.createElement("div");
		warningPanel.setAttribute("id", event.currentTarget.name + "-warning-message");
		warningPanel.setAttribute("class", "warningMessage");
		warningPanel.appendChild(document.createTextNode("!下の「更新を確定する」ボタンを押して、更新を確定してください。"));
		newWarningFragment.appendChild(warningPanel);
		target.appendChild(newWarningFragment); 
	}
}

function addErrorMessage(event, target){
	var errorFragment = document.getElementById(event.currentTarget.name + "-error-message");
	var warningFragment = document.getElementById(event.currentTarget.name + "-warning-message");
	if(null == errorFragment || null != warningFragment){
		if(null != warningFragment){
			target.removeChild(warningFragment); 
		}
		var newErrorFragment = document.createDocumentFragment();
		var errorPanel = document.createElement("div");
		errorPanel.setAttribute("id", event.currentTarget.name + "-error-message");
		errorPanel.setAttribute("class", "errorMessage");
		errorPanel.appendChild(document.createTextNode("入力値が異なります。"));
		newErrorFragment.appendChild(errorPanel);
		target.appendChild(newErrorFragment); 
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