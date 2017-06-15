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

import dao.IRaavareBatchDAO;
import dao.MySQLRaavareBatchDAO;
import database.DALException;
import dto.RaavareBatchDTO;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;
@Path("/raavarebatchservice")
public class RaavareBatchService {
	@Context HttpServletRequest request;
	IRaavareBatchDAO raavarebatch = new MySQLRaavareBatchDAO();
	
	@Path("/raavarebatch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareBatchDTO> ShowRaavareBatch() {
		
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
		
		List<RaavareBatchDTO> allRaavareBatch = null;
		try {
			allRaavareBatch = raavarebatch.getRaavareBatchList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allRaavareBatch;
	}

	@Path("/raavarebatch/{id}")
	@GET
	@Produces("application/json")
	public RaavareBatchDTO findRaavareBatchOnId(@PathParam("id") Integer id) {
		
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
		
		RaavareBatchDTO raavareBatches = null;
		try {
			raavareBatches = raavarebatch.getRaavareBatch(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return raavareBatches;
	}
	
	@Path("/create/raavarebatch")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean createRaavareBatches(RaavareBatchDTO raavareBatches) {
		
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
		
		try {
			raavarebatch.createRaavareBatch(raavareBatches);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
