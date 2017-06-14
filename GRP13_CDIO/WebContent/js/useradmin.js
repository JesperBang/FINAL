$(document).ready(function() {
	
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
	// Variable ini
	var allUsers;

	//Get current logged in users rights fom their JWT
	function getRole(){
		var rights = $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1])).UserDTO.roles;
		return rights;
	}
	
	//Load User table on request
	document.getElementById("createusermenu").addEventListener("click",function() {

		var rights = getRole();

		
		if(rights.includes(' Administrator')){
			$("#table").hide();
			$("#updateuser").hide();
			$("#createprescript").hide();
			$("#receptable").hide();
			$("#updateraavare").hide();
			$("#createraavare").hide();
			$("#pbtable").hide();
			$("#rtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#popupID").hide();
			$("#createuser").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create a user!")
			rights = "";
		}
		return false;
	});
	document.getElementById("updateusermenu").addEventListener("click",function() {

		var rights = getRole();
		
		if(rights.includes(' Administrator')){
			$("#table").hide();
			$("#createuser").hide();
			$("#createuser").hide();
			$("#createprescript").hide();
			$("#receptable").hide();
			$("#updateraavare").hide();
			$("#createraavare").hide();
			$("#rtable").hide();
			$("#pbtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#popupID").hide();
			$("#updateuser").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create a user!")
			rights = "";
		}

		
		return false;
	});
	//Load logout
	document.getElementById("logoutmenu").addEventListener("click",function() {
		$("#usradmin").hide();
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#deactivateuser").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#rtable").hide();
		$("#createprescript").hide();
		$("#SPtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#pbtable").hide();
		$("#popupID").hide();
		alert("Du er nu logget ud.");
		
		//Clearing storage
		sessionStorage.clear();
		localStorage.clear();
		var rights = "";
		
		$("#login").show();
		
		return false;
	});
	
	document.getElementById("updatestatususermenu").addEventListener("click",function() {

		var rights = getRole();
		
		if(rights.includes(' Administrator')){	
			$("#table").hide();
			$("#updateuser").hide();
			$("#createprescript").hide();
			$("#receptable").hide();
			$("#updateraavare").hide();
			$("#createraavare").hide();
			$("#pbtable").hide();
			$("#rtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#popupID").hide();
			$("#createuser").hide();
			$("#deactivateuser").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create a user!")
			rights = "";
		}
		
		return false;
	});
	

	// Load users on useradmin page
	document.getElementById("usradminmenu").addEventListener("click",function() {
		var rights = $.parseJSON(sessionStorage.getItem("user"));
		
		//visuals
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#deactivateuser").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#rtable").hide();
		$("#createprescript").hide();
		$("#SPtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#pbtable").hide();
		$("#popupID").hide();
		$("#table").show();
		
		//ajax request
		$.ajax({
		url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/users",
		method: "GET",
		
		//success function
		success: function(data){
			allUsers = data;
			console.log(data);
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
					$('<th>').text("Initials"),
					$('<th>').text("SSN"),
					$('<th>').text("Password"),
					$('<th>').text("Roles"),
					$('<th>').text("Status")
			).appendTo("#usertable");
			
			//Loop through users and append them to the table in html
			$.each(allUsers, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.opr_id),
						$('<td>').text(item.firstname),
						$('<td>').text(item.lastname),
						$('<td>').text(item.ini),
						$('<td>').text(item.cpr),
						$('<td>').text(hex_md5(item.password)+" - "+item.password),
						$('<td>').text(item.roles),
						$('<td>').text(item.aktiv)			
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
			
			
			var data = $('#CreateUserForm').serializeObject();

			console.log(data);
			$.ajax({
				url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/create/user",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("CreateUserForm").reset();
					console.log("CUForm has been cleared")
					 	
					document.getElementById('createUsrSuccess').style.display = 'block';
					setTimeout(function() {
						$('#createUsrSuccess').fadeOut('slow').empty()}, 5000)
					
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
		$("#DeactivateUserForm").submit( function() {               
			   
			event.preventDefault();
			
			
			var data = $('#DeactivateUserForm').serializeObject();

			console.log(data);
			$.ajax({
				url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/deactivate/user",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("DeactivateUserForm").reset();
					console.log("DUForm has been cleared")
					
					document.getElementById('statusUsrSuccess').style.display = 'block';
					setTimeout(function() {
						$('#statusUsrSuccess').fadeOut('slow').empty()}, 5000)
					//Goes back to menu
					$('#usradmin').show();
					$('#deactivateuser').hide();

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