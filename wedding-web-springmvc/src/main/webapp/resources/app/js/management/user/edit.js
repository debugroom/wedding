document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("user");
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

	if(inputItem.type == "file"){
		var formElement = document.getElementById("user");
		formElement.setAttribute("enctype", "multipart/form-data");
		if(null == document.getElementById("newImageFile")){
			updateRootNode.appendChild(inputItem);
		}else{
			var existingFileInputFormFragment = document.getElementById("newImageFile")
			updateRootNode.replaceChild(existingFileInputFormFragment, inputItem);
			
		}
		inputItem.setAttribute("id", "newImageFile");
		inputItem.setAttribute("name", "newImageFile");
	}else if(inputItem.type == "password"){
		var inputItem1 = document.getElementById("credentials[0].credentialKey-edit");
		var inputItem2 = document.getElementById("credentials[1].credentialKey-edit");
		document.getElementById("credentials[0].credentialKey").value = 
			inputItem1.value;
		document.getElementById("credentials[1].credentialKey").value = 
			inputItem2.value;
	}else{
		document.getElementById(event.currentTarget.name).value
			= inputItem.value;
		displayValue.textContent = inputItem.value;
	}
	updateRootNode.removeChild(updatePanel);
}