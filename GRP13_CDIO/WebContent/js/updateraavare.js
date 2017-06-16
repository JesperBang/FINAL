/**
 * 
 */
/**
 *  Yas
 */
$(document).ready(function() {	
		//Fetch Update User Submit Button
		$("#FUpdateRaavareForm").submit( function() {               
		 
			event.preventDefault();
			
			
			var idu  = document.getElementById("chooseraaid").value;
			
			$.ajax({
				url: "rest2/raavareservice/raavare/" + idu,
				contentType: "application/json",
				method: 'GET',
				success: function(resp){
					console.log(idu);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("FUpdateRaavareForm").reset();
					console.log("Chose User has been cleared")
					
					//Parsing fetched information into update form
					try{
						console.log(resp.opr_id);
						$("#updateraaid").val(resp.raavareId);
						$("#updateraan").val(resp.raavareNavn);
						$("#updateraal").val(resp.leverandoer);
					

					}catch(err){
						alert("Raavare doesn't excist! make sure to type a valid id.");
					}
				},
				
				error: function(resp){
					console.log(idu);
					console.log('This is the ERROR method')
					console.log(resp)
					
					$("#chooseid").text("No raavare with that id was found");
				}
			});
			return false;
	});
		
		
		//Send updated raavare details to db
		$("#UpdateRaavareForm").submit( function() {
			
			event.preventDefault();
			
			var data = $('#UpdateRaavareForm').serializeObject();

			console.log(data);
			$.ajax({
				url: "rest2/raavareservice/update/raavare",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("UpdateRaavareForm").reset();
					
					document.getElementById('updateRSuccess').style.display = 'block';
					setTimeout(function() {
						$('#updateRSuccess').fadeOut('slow').empty()}, 5000)
					
						//Goes back to menu
					$('#usradmin').show();
					$("#updateraavare").hide();

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