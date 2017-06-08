package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import database.DALException;
import dao.IReceptDAO;
import dao.MySQLReceptDAO;
import dto.ReceptDTO;
@Path("/receptservice")
public class ReceptService {
IReceptDAO recepts = new MySQLReceptDAO();
	
	@Path("/recept")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptDTO> ShowRecepts() {
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
		System.out.println("Hej");

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
