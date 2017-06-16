$(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
	
	//Ini login form with a valid l ogin
	$("#uname").val("Admin");
	$("#pasid").val("root");
	
	//Check jwt
	validjwt();
	
	function validjwt(){
	
		$.ajax({
			url: "rest2/userservice/validate/jwt",
			contentType: "application/json",
			method: 'GET',
			success: function(resp) {
				
				console.log(resp);
				if(resp == "true"){
					alert("True");
					$("#login").hide();
					$("#usradmin").show();
					console.log("user allready logged in");
					
					var name = $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1]));
					
					console.log(name);
					console.log(name.UserDTO.firstname);
					
					document.getElementById("logoutmenu").innerHTML = "Logout - "+name.UserDTO.firstname;
				}else{
					alert("Token invalid, please login again.")
					$("#login").show();
				}
				
	
			},
			error: function(resp) {
				//Error handling...
				console.log("error: "+resp);
			}
			});
	}
	
	// On login load useradmin page
	$("#loginform").submit(function() {
		
		event.preventDefault();
		
		var formData = $("loginform").serializeObject();
		//var username = formData['uname'];
		
		var idu  = document.getElementById("uname").value;
		var pas  = document.getElementById("pasid").value;
		
		if(idu == "Admin" || idu == "admin"){
			var idu = 1;
		}
		
		$.ajax({
			
			url: "rest2/userservice/validate/" + idu +"/"+ pas,
			contentType: "application/json",
			method: 'GET',
			success: function(resp) {
				console.log(resp);
				
				if (resp == null) {
					alert("Wrong Credentials!");
				}
				else {

					localStorage.setItem("user", resp); //session Storage
					$("#login").hide();
					$("#usradmin").show();
					
					console.log(resp);
					
					var name = $.parseJSON(window.atob(resp.split(".")[1]));
					
					console.log(name);
					console.log(name.UserDTO.firstname);
					
					document.getElementById("logoutmenu").innerHTML = "Logout - "+name.UserDTO.firstname;
					
					
				}
			},
			error: function(resp) {
				//Error handling...
				console.log(idu + " " + pas);
				console.log(resp);
			}
			
		});
		
		
		
		return false;

	});


});