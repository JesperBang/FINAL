/**
 * 
 */
$(document).ready(function() {

	var allProduktbatches;
//ajax request
	document.getElementById("VPB").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#createprescript").hide();
		$("#receptable").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#rtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
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
					$('<th>').text("Recept ID")
			).appendTo("#produktbatchtable");
			
			//Loop through productbatches and append them to the table in html
			$.each(allProduktbatches, function(i, item) {
				$('<tr>').append(
						$('<td>').text(item.pbId),
						$('<td>').text(item.status),
						$('<td>').text(item.receptId)
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
});