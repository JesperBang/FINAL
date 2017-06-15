package rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.IProduktBatchDAO;
import dao.MySQLProduktBatchDAO;
import database.DALException;
import dto.ProduktBatchDTO;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;
@Path("/produktbatchservice")
public class ProduktBatchService {
	@Context HttpServletRequest request;
	
	IProduktBatchDAO pb = new MySQLProduktBatchDAO();
	
	@Path("/produktbatches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProduktBatchDTO> ShowProduktbatches() {
		
		//VAlidate token and role requirement
				String header = request.getHeader("Authorization");
				System.out.println("header: " +header);

				try {
					if(header != null){
						JWTHandler.validateToken(header.split(" ")[1]);
						
						if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut") || 
								JWTHandler.validateToken(header.split(" ")[1]).toString().contains("Varkforer")){
							System.out.println("Farma/Varkforer confirmed");
						}else{
							throw new WebApplicationException(401);
						}
					}else{
						throw new WebApplicationException(401);
					}
				} catch (AuthException e1) {
					e1.printStackTrace();
					throw new WebApplicationException(403);
				}
		
		List<ProduktBatchDTO> allProduktbatches = null;
		try {
			allProduktbatches = pb.getProduktBatchList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allProduktbatches;
	}

	@Path("/produktbatch/{id}")
	@GET
	@Produces("application/json")
	public ProduktBatchDTO findProduktBatchOnId(@PathParam("id") Integer id) {
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut") || 
						JWTHandler.validateToken(header.split(" ")[1]).toString().contains("Varkforer")){
					System.out.println("Farma/Varkforer confirmed");
				}else{
					throw new WebApplicationException(401);
				}
			}else{
				throw new WebApplicationException(401);
			}
		} catch (AuthException e1) {
			e1.printStackTrace();
			throw new WebApplicationException(403);
		}
		
		ProduktBatchDTO produktbatch = null;
		try {
			produktbatch = pb.getProduktBatch(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return produktbatch;
	}
	
	@Path("/create/produktbatch")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean createProduktBatch(ProduktBatchDTO produktbatch) {
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut") || 
						JWTHandler.validateToken(header.split(" ")[1]).toString().contains("Varkforer")){
					System.out.println("Farma/Varkforer confirmed");
				}else{
					throw new WebApplicationException(401);
				}
			}else{
				throw new WebApplicationException(401);
			}
		} catch (AuthException e1) {
			e1.printStackTrace();
			throw new WebApplicationException(403);
		}

		System.out.println(produktbatch);
		try {
			pb.createProduktBatch(produktbatch);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
