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
		addWarningMessage(event, updateRootNode);
   	    updateRootNode.removeChild(updatePanel);
	}else if(inputItem.type == "password"){
		var inputItem1 = document.getElementById("credentials[0].credentialKey-edit");
		var inputItem2 = document.getElementById("credentials[1].credentialKey-edit");
		if(inputItem1.value != "" && inputItem2.value != ""){
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
		}else{
				updateRootNode.removeChild(updatePanel);
		}
	}else if(inputItem.id == "lastName-edit"){
		var inputItem1 = document.getElementById("lastName-edit");
		var inputItem2 = document.getElementById("firstName-edit");
		if(inputItem1.value != ""){
			document.getElementById("lastName").value = inputItem1.value;
			addWarningMessage(event, updateRootNode);
		}else{
			inputItem1.value = document.getElementById("lastName").value;
		}
		if(inputItem2.value != ""){
			document.getElementById("firstName").value = inputItem2.value;
			addWarningMessage(event, updateRootNode);
		}else{
			inputItem2.value = document.getElementById("firstName").value;
		}
		document.getElementById("userName").textContent = inputItem1.value + " " + inputItem2.value;
   	    updateRootNode.removeChild(updatePanel);
	}else{
        //ファイルアップロード以外には、Hidden項目の変更
		if(inputItem.value != ""){
			document.getElementById(event.currentTarget.name).value
				= inputItem.value;
			displayValue.textContent = inputItem.value;
			addWarningMessage(event, updateRootNode);
		}
		updateRootNode.removeChild(updatePanel);
	}
}

