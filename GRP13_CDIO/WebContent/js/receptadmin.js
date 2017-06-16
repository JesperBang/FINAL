/**
 * 
 */

$(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});

	var allPrescriptions;
	
	//Get current logged in users rights fom their JWT
	function getRole(){
		var rights = $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1])).UserDTO.roles;
		return rights;
	}
	
	document.getElementById("OpretReceptSM").addEventListener("click",function() {


		var rights = getRole();

		if(rights.includes('Farmaceut')){
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
			$("#createproduktbatch").hide();
			$("#createprescript").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create recept!")
			rights = "";
		}

		return false;
	});
	
	document.getElementById("addKomp").addEventListener("click",function() {
		$( ".komponenter" ).append( "<input type=\"number\" name=\"komp[][raavareId]\" placeholder=\"Raavare ID\" min =\"1\" max=\"99\">"
    			+" <input type=\"number\" name=\"komp[][nomNetto]\" placeholder=\"Maengde\" min =\"1\" max=\"99\">"
    			+" <input type=\"number\" name=\"komp[][tolerance]\" placeholder=\"Tolerance (0,1-10,0%)\" maxlength=\"50\">" );
		return false;
	});

	// Load prescriptions on Vis Recepter page
	document.getElementById("VisReceptSM").addEventListener("click",function() {


		var rights = getRole();

		if(rights.includes('Farmaceut')){
			//visuals
			$("#table").hide();
			$("#createuser").hide();
			$("#updateuser").hide();
			$("#deactivateuser").hide();
			$("#updateraavare").hide();
			$("#createraavare").hide();
			$("#rtable").hide();
			$("#createprescript").hide();
			$("#createRB").hide();
			$("#RBtable").hide();
			$("#pbtable").hide();
			$("#popupID").hide();
			$("#createproduktbatch").hide();
			$("#SPtable").show();
			rights = "";
		}else{
			alert("You do not meet the required role to view recepts!")
			rights = "";
		}

		//ajax request
		$.ajax({
			url: "rest2/receptservice/recept",
			method: "GET",

			//success function
			success: function(data){
				allPrescriptions = data;
				console.log(data);
				//Parse JSON from ajax request to html table
				//ini vars for table row
				var tr;

				//clearing old table rows and table heads
				$("#pretable tr").remove();
				$("#pretable th").remove();

				//Append table row with descriptions
				$('<tr>').append(
						$('<th>').text("Recept ID"),
						$('<th>').text("Navn"),
						$('<th>').text("Raavare (type)"),
						$('<th>').text("Maengde"),
						$('<th>').text("Tolerance")
						
				).appendTo("#pretable");

				//Loop through prescriptions and append them to the table in html
				$.each(allPrescriptions, function(i, item) {
					
					var raavareId = "<table > ";
					$.each(item.komp, function(j, item2){
						raavareId += "<tr><td>";
						raavareId += item2.raavareId;
						raavareId += "</td></tr>";
					})
					raavareId += " </table>";
					
					var nomNetto = "<table > ";
					$.each(item.komp, function(j, item2){
						nomNetto += "<tr><td>";
						nomNetto += item2.nomNetto;
						nomNetto += "</td></tr>";
					})
					nomNetto += " </table>";
					
					var tolerance = "<table > ";
					$.each(item.komp, function(j, item2){
						tolerance += "<tr><td>";
						tolerance += item2.tolerance;
						tolerance += "</td></tr>";
					})
					
					
					$('<tr>').append(
							$('<td>').text(item.receptId),
							$('<td>').text(item.receptNavn),
							$('<td>').html(raavareId),
							$('<td>').html(nomNetto),
							$('<td>').html(tolerance)
							
					).appendTo('#pretable');
				});

			},
			
			//error function
			error: function(error){
				localStorage.clear();
				
				$("#login").show();
				$("#table").hide();
				$("#usradmin").hide();
				
				alert("Error, timed out or invalid security token");
			},
		});
	});

	//Create Prescription Button
	$("#CreatePrescription").submit( function() {               

		event.preventDefault();

		var data = $('#CreatePrescription').serializeObject();

		console.log(data);

		$.ajax({
			url: "rest2/receptservice/create/recept",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("CreatePrescription").reset();
				console.log("CPForm has been cleared")

				document.getElementById('createPSuccess').style.display = 'block';
					setTimeout(function() {
						$('#createPSuccess').fadeOut('slow').empty()}, 5000)
						
				//Goes back to menu
				$('#usradmin').show();
				$("#createprescript").hide();

			},
			error: function(resp){
				localStorage.clear();
				
				$("#login").show();
				$("#table").hide();
				$("#usradmin").hide();
				
				alert("Error, timed out or invalid security token");
				console.log('This is the ERROR method: '+rest);
			}
		});
		return false;
	});
});