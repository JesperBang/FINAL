/**
 * 
 */
$(document).ready(function() {

	var allRaavare;
//ajax request
	document.getElementById("OpretRaavareSM").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#createprescript").hide();
		$("#receptable").hide();
		$("#updateraavare").hide();
		$("#pbtable").hide();
		$("#rtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#popupID").hide();
		$("#createraavare").show();
		return false;
	});
	document.getElementById("OpdaterRaavareSM").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#createprescript").hide();
		$("#receptable").hide();
		$("#createraavare").hide();
		$("#pbtable").hide();
		$("#rtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#popupID").hide();
		$("#updateraavare").show();
		return false;
		
	});

	//Create User Submit Button
	$("#CreateRaavareForm").submit( function() {               

		event.preventDefault();
		
		
		var data = $('#CreateRaavareForm').serializeObject();

		console.log(data);
		debugger;
		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/raavareservice/create/raavare",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("CreateRaavareForm").reset();
				console.log("CRForm has been cleared")
				
				//Goes back to menu
				$('#usradmin').show();
				$('#createraavare').hide();

			},
			error: function(resp){
				console.log(data);
				console.log('This is the ERROR method')
				console.log(resp)
			}
		});
	   
		return false;

});
	
	document.getElementById("VisRaavareSM").addEventListener("click",function() {
		$("#table").hide();
		$("#createuser").hide();
		$("#updateuser").hide();
		$("#createprescript").hide();
		$("#receptable").hide();
		$("#updateraavare").hide();
		$("#createraavare").hide();
		$("#pbtable").hide();
		$("#createRB").hide();
		$("#RBtable").hide();
		$("#popupID").hide();
		$("#rtable").show();
		$("#SPtable").hide();
		$.ajax({
		url: "http://localhost:8080/GRP13_CDIO/rest2/raavareservice/raavare",
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
			alert("Error, sorry! :(");
		},

	});
		return false;
	});
});