package database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO implements UserDAO {
	private Connector connector = new Connector();
	@Override
	public UserDTO getUser(int UserId) throws DALException {
		ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from showUser where UserID = ?;");
			stmt.setInt(1, UserId);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
	    try {
	    	if (!rs.first()) throw new DALException("User " + UserId + " findes ikke");
	    	UserDTO user = new UserDTO ();
	    	user.setUserId(rs.getInt("UserID"));
			user.setFirstname(rs.getString("Firstname"));
			user.setLastname(rs.getString("Lastname"));
			user.setCPR(rs.getString("CPR"));
			user.setPassword(rs.getString("Password"));
			user.addRole(rs.getString("Rolename"));
	    	List<String> roles = new ArrayList<>();
	    	for (int i = 1; i < 5; i++) {
				rs.absolute(i);
				roles.add(rs.getString("Rolename"));
				if (rs.isLast()){
					break;
				}
			}
	    	user.setRoles(roles);
	    	return user;
	    }
	    catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	}

	@Override
	public List<UserDTO> getUserList() throws DALException {
		List<UserDTO> list = new ArrayList<UserDTO>();
		ResultSet rs;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(1)
			rs = connector.doQuery("select * from showUser2;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			UserDTO user = new UserDTO();
			int last = 0;
			while (rs.next()) 
			{
				
				if (last == rs.getInt("UserID")){
					user.addRole(rs.getString("Rolename"));
				} else {
					if (last != 0){
						list.add(user);
						user = new UserDTO();
					}
					user.setUserId(rs.getInt("UserID"));
					user.setFirstname(rs.getString("Firstname"));
					user.setLastname(rs.getString("Lastname"));
					user.setCPR(rs.getString("CPR"));
					user.setPassword(rs.getString("Password"));
					user.addRole(rs.getString("Rolename"));
				}
				last = rs.getInt("UserID");
			}
			list.add(user);
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createOperatoer(UserDTO user) throws DALException {

		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_user(?,?,?,?,?);");
			stmt.setInt(1, user.getUserId());
			stmt.setString(2, user.getFirstname());
			stmt.setString(3, user.getLastname());
			stmt.setString(4, user.getCPR());
			stmt.setString(5, user.getPassword());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		for (int i = 0; i < user.getRoles().size(); i++) {
			try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(3)
				PreparedStatement stmt = connector.getConnection().prepareStatement("call add_userroles(?,?);");
				List<String> roles = user.getRoles();
				switch(roles.get(i)){
				case "Admin":
					stmt.setInt(1, 1);
					break;
				case "Pharmacist":
					stmt.setInt(1, 2);
					break;
				case "Foreman":
					stmt.setInt(1, 3);
					break;
				case "Operator":
					stmt.setInt(1, 4);
					break;
				}
				
				stmt.setInt(2, user.getUserId());
				stmt.executeQuery();
			} catch (Exception e) {
				throw new DALException(e.getMessage());
			}
		}

	}

}
