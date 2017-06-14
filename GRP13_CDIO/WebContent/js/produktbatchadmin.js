/**
 * 
 */
$(document).ready(function() {

	var allProduktbatches;
//ajax request
	
	document.getElementById("OPB").addEventListener("click",function() {
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
		$("#createproduktbatch").show();
		
		return false;
	});
	
	
	document.getElementById("addpbKomp").addEventListener("click",function() {
		$( ".pbkomponenter" ).append( "<input type=\"number\" name=\"komp[][rbId]\" placeholder=\"Raavarebatch ID\" min =\"1\" max=\"99\">" );
		return false;
	});
	
	document.getElementById("VPB").addEventListener("click",function() {
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
		$("#popupID").hide();
		$("#pbtable").show();

		$.ajax({
		url: "http://localhost:8080/GRP13_CDIO/rest2/produktbatchservice/produktbatches",
		method: "GET",
		
		//success function
		success: function(data){
			allProduktbatches = data;
			console.log(data);
			//Parse JSON from ajax request to html table
			//ini vars for table row
			var tr;
			
			//clearing old table rows and table heads
			$("#produktbatchtable tr").remove();
			$("#produktbatchtable th").remove();
		
			//Append table row with descriptions
			$('<tr>').append(
					$('<th>').text("Productbatch ID"),
					$('<th>').text("Status"),
					$('<th>').text("Recept ID"),
					$('<th>').text("Raavare ID"),
					$('<th>').text("Netto"),
					$('<th>').text("Tara"),
					$('<th>').text("Operatoer ID")
			).appendTo("#produktbatchtable");
			
			
			//Loop through productbatches and append them to the table in html
			$.each(allProduktbatches, function(i, item) {
				var netto = "<table > ";
				$.each(item.komp, function(j, item2){
					netto += "<tr><td>";
					netto += item2.netto;
					netto += "</td></tr>";
				})
				netto += " </table>";
				var oprId = "<table > ";
				$.each(item.komp, function(j, item2){
					oprId += "<tr><td>";
					oprId += item2.oprId;
					oprId += "</td></tr>";
				})
				oprId += " </table>";
				var rbId = "<table > ";
				$.each(item.komp, function(j, item2){
					rbId += "<tr><td>";
					rbId += item2.rbId;
					rbId += "</td></tr>";
				})
				rbId += " </table>";
				var tara = "<table > ";
				$.each(item.komp, function(j, item2){
					tara += "<tr><td>";
					tara += item2.tara;
					tara += "</td></tr>";
				})
				tara += " </table>";
				
				$('<tr>').append(
						$('<td>').text(item.pbId),
						$('<td>').text(item.status),
						$('<td>').text(item.receptId),
						$('<td>').html(rbId),
						$('<td>').html(netto),
						$('<td>').html(tara),
						$('<td>').html(oprId)
//						$('<td>').append(
//								
//								$('<td>').text(item.receptId),
//								
//								$.each(item.komp, function(j, item2){
//									$('<td>').text(item2.netto).appendTo('#produktbatchtable');
//								})
//								)
//						$('<td>').append(
//								$.each(item, function(j, item2){
//									$('<tr>').text(item2.komp[j].oprId)
//								})
//						)
								
				).appendTo('#produktbatchtable');
		});
			
		},
		
		//error function
		error: function(error){
			alert("Error, sorry! :(");
		},

	});
		return false;
	});
	
	
	$("#Createproduktbatch").submit( function() {               

		event.preventDefault();


		var data = $('#Createproduktbatch').serializeObject();

		console.log(data);


		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/produktbatchservice/create/produktbatch",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("Createproduktbatch").reset();
				console.log("CPBForm has been cleared")

				//Goes back to menu
				$('#usradmin').show();
				$('#pbtable').hide();

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