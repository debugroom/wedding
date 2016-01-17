document.addEventListener('DOMContentLoaded', function(){
	
	var formElement = document.getElementById("user");
	//IE8以前ではサポート外。
	var buttonNodeList = formElement.getElementsByTagName("button");
	//パフォーマンスのために、Arrayに変換、
	var buttonArray = Array.prototype.slice.call(buttonNodeList);
	
	//for in文の方が書きやすいが、パフォーマンス問題と、キー・値の混同が生じやすいので使わない。
	for(var i=0 ; i < buttonArray.length ; i++ ){
		//各ボタンにリスナーを設定。
		buttonArray[i].addEventListener("click", function(){showDialog(formElement, 
				buttonArray[i].id, buttonArray[i].value)}, false);
	}
	
	
}, false)

function showDialog(formElement, id, value){
	alert(value);
	alert(value);
	var inputElement = formElement.getElementById(button.value);
	alert(inputElement.value);
	var form = document.createElement("div");
}