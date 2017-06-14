/**
 * 
 */

$(document).ready(function() {


	var allRaavareBatches;
	
	document.getElementById("OpretRaavarebatchSM").addEventListener("click",function() {
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
		$("#createRB").show();
		$("#SPtable").hide();
		return false;
		
	});

	// Load prescriptions on Vis Raavarebatch page
	document.getElementById("VisRaavarebatchSM").addEventListener("click",function() {

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
		$("#pbtable").hide();
		$("#popupID").hide();
		$("#RBtable").show();

		//ajax request
		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/raavarebatchservice/raavarebatch",
			method: "GET",

			//success function
			success: function(data){
				allRaavareBatches = data;
				console.log(data);
				//Parse JSON from ajax request to html table
				//ini vars for table row
				var tr;

				//clearing old table rows and table heads
				$("#raavarebatchtable tr").remove();
				$("#raavarebatchtable th").remove();

				//Append table row with descriptions
				$('<tr>').append(
						$('<th>').text("Raavarebatch ID"),
						$('<th>').text("Raavare ID"),
						$('<th>').text("Maengde")
				).appendTo("#raavarebatchtable");

				//Loop through prescriptions and append them to the table in html
				$.each(allRaavareBatches, function(i, item) {
					$('<tr>').append(
							$('<td>').text(item.rbId),
							$('<td>').text(item.raavareId),
							$('<td>').text(item.maengde)
					).appendTo('#raavarebatchtable');
				});

			}
		});

	});
	
	
	//Create Raavarebatch Button
	$("#CreateRaavarebatch").submit( function() {               

		event.preventDefault();


		var data = $('#CreateRaavarebatch').serializeObject();

		console.log(data);
		
		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/raavarebatchservice/create/raavarebatch",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("CreateRaavarebatch").reset();
				console.log("RBForm has been cleared")

				document.getElementById('createRBSuccess').style.display = 'block';
					setTimeout(function() {
						$('#createRBSuccess').fadeOut('slow').empty()}, 5000)
						
				//Goes back to menu
				$('#usradmin').show();
				$('#RBtable').hide();

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
	
