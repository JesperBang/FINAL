$(document).ready(function() {

	// Variable ini
	var allUsers;
	
	//Load User table on request
	document.getElementById("createusermenu").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").show();
		return false;
	});
	
	//Load logout
	document.getElementById("logoutmenu").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		alert("Du er nu logget ud.");
		$("#login").show();
		$("#usradmin").hide();
		return false;
	});

	// Load users on useradmin page
	document.getElementById("usradminmenu").addEventListener("click",function() {
		
		//visuals
		$("#table").show();
		$("#createuser").hide();
		
		//ajax request
		$.ajax({
		url: "http://localhost:8080/webProject/rest2/service/users",
		method: "GET",
		
		//success function
		success: function(data){
			allUsers = data;
			
			//Parse JSON from ajax request to html table
			//ini vars for table row
			var tr;
			
			//clearing old table rows and table heads
			$("#usertable tr").remove();
			$("#usertable th").remove();
		
			//Append table row with descriptions
			$('<tr>').append(
					$('<th>').text("User ID"),
					$('<th>').text("First Name"),
					$('<th>').text("Last Name"),
					$('<th>').text("CPR"),
					$('<th>').text("Password"),
					$('<th>').text("Roles")
			).appendTo("#usertable");
			
			//Loop through users and append them to the table in html
			$.each(allUsers, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.userId),
						$('<td>').text(item.firstname),
						$('<td>').text(item.lastname),
						$('<td>').text(item.cpr),
						$('<td>').text(item.password),
						$('<td>').text(item.roles)
				).appendTo('#usertable');
			});
			
		},
		
		//error function
		error: function(error){
			alert("Error, sorry! :(");
		},

	});
		return false;
	});
	document.getElementById("Forfattere").addEventListener("click",function myFunction() {
	    var popup = document.getElementById("myPopup");
	    popup.classList.toggle("show");
	    $("#popupID").show();
	    
	});
	
	
		//Create User Submit Button
		$("#CreateUserForm").submit( function() {
		
			event.preventDefault();
			//validation?
			
			var data = $('#CreateUserForm').serializeObject();

			console.log(data);
			debugger;
			$.ajax({
				url: "http://localhost:8080/webProject/rest2/service/create/user",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("CreateUserForm").reset();
					console.log("CUForm has been cleared")
					
					//Goes back to menu
					$('#usradmin').show();
					$('#createuser').hide();

				},
				error: function(resp){
					console.log(data);
					console.log('This is the ERROR method')
					console.log(resp)
				}
			});

			return false;

	});
	
});