package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.IProduktBatchDAO;
import dao.MySQLProduktBatchDAO;
import database.DALException;
import dto.ProduktBatchDTO;
@Path("/produktbatchservice")
public class ProduktBatchService {
IProduktBatchDAO pb = new MySQLProduktBatchDAO();
	
	@Path("/produktbatches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProduktBatchDTO> ShowProduktbatches() {
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
		System.out.println("Hej");

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
