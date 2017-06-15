$(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
	
	//Ini login form with a valid l ogin
	$("#uname").val("2");
	$("#pasid").val("atoJ21v");
	
	// On login load useradmin page
	$("#loginform").submit(function() {
		
		event.preventDefault();
		
		var formData = $("loginform").serializeObject();
		var username = formData['uname'];
		
		var idu  = document.getElementById("uname").value;
		var pas  = document.getElementById("pasid").value;
		
		if(idu == "Admin"){
			var idu = 2;
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