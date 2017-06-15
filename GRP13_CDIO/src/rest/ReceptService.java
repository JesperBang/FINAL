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

import database.DALException;
import dao.IReceptDAO;
import dao.MySQLReceptDAO;
import dto.ReceptDTO;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;
@Path("/receptservice")
public class ReceptService {
	@Context HttpServletRequest request;
	
	IReceptDAO recepts = new MySQLReceptDAO();
	
	@Path("/recept")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptDTO> ShowRecepts() {
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut")){
					System.out.println("Farma confirmed");
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
		
		List<ReceptDTO> allRecept = null;
		try {
			allRecept = recepts.getReceptList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allRecept;
	}

	@Path("/recept/{id}")
	@GET
	@Produces("application/json")
	public ReceptDTO findReceptOnId(@PathParam("id") Integer id) {
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut") || 
						(JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Varkforer")){
					System.out.println("Farma confirmed");
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
		
		ReceptDTO recept = null;
		try {
			recept = recepts.getRecept(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recept;
	}
	
	@Path("/create/recept")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean createRecepts(ReceptDTO recept) {
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Farmaceut")){
					System.out.println("Farma confirmed");
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

		System.out.println(recept);
		try {
			recepts.createRecept(recept);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}


}
