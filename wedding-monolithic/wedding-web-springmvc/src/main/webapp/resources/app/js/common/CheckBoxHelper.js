function setHiddenParameterForUserId(event){

	var checkbox = event.currentTarget;
	var parentNode = event.currentTarget.parentNode;
	
	if(checkbox.value == "on"){
        var userIdHiddenElement = document.getElementById("checkedUsers[" + checkbox.name + "].userId");
        var userNameHiddenElement = document.getElementById("checkedUsers[" + checkbox.name + "].userName");
        parentNode.removeChild(userIdHiddenElement);
        parentNode.removeChild(userNameHiddenElement);
        checkbox.value = "off";
	}else{
		var userIdElement = document.getElementById("users[" + checkbox.name + "].userId");
		var userIdHiddenElement = document.createElement("input");
		userIdHiddenElement.setAttribute("type", "hidden");
		userIdHiddenElement.setAttribute("id", "checkedUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("name", "checkedUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("value", userIdElement.value);

		var userNameElement = document.getElementById("users[" + checkbox.name + "].userName");
		var userNameHiddenElement = document.createElement("input");
		userNameHiddenElement.setAttribute("type", "hidden");
		userNameHiddenElement.setAttribute("id", "checkedUsers[" + checkbox.name + "].userName");
		userNameHiddenElement.setAttribute("name", "checkedUsers[" + checkbox.name + "].userName");
		userNameHiddenElement.setAttribute("value", userNameElement.value);
		
		parentNode.appendChild(userIdHiddenElement);
		parentNode.appendChild(userNameHiddenElement);
        checkbox.value = "on";
	}
}