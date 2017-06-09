package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import dto.ProduktBatchDTO;
import dto.ProduktBatchKompDTO;


public class MySQLProduktBatchDAO implements IProduktBatchDAO {


	private Connector connector = new Connector();
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		// TODO Auto-generated method stub
		
		ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from getproduktbatch where pb_id = ?;");
			stmt.setInt(1, pbId);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
	    try {
	    	if (!rs.first()) throw new DALException("Produktbatch " + pbId + " findes ikke");
	    	ProduktBatchDTO produkt = new ProduktBatchDTO ();
	    	produkt.setPbId(rs.getInt("pb_id"));
	    	produkt.setStatus(rs.getInt("status"));
	    	produkt.setReceptId(rs.getInt("recept_id"));
	    	return produkt;
	    }
	    catch (SQLException e) {
	    	throw new DALException(e); 
	    }
		
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs, rs2;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(1)
			rs = connector.doQuery("select * from getproduktbatch;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			ProduktBatchDTO produkt;
			PreparedStatement stmt;
			while (rs.next()) 
			{
				produkt = new ProduktBatchDTO();
				produkt.setPbId(rs.getInt("pb_id"));
				produkt.setStatus(rs.getInt("status"));
				produkt.setReceptId(rs.getInt("recept_id"));
				stmt = connector.getConnection().prepareStatement("select * from getproduktbatchkomponent where pb_id = ?;");
				stmt.setInt(1, produkt.getPbId());
				rs2 = stmt.executeQuery();
				ProduktBatchKompDTO komp;
				while(rs2.next()){
					komp = new ProduktBatchKompDTO();
					komp.setPbId(rs2.getInt("pb_id"));
					komp.setNetto(rs2.getDouble("netto"));
					komp.setTara(rs2.getDouble("tara"));
					komp.setOprId(rs2.getInt("opr_id"));
					komp.setRbId(rs2.getInt("rb_id"));
					produkt.addKomp(komp);
				}
				
				
				list.add(produkt);
			}
				
			
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {

		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_produktbatch(?,?,?);");
			stmt.setInt(1, produktbatch.getPbId());
			stmt.setInt(2, produktbatch.getStatus());
			stmt.setInt(3, produktbatch.getReceptId());
			stmt.executeQuery();
			List<ProduktBatchKompDTO> komp = produktbatch.getKomp();
			PreparedStatement stmt2;
			for (ProduktBatchKompDTO kompDTO : komp) {
				stmt2 = connector.getConnection().prepareStatement("call add_produktbatchkomponent(?,?,?,?,?);");
				stmt2.setInt(1, kompDTO.getPbId());
				stmt2.setInt(2, kompDTO.getRbId());
				stmt2.setDouble(3, kompDTO.getTara());
				stmt2.setDouble(4, kompDTO.getNetto());
				stmt2.setInt(5, kompDTO.getOprId());
				stmt2.executeQuery();
				
			}
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
	}

	

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		try { //Files.readAllLines(Paths.get("UserCommands.txt")).get(2)
			PreparedStatement stmt = connector.getConnection().prepareStatement("call update_produktbatch(?,?);");
			stmt.setInt(1, produktbatch.getPbId());
			stmt.setInt(2, produktbatch.getStatus());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}

	}

}
