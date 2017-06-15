/**
 *  Yas
 */
$(document).ready(function() {	
		//Fetch Update User Submit Button
		$("#ChooseUserToUpdate").submit( function() {               
		 
			event.preventDefault();
			
			var admin = document.getElementById("updateradmin");
			var farma = document.getElementById("updaterfarm");
			var vaerk = document.getElementById("updatervaerk");
			var labor = document.getElementById("updaterLab");
			
			var idu  = document.getElementById("chooseid").value;
			
			$.ajax({
				url: "rest2/userservice/user/" + idu,
				contentType: "application/json",
				method: 'GET',
				success: function(resp){
					console.log(idu);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("ChooseUserToUpdate").reset();
					console.log("Chose User has been cleared")
					
					//Parsing fetched information into update form
					try{
						console.log(resp.opr_id);
						$("#updateidin").val(resp.opr_id);
						$("#updatefnin").val(resp.firstname);
						$("#updatelnin").val(resp.lastname);
						$("#updateiniin").val(resp.ini);
						$("#updatecprin").val(resp.cpr);
						$("#updatepassin").val(resp.password);
					
						//role filling
						if(resp.roles.length == 1){
							//current role chosen
							labor.checked = true;
							vaerk.checked = false;
							farma.checked = false;
							admin.checked = false;
							
						}if(resp.roles.length == 2){
							//current role chosen
							labor.checked = true;
							vaerk.checked = true;
							farma.checked = false;
							admin.checked = false;
							

						}if(resp.roles.length == 3){
							//current role chosen
							labor.checked = true;
							vaerk.checked = true;
							farma.checked = true;
							admin.checked = false;

						}if(resp.roles.length == 4){
							//current role chosen
							labor.checked = true;
							vaerk.checked = true;
							farma.checked = true;
							admin.checked = true;
							
						}
					}catch(err){
						alert("User doesn't excist! make sure to type a valid id.");
					}
				},
				
				error: function(resp){
					console.log(idu);
					console.log('This is the ERROR method')
					console.log(resp)
					
					$("#chooseid").text("No user with that id was found");
				}
			});
			return false;
	});
		
		
		//Send updated user details to db
		$("#UpdateUserForm").submit( function() {
			
			event.preventDefault();
			
			var data = $('#UpdateUserForm').serializeObject();

			console.log(data);
			$.ajax({
				url: "rest2/userservice/update/user",
				data: JSON.stringify(data),
				contentType: "application/json",
				method: 'POST',
				success: function(resp){
					console.log(data);
					console.log('This is the Success method')
					console.log(resp)
					document.getElementById("UpdateUserForm").reset();
					console.log("CUForm has been cleared")
										//Goes back to menu
					$('#usradmin').show();
					$('#updateuser').hide();
					
					document.getElementById('updateUsrSuccess').style.display = 'block';
					setTimeout(function() {
						$('#updateUsrSuccess').fadeOut('slow').empty()}, 5000)


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