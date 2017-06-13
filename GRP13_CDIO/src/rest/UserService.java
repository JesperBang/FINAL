package rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.MySQLUserDAO;
import database.DALException;
import database.UserDAO;
import database.UserDTO;

@Path("/userservice")
public class UserService {
	
	UserDAO users = new MySQLUserDAO();
	
	@Path("/users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> ShowUsers() {
//		String Users = "all users";
		List<UserDTO> allUsers = null;
		try {
			allUsers = users.getUserList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(allUsers.toString());
		return allUsers;
	}

	@Path("/user/{id}")
	@GET
	@Produces("application/json")
	public UserDTO findUserOnId(@PathParam("id") Integer id) {
		
		UserDTO user = null;
		try {
			user = users.getUser(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Path("/create/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public boolean createUser(UserDTO user) {
		System.out.println(user);
		try {
			users.createOperatoer(user);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	@Path("/update/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateUser(UserDTO user){
		try {
			users.updateOperatoer(user);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	@Path("/deactivate/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deactivateUser(UserDTO user){
		
		try {
			users.deactivateOperatoer(user);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}

	@Path("/validate/{id}/{password}")
	@GET
	@Produces("application/json")
	public UserDTO validateUser(@PathParam("id") Integer id, @PathParam("password") String password){
		UserDTO user = null;
		try {
			user = users.getUser(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String passdb = user.getPassword();
		String passin = password;
		System.out.println(passdb+" "+passin);
		
		
		if(passdb.equals(passin)){
			System.out.println("YAAA");
			return user;
		}else{
			System.out.println("No User");
			return null;
		}
	}
}