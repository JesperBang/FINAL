package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.IRaavareBatchDAO;
import dao.MySQLRaavareBatchDAO;
import database.DALException;
import dto.RaavareBatchDTO;
@Path("/raavarebatchservice")
public class RaavareBatchService {
IRaavareBatchDAO raavarebatch = new MySQLRaavareBatchDAO();
	
	@Path("/raavarebatch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareBatchDTO> ShowRaavareBatch() {
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
		System.out.println("Hej");

		System.out.println(raavareBatches);
		try {
			raavarebatch.createRaavareBatch(raavareBatches);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
