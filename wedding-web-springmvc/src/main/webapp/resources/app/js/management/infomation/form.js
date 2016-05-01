document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("newInfomationForm");
	var inputNodeList = formElement.getElementsByTagName("input");
	var inputArray = Array.prototype.slice.call(inputNodeList);
	
	for(var i=0 ; i < inputArray.length ; i++){
		if(inputArray[i].type == "checkbox"){
			inputArray[i].addEventListener("change", function(event){
				setHiddenParameterForUserId(event);
			}, false);
		}
	}
}, false)
