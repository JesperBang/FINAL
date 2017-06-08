/**
 * Author: Thomas Kristian Lorentzen
 */

$(document).ready(function() {
	
	var allUsers;
	
	//Load raavare creation by request
	document.getElementbyID("ORV").addEventListener("click",function(){
		alert("SSSS");
		$("#createraavare").show();
		return false;
	
	});
	//load raavare modification by request
	document.getElementbyID("RRV").addEventListener("click",function(){
		$("#table").hide();
		$("#modifyraavare").show();
		return false;
	});
	
	//load raavare modification by request
	document.getElementbyID("SRV").addEventListener("click",function(){
		$("#table").hide();
		$("#ShowRaavare").show();
		return false;
	});

	
	
});
