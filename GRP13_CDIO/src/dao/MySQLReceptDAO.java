package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import dto.ReceptDTO;

public class MySQLReceptDAO implements IReceptDAO {

	private Connector connector = new Connector();
	public ReceptDTO getRecept(int receptId) throws DALException{
		ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from getrecept where recept_id = ?;");
			stmt.setInt(1, receptId);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
	    try {
	    	if (!rs.first()) throw new DALException("Recept " + receptId + " findes ikke");
	    	ReceptDTO recept = new ReceptDTO ();
	    	recept.setReceptId(rs.getInt("recept_id"));
	    	recept.setReceptNavn(rs.getString("recept_navn"));
	    	
	    	return recept;
	    }
	    catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	}
	public List<ReceptDTO> getReceptList() throws DALException{
		List<ReceptDTO> list = new ArrayList<ReceptDTO>();
		ResultSet rs;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(1)
			rs = connector.doQuery("select * from getrecept;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			ReceptDTO recept;
			while (rs.next()) 
			{
				recept = new ReceptDTO();
				recept.setReceptId(rs.getInt("recept_id"));
				recept.setReceptNavn(rs.getString("recept_navn"));
				list.add(recept);
			}
				
			
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	public void createRecept(ReceptDTO recept) throws DALException{

		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_recept(?,?);");
			stmt.setInt(1, recept.getReceptId());
			stmt.setString(2, recept.getReceptNavn());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
	}
	public void updateRecept(ReceptDTO recept) throws DALException{
		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call update_recept(?,?);");
			stmt.setInt(1, recept.getReceptId());
			stmt.setString(2, recept.getReceptNavn());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		
	}
	
}
