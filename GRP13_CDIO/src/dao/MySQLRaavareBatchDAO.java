package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import dto.RaavareBatchDTO;


public class MySQLRaavareBatchDAO implements IRaavareBatchDAO {

	 
		private Connector connector = new Connector();

		@Override
		public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
			ResultSet rs = null;
			RaavareBatchDTO raavarebatch = new RaavareBatchDTO();
			try { 
				PreparedStatement stmt = connector.getConnection().prepareStatement("select * from getraavarebatch where rb_id = ?;");
				stmt.setInt(1, rbId);
				rs = stmt.executeQuery();
			} catch (Exception e) {
				throw new DALException(e.getMessage()); 
			}
			try {
				if (!rs.first()) throw new DALException("RaavareBatch " + rbId + " findes ikke");
				raavarebatch.setRbId(rs.getInt("RbId"));
				raavarebatch.setRaavareId(rs.getInt("raavareId"));
				raavarebatch.setMaengde(rs.getDouble("maengde"));
			}
			catch (SQLException e) {
				throw new DALException(e); 
			}
			
			return raavarebatch;
	
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs;
		try { 
			rs = connector.doQuery("select * from getraavarebatch;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			RaavareBatchDTO raavarebatch = new RaavareBatchDTO();

			raavarebatch.setRbId(rs.getInt("RbId"));
			raavarebatch.setRaavareId(rs.getInt("raavareId"));
			raavarebatch.setMaengde(rs.getDouble("maengde"));



			list.add(raavarebatch);
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs;
		try { 
			rs = connector.doQuery("select * from getraavarebatch where RbId = ?;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			RaavareBatchDTO raavarebatch = new RaavareBatchDTO();

			raavarebatch.setRbId(rs.getInt("RbId"));
			raavarebatch.setRaavareId(rs.getInt("raavareId"));
			raavarebatch.setMaengde(rs.getDouble("maengde"));



			list.add(raavarebatch);
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		try { 
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_raavarebatch(?,?,?);");
			stmt.setInt(1, raavarebatch.getRbId());
			stmt.setInt(2, raavarebatch.getRaavareId());
			stmt.setDouble(3, raavarebatch.getMaengde());

			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());

		}


	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		try {
			PreparedStatement stmt = connector.getConnection().prepareStatement("call update_raavarebatch(?,?,?);");
			stmt.setInt(1, raavarebatch.getRbId());
			stmt.setInt(2, raavarebatch.getRaavareId());
			stmt.setDouble(3, raavarebatch.getMaengde());
		} catch (Exception e) {
			throw new DALException(e.getMessage());

		}

	}

}
