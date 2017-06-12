/**
 * 
 */

$(document).ready(function() {


	var allPrescriptions;
	
	document.getElementById("OpretReceptSM").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#createprescript").hide();
		$("#receptable").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#rtable").hide();
		$("#pbtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#popupID").hide();
		$("#createprescript").show();
		return false;
		
	});

	// Load prescriptions on Vis Recepter page
	document.getElementById("VisReceptSM").addEventListener("click",function() {

		//visuals

		$("#table").hide();
		$("#createuser").hide();
		$("#createuser").hide();
		$("#createprescript").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#rtable").hide();
		$("#pbtable").hide();
		$("#createprescript").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#popupID").hide();
		$("#receptable").show();

		//ajax request
		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/receptservice/recept",
			method: "GET",

			//success function
			success: function(data){
				allPrescriptions = data;
				console.log(data);
				//Parse JSON from ajax request to html table
				//ini vars for table row
				var tr;

				//clearing old table rows and table heads
				$("#recepttable tr").remove();
				$("#recepttable th").remove();

				//Append table row with descriptions
				$('<tr>').append(
						$('<th>').text("Recept ID"),
						$('<th>').text("Navn"),
						$('<th>').text("Receptkomponenter")
				).appendTo("#recepttable");

				//Loop through prescriptions and append them to the table in html
				$.each(allPrescriptions, function(i, item) {
					$('<tr>').append(
							$('<td>').text(item.receptId),
							$('<td>').text(item.receptNavn),
							$('<td>').text(item.receptId)
					).appendTo('#recepttable');
				});

			}
		});

	});
	
	

	//Create Prescription Button
	$("#CreatePrescription").submit( function() {               

		event.preventDefault();


		var data = $('#CreatePrescription').serializeObject();

		console.log(data);

		debugger;

		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/receptservice/create/recept",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("CreatePrescription").reset();
				console.log("CPForm has been cleared")

				//Goes back to menu
				$('#usradmin').show();
				$('#receptable').hide();

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
	
