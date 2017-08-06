document.addEventListener('DOMContentLoaded', function(){
	
	var getAddressButtonElement = document.getElementById("get-address-button");
	var confirmLoginIdButtonElement = document.getElementById("confirm-loginId-button");

	getAddressButtonElement.addEventListener("click", function(event){
		getAddressList(event);
	}, false);

	confirmLoginIdButtonElement.addEventListener("click", function(event){
		confirmLoginId(event);
	}, false);

});

function confirmLoginId(event){
    var loginIdFormElement = event.currentTarget.parentNode.firstElementChild;
    var loginId = loginIdFormElement.value;
    var xhr = new XMLHttpRequest();
    
    xhr.onreadystatechange = function(){
    	if(xhr.readyState == 4){
			var messagesElement = document.getElementById("exists-loginId-message");
			if(messagesElement == null){
				messagesElement = document.createElement("div");
				messagesElement.setAttribute("id", "exists-loginId-message");
				loginIdFormElement.parentNode.appendChild(messagesElement);
			}else{
				while(messagesElement.firstChild){
					messagesElement.removeChild(messagesElement.firstChild);
				}
			}
    		if(xhr.status == 200){
    			if(exists(xhr.responseText) == true){
    				messagesElement.setAttribute("class", "warningMessage");
    				messagesElement.appendChild(document.createTextNode("既に同じIDが使用されています。"))
    			}else{
    				messagesElement.setAttribute("class", "successMessage");
    				messagesElement.appendChild(document.createTextNode("使用可能です。"))
    			}
    		}else if(xhr.status == 400){
   				messagesElement.setAttribute("class", "errorMessage");
   				messagesElement.appendChild(document.createTextNode(getErrorMessages(xhr.responseText)))
    		}
    	}
   	}

	xhr.open('GET', 'http://localhost:8080/wedding/user?loginId=' + loginId);
	xhr.setRequestHeader('If-Modified-Since', 'Thu, 01 Jun 1970 00:00:00 GMT');
	xhr.send(null);
    
}

function getAddressList(event){

	var zipCodeFormElement = event.currentTarget.parentNode.firstElementChild;
	var zipCode = zipCodeFormElement.value;
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			var errorMessagesElement = document.getElementById("postCd-error-message");
			if(xhr.status == 200){
				var addressFormElement = document.getElementById("address.address");
				addressFormElement.value = getAddressDetail(xhr.responseText);
				if(errorMessagesElement != null){
					errorMessagesElement.parentNode.removeChild(errorMessagesElement);
				}
			}else if(xhr.status == 400){
				var getAddressButtonElement = document.getElementById("get-address-button");
				if(errorMessagesElement == null){
					errorMessagesElement = document.createElement("div");
					errorMessagesElement.setAttribute("id", "postCd-error-message");
					errorMessagesElement.setAttribute("class", "warningMessage");
					errorMessagesElement.appendChild(document.createTextNode(getErrorMessages(xhr.responseText)));
					getAddressButtonElement.parentNode.appendChild(errorMessagesElement);
				}else{
					errorMessagesElement.innerText = getErrorMessages(xhr.responseText);
				}
			}
		}
	}

	xhr.open('GET', 'http://localhost:8080/wedding/address?zipCode=' + zipCode);
	xhr.setRequestHeader('If-Modified-Since', 'Thu, 01 Jun 1970 00:00:00 GMT');
	xhr.send(null);

}

function getAddressDetail(jsonData){
	var data = JSON.parse(jsonData);
	return data.address.fullAddress;
}

function getErrorMessages(jsonData){
	var data = JSON.parse(jsonData);
	return data.messages;
}

function exists(jsonData){
	var data = JSON.parse(jsonData);
	return data.exists;
}
