/**
 * 
 */

$(document).ready(function() {


	var allPrescriptions;

	// Load prescriptions on Vis Recepter page
	document.getElementById("Receptadmin").addEventListener("click",function() {

		//visuals
		$("#table").show();


		//ajax request
		$.ajax({
			url: "http://localhost:8080/GRP13_CDIO/rest2/userservice/recept",
			method: "GET",

			//success function
			success: function(data){
				allPrescriptions = data;
				console.log(data);
				//Parse JSON from ajax request to html table
				//ini vars for table row
				var tr;

			}
		});

	});
	
});