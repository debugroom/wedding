document.addEventListener('DOMContentLoaded', function(){
	var menu_button = document.getElementById("menu_button");
	menu_button.addEventListener("click", function(event){
		toggleMenu(event);
	});
}
, false)

function toggleMenu(event){
	var target = document.getElementsByClassName("flex-item-1");
	if(target.length == 0){
		target = document.getElementsByClassName("header-menu");
	}
	var targetArray = Array.prototype.slice.call(target);
	if(targetArray[0].className == "flex-item-1"){
		targetArray[0].setAttribute('class', "flex-item-1 visible");
	}else if(targetArray[0].className == "flex-item-1 visible"){
		targetArray[0].setAttribute('class', "flex-item-1");
	}else if(targetArray[0].className == "header-menu"){
		targetArray[0].setAttribute('class', "header-menu visible");
	}else if(targetArray[0].className == "header-menu visible"){
		targetArray[0].setAttribute('class', "header-menu");
	}
}