$(document).ready(function() {
	
//	var data = $("#loginForm").serializeObject();
//	var un = data['userName'];
//	sessionStorage.setItem("username", un);
	
	// Variable ini
	var allUsers;
	
	// On login load useradmin page
	$("#loginform").submit(function() {
		
		event.preventDefault();
		
		var formData = $("loginform").serializeObject();
		var username = formData['uname'];
		
		var idu  = document.getElementById("uname").value;
		var pas  = document.getElementById("pasid").value;
		
		$.ajax({
			
			url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/validate/" + idu +"/"+ pas,
			contentType: "application/json",
			method: 'GET',
			success: function(resp) {

				console.log(resp);
				alert("success");
				if (resp == null) {
					alert("Wrong Credentials!");
				}
				else {
					alert("Else");
					
					sessionStorage.setItem("user", resp); //session Storage
					$("#login").hide();
					$("#usradmin").show();
					
				}
			},
			error: function(resp) {
				//Error handling...
				console.log(idu + " " + pas);
				alert("Fail");
				console.log(resp);
			}
			
		});
		
		
		
		return false;

	});


});