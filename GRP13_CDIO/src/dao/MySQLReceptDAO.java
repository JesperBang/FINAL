package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import dto.ReceptDTO;
import dto.ReceptKompDTO;

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
		ResultSet rs, rs2;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(1)
			rs = connector.doQuery("select * from getrecept;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			ReceptDTO recept;
			PreparedStatement stmt;
			while (rs.next()) 
			{
				recept = new ReceptDTO();
				recept.setReceptId(rs.getInt("recept_id"));
				recept.setReceptNavn(rs.getString("recept_navn"));
				stmt = connector.getConnection().prepareStatement("select * from getreceptkomponent where recept_id = ?");
				stmt.setInt(1, recept.getReceptId());
				rs2 = stmt.executeQuery();
				ReceptKompDTO komp;
				while(rs2.next()){
					komp = new ReceptKompDTO();
					komp.setReceptId(rs2.getInt("recept_id"));
					komp.setNomNetto(rs2.getDouble("nom_netto"));
					komp.setRaavareId(rs2.getInt("raavare_id"));
					komp.setTolerance(rs2.getDouble("tolerance"));
					recept.addKomp(komp);
					
				}
				
		
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
			List<ReceptKompDTO> komps = recept.getKomp();
			for (ReceptKompDTO komp : komps) {
				komp.setReceptId(recept.getReceptId());
				stmt = connector.getConnection().prepareStatement("call add_receptkomponent(?,?,?,?);");
				stmt.setInt(1, komp.getReceptId());
				stmt.setInt(2, komp.getRaavareId());
				stmt.setDouble(3, komp.getNomNetto());
				stmt.setDouble(4, komp.getTolerance());
				stmt.executeQuery();
			}
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
