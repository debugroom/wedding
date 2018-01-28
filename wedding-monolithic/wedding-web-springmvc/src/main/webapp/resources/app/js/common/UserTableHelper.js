function getUsers(event, param, url){
	var idFormElement = document.getElementById(param);
	var id = idFormElement.value;
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			var tableElement = document.getElementById("users-table");
			if(tableElement == null){
				var buttonElement = document.getElementById("get-users-button");
				tableElement = document.createElement("table");
				tableElement.setAttribute("id", "add-users-table");
				tableElement.innerHTML = '<tbody><tr id="add-users-table-header"><th>No</th><th>ユーザID</th><th>ユーザ名</th><th>追加</th></tr></tbody>'; 
				buttonElement.parentNode.insertBefore(tableElement, buttonElement.nextSibling);
			}
			if(xhr.status == 200){
				var json = JSON.parse(xhr.responseText);
				var users = json.users;
				var count = 1;
				for(var user in users){
					var trElement = document.createElement("tr");
					tableElement.firstElementChild.appendChild(trElement);
					trElement.setAttribute("id", "add-users-table-tr-" + count);
					var tdElementForNo = document.createElement("td");
					trElement.appendChild(tdElementForNo);
					tdElementForNo.appendChild(document.createTextNode(count))
					var tdElementForUserId = document.createElement("td");
					tdElementForUserId.setAttribute("id", "add-users-table-td-userId-" + count);
					trElement.appendChild(tdElementForUserId);
					tdElementForUserId.appendChild(document.createTextNode(users[user].userId));
					var tdElementForUserName = document.createElement("td");
					trElement.appendChild(tdElementForUserName);
					tdElementForUserName.appendChild(document.createTextNode(users[user].userName));
					var tdElementForAddCheckBox = document.createElement("td");
					trElement.appendChild(tdElementForAddCheckBox);
					var checkBoxElement = document.createElement("input")
					checkBoxElement.setAttribute("type", "checkbox");
					checkBoxElement.setAttribute("name", count);
					checkBoxElement.setAttribute("value", "off");
					checkBoxElement.setAttribute("autocomplete", "off");
					checkBoxElement.addEventListener("change", function(event){setHiddenParameterForAddUserId(event);}, false);
					tdElementForAddCheckBox.appendChild(checkBoxElement);
					count++
				}
			}else if(xhr.status == 400){
				var text = xhr.responseText;
			}
		}
	}
	
	xhr.open('GET', url  + id);
	xhr.setRequestHeader('If-Modified-Since', 'Thu, 01 Jun 1970 00:00:00 GMT');
	xhr.send(null);
}

function setHiddenParameterForAddUserId(event){

	var checkbox = event.currentTarget;
	var parentNode = event.currentTarget.parentNode;
	var tdForUserIdElement = document.getElementById("add-users-table-td-userId-" + checkbox.name);
	
	if(checkbox.value == "on"){
        var userIdHiddenElement = document.getElementById("checkedAddUsers[" + checkbox.name + "].userId");
        parentNode.removeChild(userIdHiddenElement);
        checkbox.value = "off";
	}else{
		var userIdHiddenElement = document.createElement("input");
		userIdHiddenElement.setAttribute("type", "hidden");
		userIdHiddenElement.setAttribute("id", "checkedAddUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("name", "checkedAddUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("value", tdForUserIdElement.innerText);
		
		parentNode.appendChild(userIdHiddenElement);
        checkbox.value = "on";
	}
}

function setHiddenParameterForDeleteUserId(event){

	var checkbox = event.currentTarget;
	var parentNode = event.currentTarget.parentNode;
	var hiddenUserIdElement = document.getElementById("users[" + checkbox.name + "].userId");
	
	if(checkbox.value == "on"){
        var userIdHiddenElement = document.getElementById("checkedDeleteUsers[" + checkbox.name + "].userId");
        parentNode.removeChild(userIdHiddenElement);
        checkbox.value = "off";
	}else{
		var userIdHiddenElement = document.createElement("input");
		userIdHiddenElement.setAttribute("type", "hidden");
		userIdHiddenElement.setAttribute("id", "checkedDeleteUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("name", "checkedDeleteUsers[" + checkbox.name + "].userId");
		userIdHiddenElement.setAttribute("value", hiddenUserIdElement.value);
		
		parentNode.appendChild(userIdHiddenElement);
        checkbox.value = "on";
	}
}

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
