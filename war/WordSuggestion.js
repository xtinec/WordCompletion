window.onload = function () {
	var inputBox = document.getElementById("input");
	inputBox.value = "";
	inputBox.onkeyup = getSuggestions;
}

function getSuggestions() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	} else {
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		var suggestionBox = document.getElementById("suggestion");
		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			var suggestions = JSON.parse(xmlhttp.responseText);
			suggestionBox.innerHTML="";
			for (s in suggestions) {
				var word = document.createElement("li");
				suggestionBox.appendChild(word);
				word.innerText = suggestions[s];
			}
		}
	}
	
	var prefix = document.getElementById("input").value;
	xmlhttp.open("GET","completion?p="+prefix,true);
	xmlhttp.send();
}