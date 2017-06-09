/**
 *Author: Thomas Kristian Lorentzen 
 */

$(document).ready(function() {
	
	var allRBs;
	//Load raavarebatch Showing by request
	document.getElementbyID("RBO").addEventListener("click",function(){
		alert("somthing");
		$("#CreateRaavarebatch").show();
		return false;
	
	});
	//load raavarebatch showing by request
	document.getElementbyID("RBV").addEventListener("click",function(){
		$("#ShowRaavareBatch").show();
		return false;
	});
	
	// ajax request
	$.ajax({
		url: "http://localhost:8080/GRP13_CDIO/rest2/raavarebatchservice",
		method: "GET",
		
		//success function
		success: function(data){
			allRBs = data;
			console.log(data);
			//Parse JSON from ajax request to html table
			//ini vars for table row
			var tr;
			$("#raavarebatchtable tr").remove();
			$("#raavarebatchtable th").remove();
		
			//Append table row with descriptions
			$('<tr>').append(
					$('<th>').text("rbID"),
					$('<th>').text("raavareID"),
					$('<th>').text("maengde")
			).appendTo("#raavarebatchtable");
			
			//Loop through raavare and append them to the table in html
			$.each(allRBs, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.rbID),
						$('<td>').text(item.raavareID),
						$('<td>').text(item.maengde)
				).appendTo('#raavarebatchtable');
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
				url: "http://localhost:8080/GRP13_CDIO/rest2/raavarebatchservice/raavarebatch",
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

