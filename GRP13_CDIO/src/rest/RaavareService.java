package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.IRaavareDAO;
import dao.MySQLRaavareDAO;
import database.DALException;
import dto.RaavareDTO;
@Path("/raavareservice")
public class RaavareService {
IRaavareDAO raavare = new MySQLRaavareDAO();
	
	@Path("/raavare")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareDTO> ShowRaavare() {
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
		

		System.out.println(raavare);
		try {
			raavare.createRaavare(raavarer);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
