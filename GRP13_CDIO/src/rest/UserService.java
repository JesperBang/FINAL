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

import dao.MySQLUserDAO;
import dao.IUserDAO;
import database.DALException;
import dto.UserDTO;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;

@Path("/userservice")
public class UserService {
	@Context HttpServletRequest request;
	
	IUserDAO users = new MySQLUserDAO();
	
	@Path("/users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> ShowUsers() {
	
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Administrator")){
					System.out.println("Admin confirmed");
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
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Administrator")){
					System.out.println("Admin confirmed");
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
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Administrator")){
					System.out.println("Admin confirmed");
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
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Administrator")){
					System.out.println("Admin confirmed");
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
		
		//VAlidate token and role requirement
		String header = request.getHeader("Authorization");
		System.out.println("header: " +header);

		try {
			if(header != null){
				JWTHandler.validateToken(header.split(" ")[1]);
				
				if((JWTHandler.validateToken(header.split(" ")[1])).toString().contains("Administrator")){
					System.out.println("Admin confirmed");
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
			users.deactivateOperatoer(user);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}

	@Path("/validate/{id}/{password}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String validateUser(@PathParam("id") Integer id, @PathParam("password") String password){
		UserDTO user = null;
		try {
			user = users.getUser(id);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(user);
		
		String passdb = user.getPassword();
		String passin = password;
		System.out.println(passdb+" "+passin);
		
		System.out.println("aktiv:" +user.getAktiv());
		if(passdb.equals(passin) && user.getAktiv() == 1){
			//Protects user password
			user.setPassword("");
			return JWTHandler.generateJwtToken(user);
		}else{
			System.out.println("No User");
			return null;
		}
	}
	
	//Check token
	@Path("/validate/jwt")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public boolean validateJWT(){
		//VAlidate token and role requirement
				String header = request.getHeader("Authorization");
				System.out.println("header: " +header);

				try {
					if(header != null){
						JWTHandler.validateToken(header.split(" ")[1]);
						return true;
					}else{
						return false;
					}
				} catch (AuthException e1) {
					e1.printStackTrace();
					return false;
				}
				
	}
	
}