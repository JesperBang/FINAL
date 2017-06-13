package dao;

import java.sql.PreparedStatement;
import passgen.Password;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import database.UserDAO;
import database.UserDTO;

public class MySQLUserDAO implements UserDAO {
	private Connector connector = new Connector();
	@Override
	public UserDTO getUser(int opr_id) throws DALException {
		ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from getoperatoer where opr_id = ?;");
			stmt.setInt(1, opr_id);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
	    try {
	    	if (!rs.first()) throw new DALException("User " + opr_id + " findes ikke");
	    	UserDTO user = new UserDTO ();
	    	user.setUserId(rs.getInt("opr_id"));
			user.setFirstname(rs.getString("opr_fornavn"));
			user.setLastname(rs.getString("opr_efternavn"));
			user.setIni(rs.getString("ini"));
			user.setCPR(rs.getString("cpr"));
			user.setPassword(rs.getString("password"));
			user.addRole(rs.getString("rollenavn"));
	    	List<String> roles = new ArrayList<>();
	    	for (int i = 1; i < 5; i++) {
				rs.absolute(i);
				roles.add(rs.getString("rollenavn"));
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
			rs = connector.doQuery("select * from getoperatoer;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			UserDTO user = new UserDTO();
			int last = 0;
			while (rs.next()) 
			{
				
				if (last == rs.getInt("opr_id")){
					user.addRole(rs.getString("rollenavn"));
				} else {
					if (last != 0){
						list.add(user);
						user = new UserDTO();
					}
					user.setUserId(rs.getInt("opr_id"));
					user.setFirstname(rs.getString("opr_fornavn"));
					user.setLastname(rs.getString("opr_efternavn"));
					user.setIni(rs.getString("ini"));
					user.setCPR(rs.getString("cpr"));
					user.setPassword(rs.getString("password"));
					user.setAktiv(rs.getInt("aktiv"));
					user.addRole(rs.getString("rollenavn"));
				}
				last = rs.getInt("opr_id");
			}
			list.add(user);
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createOperatoer(UserDTO user) throws DALException {
		Password pss = new Password();
		String pass = pss.generatePassword();
		System.out.println(pass);
		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_operatoer(?,?,?,?,?,?);");
			stmt.setInt(1, user.getUserId());
			stmt.setString(2, user.getFirstname());
			stmt.setString(3, user.getLastname());
			stmt.setString(4, user.getIni());
			stmt.setString(5, user.getCPR());
			stmt.setString(6, pass);
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		for (int i = 0; i < user.getRoles().size(); i++) {
			try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(3)
				PreparedStatement stmt = connector.getConnection().prepareStatement("call add_userroles(?,?);");
				List<String> roles = user.getRoles();
				switch(roles.get(i)){
				case "Administrator":
					stmt.setInt(1, 1);
					break;
				case "Farmaceut":
					stmt.setInt(1, 2);
					break;
				case "Værkfører":
					stmt.setInt(1, 3);
					break;
				case "Laborant":
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
	public void updateOperatoer(UserDTO user) throws DALException{

		try { 
			PreparedStatement stmt = connector.getConnection().prepareStatement("call delete_roles(?);");
			stmt.setInt(1, user.getUserId());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call update_operatoer1(?,?,?,?,?,?);");
			stmt.setInt(1, user.getUserId());
			stmt.setString(2, user.getFirstname());
			stmt.setString(3, user.getLastname());
			stmt.setString(4, user.getIni());
			stmt.setString(5, user.getCPR());
			stmt.setString(6, user.getPassword());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		System.out.println(user);
		for (int i = 0; i < user.getRoles().size(); i++) {
			try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(3)
				PreparedStatement stmt = connector.getConnection().prepareStatement("call add_userroles(?,?);");
				List<String> roles = user.getRoles();
				switch(roles.get(i)){
				case "Administrator":
					stmt.setInt(1, 1);
					break;
				case "Farmaceut":
					stmt.setInt(1, 2);
					break;
				case "Værkfører":
					stmt.setInt(1, 3);
					break;
				case "Laborant":
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
