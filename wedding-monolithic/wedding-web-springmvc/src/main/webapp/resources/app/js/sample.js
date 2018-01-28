document.addEventListener('DOMContentLoaded', function(){
/*
	console.log("(ΦωΦ)ふぉふぉふぉ");
 */
	var my = document.getElementById('my');
/*
	var element1;
	element1 = my.parentNode;
	console.log(element1.id);
	var element2;
	element2 = my.firstChild;
	console.log(element2.id);
	var element3;
	element3 = element2.nextSibling;
	console.log(element3.id);
	var element4;
	element4 = element3.firstChild;
	console.log(element4.id);
	var element5;
	element5 = element4.nextSibling;
	console.log(element5.id);
	var elementChildren;
	elementChildren = my.childNodes;
	console.log("elementChildren[0] : " + elementChildren[0].id);
	console.log("elementChildren[1] : " + elementChildren[1].id);
	console.log("elementChildren[2] : " + elementChildren[2].id);
	console.log("elementChildren[3] : " + elementChildren[3].id);
	var element6;
	element6 = my.firstElementChild;
	console.log("element6 : " + element6.id);
	var element7;
	element7 = element6.nextElementSibling;
	console.log("element7 : " + element7.id);

	var result = document.evaluate(
			'//div[@id="main"]/p[contains(@class, "content")][3]/a[starts-with(@href, "http://localhost:8080/")]',
			document,
			null,
			XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,
			null
	);
	
	console.log(result.snapshotLength);
	var element8 = result.snapshotItem(0);
	console.log(element8.innerHTML);
	
	var element9 = document.createElement('div');
	var text1 = document.createTextNode('This is new div element.');
	document.body.appendChild(element9);
	element9.appendChild(text1);
	var comment1 = document.createComment('This is comment');
	document.body.insertBefore(commnet1, element9);

	
	var newNode = document.createElement('div');
	var oldNode = document.getElementById('foo');
	var parentNode = oldNode.parentNode;
	newNode.appendChild(document.createTextNode('(ΦωΦ)ふぉふぉふぉ'));
	parentNode.replaceChild(newNode, oldNode);
	
	var element11 = document.getElementById('foo');
	element11.innerHTML = '<div>(ΦωΦ)ふぉふぉふぉ11</div>';

	var element12 = document.getElementById('foo');
	element12.textContent = '<div>(ΦωΦ)ふぉふぉふぉ12</div>';
 */
	var fragment = document.createDocumentFragment();
	for(var i = 0; i < 10 ; i++){
		var child = document.createElement('div');
		child.appendChild(document.createTextNode('(ΦωΦ)ふぉふぉふぉ12 - ' + i));
		fragment.appendChild(child);
	}
	document.getElementById('foo').appendChild(fragment);

}, false)