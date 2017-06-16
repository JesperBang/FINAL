/**
 * 
 */
$(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
		jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
	
	//variables
	var allRaavare;
	
	//Get current logged in users rights fom their JWT
	function getRole(){
		var rights = $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1])).UserDTO.roles;
		return rights;
	}
	
	
//ajax request
	document.getElementById("OpretRaavareSM").addEventListener("click",function() {


		var rights = getRole();
		
		if(rights.includes('Farmaceut')){
			$("#table").hide();
			$("#createuser").hide();
			$("#updateuser").hide();
			$("#deactivateuser").hide();
			$("#updateraavare").hide();
			$("#rtable").hide();
			$("#createprescript").hide();
			$("#SPtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#pbtable").hide();
			$("#popupID").hide();
			$("#createproduktbatch").hide();
			$("#createraavare").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create a Raavare!")
			rights = "";
		}
		return false;
	});
	document.getElementById("OpdaterRaavareSM").addEventListener("click",function() {

		var rights = getRole();
		
		if(rights.includes('Farmaceut')){
			$("#table").hide();
			$("#createuser").hide();
			$("#updateuser").hide();
			$("#deactivateuser").hide();
			$("#createraavare").hide();
			$("#rtable").hide();
			$("#createprescript").hide();
			$("#SPtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#pbtable").hide();
			$("#popupID").hide();
			$("#createproduktbatch").hide();
			$("#updateraavare").show();
			rights = "";
		}else{
			alert("You do not meet the required role to update raavare!")
			rights = "";
		}

		return false;
		
	});

	//Create Raavare Submit Button
	$("#CreateRaavareForm").submit( function() {               

		event.preventDefault();
		
		
		var data = $('#CreateRaavareForm').serializeObject();

		console.log(data);
		$.ajax({
			url: "rest2/raavareservice/create/raavare",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("CreateRaavareForm").reset();
				console.log("CRForm has been cleared")
				
				document.getElementById('createRaavareSuccess').style.display = 'block';
					setTimeout(function() {
						$('#createRaavareSuccess').fadeOut('slow').empty()}, 5000)
						
				//Goes back to menu
				$('#usradmin').show();
				$('#createraavare').hide();

			},
			error: function(resp){
				localStorage.clear();
				
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
				$("#createproduktbatch").hide();
				$("#deactivateuser").hide();
				$("#updateuser").hide();
				$("#usradmin").hide();
				$("#login").show();
				
				alert("Error, timed out or invalid security token");
				console.log('This is the ERROR method: '+resp);
			}
		});
	   
		return false;

});
	
	document.getElementById("VisRaavareSM").addEventListener("click",function() {


		var rights = getRole();
		
		if(rights.includes('Farmaceut')){
			$("#table").hide();
			$("#createuser").hide();
			$("#updateuser").hide();
			$("#deactivateuser").hide();
			$("#updateraavare").hide();
			$("#createraavare").hide();
			$("#createprescript").hide();
			$("#SPtable").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#pbtable").hide();
			$("#popupID").hide();
			$("#createproduktbatch").hide();
			$("#rtable").show();
			rights = "";
		}else{
			alert("You do not meet the required role to view raavare!")
			rights = "";
		}
		

		$.ajax({
		url: "rest2/raavareservice/raavare",
		method: "GET",
		
		//success function
		success: function(data){
			allRaavare = data;
			console.log(data);
			//Parse JSON from ajax request to html table
			//ini vars for table row
			var tr;
			
			//clearing old table rows and table heads
			$("#raavaretable tr").remove();
			$("#raavaretable th").remove();
		
			//Append table row with descriptions
			$('<tr>').append(
					$('<th>').text("Raavare ID"),
					$('<th>').text("Raavare navn"),
					$('<th>').text("Leverandoer")
			).appendTo("#raavaretable");
			
			//Loop through raavarer and append them to the table in html
			$.each(allRaavare, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.raavareId),
						$('<td>').text(item.raavareNavn),
						$('<td>').text(item.leverandoer)
				).appendTo('#raavaretable');
		});
			
		},
		
		//error function
		error: function(error){
			localStorage.clear();
			
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
			$("#createproduktbatch").hide();
			$("#deactivateuser").hide();
			$("#updateuser").hide();
			$("#usradmin").hide();
			$("#login").show();
			
			alert("Error, timed out or invalid security token");
			console.log('This is the ERROR method: '+resp);
		},
	});
		return false;
	});
});