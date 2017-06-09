/**
 * Author: Thomas Kristian Lorentzen
 */

$(document).ready(function() {
	
	var allRaavarer;
	
	//Load raavare creation by request
	document.getElementbyID("ORV").addEventListener("click",function(){
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
		
	// ajax request
	$.ajax({
		url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/raavare",
		method: "POST",
		
		//success function
		success: function(data){
			allRaavarer = data;
			console.log(data);
			//Parse JSON from ajax request to html table
			//ini vars for table row
			var tr;
			$("#raavaretable tr").remove();
			$("#raavaretable th").remove();
		
			//Append table row with descriptions
			$('<tr>').append(
					$('<th>').text("raavareID"),
					$('<th>').text("raavareName"),
					$('<th>').text("leverandoer")
			).appendTo("#raavaretable");
			
			//Loop through raavare and append them to the table in html
			$.each(allUsers, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.raavareID),
						$('<td>').text(item.raavareName),
						$('<td>').text(item.leverandoer)
				).appendTo('#raavaretable');
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
			debugger;
			$.ajax({
				url: "http://localhost:8080/GRP13_CDIO/rest2/raavareservice",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("CreateUserForm").reset();

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

	
	

