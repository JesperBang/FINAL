/**
 *Author: Thomas Kristian Lorentzen 
 */

$(document).ready(function() {
	
	var allUsers;
	
	//Load raavarebatch Showing by request
	document.getElementbyID("RBO").addEventListener("click",function(){
		$("#CreateRaavarebatch").show();
		return false;
	
	});
	//load raavarebatch showing by request
	document.getElementbyID("RBV").addEventListener("click",function(){
		$("#ShowRaavarebatch").show();
		return false;
	});
	
	
});
