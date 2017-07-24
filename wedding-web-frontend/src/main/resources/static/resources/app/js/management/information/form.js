document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("newInformationForm");
	var inputNodeList = formElement.getElementsByTagName("input");
	var inputArray = Array.prototype.slice.call(inputNodeList);
	
	for(var i=0 ; i < inputArray.length ; i++){
		if(inputArray[i].type == "checkbox" && inputArray[i].id != "allCheckbox"){
			inputArray[i].addEventListener("change", function(event){
				setHiddenParameterForUserId(event);
			}, false);
		}
	}
	
	document.getElementById("allCheckbox").addEventListener("change", function(event){
		toggleAllCheckbox();
	}, false);

}, false)

function toggleAllCheckbox(){

	var formElement = document.getElementById("newInformationForm");
	var inputNodeList = formElement.getElementsByTagName("input");
	var inputArray = Array.prototype.slice.call(inputNodeList);
	
	for(var i=0 ; i < inputArray.length ; i++){
		if(inputArray[i].type == "checkbox" && inputArray[i].id != "allCheckbox"){
			var checkbox = inputArray[i];
			var parentNode = checkbox.parentNode;
			if(checkbox.value == "on"){
		        var userIdHiddenElement = document.getElementById("checkedUsers[" + checkbox.name + "].userId");
		        var lastNameHiddenElement = document.getElementById("checkedUsers[" + checkbox.name + "].lastName");
		        var firstNameHiddenElement = document.getElementById("checkedUsers[" + checkbox.name + "].firstName");
		        parentNode.removeChild(userIdHiddenElement);
		        parentNode.removeChild(lastNameHiddenElement);
		        parentNode.removeChild(firstNameHiddenElement);
		        checkbox.value = "off";
		        checkbox.checked = false; 
			}else{
				var userIdElement = document.getElementById("users[" + checkbox.name + "].userId");
				var userIdHiddenElement = document.createElement("input");
				userIdHiddenElement.setAttribute("type", "hidden");
				userIdHiddenElement.setAttribute("id", "checkedUsers[" + checkbox.name + "].userId");
				userIdHiddenElement.setAttribute("name", "checkedUsers[" + checkbox.name + "].userId");
				userIdHiddenElement.setAttribute("value", userIdElement.value);

				var lastNameElement = document.getElementById("users[" + checkbox.name + "].lastName");
				var lastNameHiddenElement = document.createElement("input");
				lastNameHiddenElement.setAttribute("type", "hidden");
				lastNameHiddenElement.setAttribute("id", "checkedUsers[" + checkbox.name + "].lastName");
				lastNameHiddenElement.setAttribute("name", "checkedUsers[" + checkbox.name + "].lastName");
				lastNameHiddenElement.setAttribute("value", lastNameElement.value);

				var firstNameElement = document.getElementById("users[" + checkbox.name + "].firstName");
				var firstNameHiddenElement = document.createElement("input");
				firstNameHiddenElement.setAttribute("type", "hidden");
				firstNameHiddenElement.setAttribute("id", "checkedUsers[" + checkbox.name + "].firstName");
				firstNameHiddenElement.setAttribute("name", "checkedUsers[" + checkbox.name + "].firstName");
				firstNameHiddenElement.setAttribute("value", firstNameElement.value);
				
				parentNode.appendChild(userIdHiddenElement);
				parentNode.appendChild(lastNameHiddenElement);
				parentNode.appendChild(firstNameHiddenElement);
		        checkbox.value = "on";
		        checkbox.checked = true;
			}
		}
	}

}