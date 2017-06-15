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

import dao.IRaavareDAO;
import dao.MySQLRaavareDAO;
import database.DALException;
import dto.RaavareDTO;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;
@Path("/raavareservice")
public class RaavareService {
	@Context HttpServletRequest request;
	
	IRaavareDAO raavare = new MySQLRaavareDAO();
	
	@Path("/raavare")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareDTO> ShowRaavare() {
		
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
		
		List<RaavareDTO> allRaavare = null;
		try {
			allRaavare = raavare.getRaavareList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allRaavare;
	}

	@Path("/raavare/{id}")
	@GET
	@Produces("application/json")
	public RaavareDTO findRaavareOnId(@PathParam("id") Integer id) {
		
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
		
		RaavareDTO raavarer = null;
		try {
			raavarer = raavare.getRaavare(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return raavarer;
	}
	
	@Path("/create/raavare")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean createRaavarer(RaavareDTO raavarer) {
		
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
		
		System.out.println(raavare);
		try {
			raavare.createRaavare(raavarer);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	@Path("/update/raavare")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean updateRaavarer(RaavareDTO raavarer) {
		

		System.out.println(raavare);
		try {
			raavare.updateRaavare(raavarer);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
