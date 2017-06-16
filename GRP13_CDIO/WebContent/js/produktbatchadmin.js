/**
 * 
 */
$(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
	
	var allProduktbatches;
	
	//Get current logged in users rights fom their JWT
	function getRole(){
		var rights = $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1])).UserDTO.roles;
		return rights;
	}

	
	document.getElementById("OPB").addEventListener("click",function() {

		var rights = getRole();
		
		if(rights.includes('Farmaceut') || rights.includes('Varkforer')){
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
			$("#SPtable").hide();
			$("#createproduktbatch").show();
			rights = "";
		}else{
			alert("You do not meet the required role to create a Produktbatch!")
			rights = "";
		}
		return false;
	});
	
	
	document.getElementById("addpbKomp").addEventListener("click",function() {
		$( ".pbkomponenter" ).append( "<input type=\"number\" name=\"komp[][rbId]\" placeholder=\"Raavarebatch ID\" min =\"1\" max=\"99\">" );
		return false;
	});
	
	document.getElementById("VPB").addEventListener("click",function() {


		var rights = getRole();
		
		if(rights.includes('Farmaceut') || rights.includes('Varkforer')){
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
			$("#createproduktbatch").hide();
			$("#pbtable").show();
			rights = "";
		}else{
			alert("You do not meet the required role to view produktbatches!")
			rights = "";
		}
			
		$.ajax({
		url: "rest2/produktbatchservice/produktbatches",
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

				).appendTo('#produktbatchtable');
		});
			
		},
		
		//error function
		error: function(error){
			localStorage.clear();
			
			$("#login").show();
			$("#table").hide();
			$("#usradmin").hide();
			
			alert("Error, timed out or invalid security token");
			console.log('This is the ERROR method: '+resp);
		},

	});
		return false;
	});
	
	
	$("#Createproduktbatch").submit( function() {               

		event.preventDefault();


		var data = $('#Createproduktbatch').serializeObject();

		console.log(data);


		$.ajax({
			url: "rest2/produktbatchservice/create/produktbatch",
			data: JSON.stringify(data),
			contentType: "application/json",
			method: 'POST',
			success: function(resp){
				console.log(data);
				console.log('This is the Success method')
				console.log(resp)
				document.getElementById("Createproduktbatch").reset();
				console.log("CPBForm has been cleared")

				document.getElementById('createPBSuccess').style.display = 'block';
					setTimeout(function() {
						$('#createPBSuccess').fadeOut('slow').empty()}, 5000)
				//Goes back to menu
				$('#usradmin').show();
				$("#createproduktbatch").hide();

			},
			error: function(resp){
				console.log(data);
				console.log('This is the ERROR method')
				console.log(resp)
			}
		});
		var recept;
		alert("start");
		$.ajax({
			url: "rest2/receptservice/recept/"+data.receptId,
			method: 'GET',
			success: function(resp){
				alert("Success");
				console.log(resp)
				recept = resp;
				Print(data, recept);
			},
			error: function(resp){
				console.log('This is the ERROR method: '+rest);
			}
		});
		
		
		return false;

	});
	
	//Printer
	function Print(elem, recept){
		alert("1");
		var currentdate = new Date(); 
		var datetime =  currentdate.getDate() + "-"
		                + (currentdate.getMonth()+1)  + "-" 
		                + currentdate.getYear() ;
		
		var PBP = "Udskrevet "+datetime;
		PBP += "<br>";
		PBP += "Produkt Batch nr.: "+elem.pbId;
		PBP += "<br>";
		PBP += "Recept nr.: "+elem.receptId;
		PBP += "<br><br>";
		
		$.each(elem.komp, function(i, item){
			PBP += "Raavare nr.: "+ recept.komp[i].raavareId;
			PBP += "<br>";
			PBP += "----------------------------------------------------------------------------------------------------------------------------";
			PBP += "<br>";
			PBP += "<table style=\"width:100%\"> <tr><td>Maengde</td><td>Tolerance</td><td>Tara</td><td>Netto (kg)</td><td>Batch</td><td>Opr.</td> </tr>"
			
			PBP += "<tr><td>"+recept.komp[i].nomNetto+"</td><td>"+recept.komp[i].tolerance +"</td><td>0</td><td>0</td><td>0</td><td>0</td></tr> </table>";
			PBP += "<br>";
		});
		
		alert("2");
	    var mywindow = window.open('', 'PRINT', 'height=400,width=600');

	    mywindow.document.write('<html><head><title>' + "Produktbatch Print"  + '</title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1>' + "Produktbatch Print"  + '</h1>');
	    mywindow.document.write(PBP);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}
});