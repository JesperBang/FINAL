package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DALException;
import dto.RaavareDTO;

public class MySQLRaavareDAO implements IRaavareDAO {

	private Connector connector = new Connector();

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		ResultSet rs = null;
		RaavareDTO raavare = new RaavareDTO();
		try { 
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from getraavare where raavare_id = ?;");
			stmt.setInt(1, raavareId);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
		try {
			if (!rs.first()) throw new DALException("User " + raavareId + " findes ikke");
			raavare.setLeverandoer(rs.getString("leverandoer"));
			raavare.setRaavareId(rs.getInt("raavare_id"));
			raavare.setRaavareNavn(rs.getString("raavare_navn"));
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}
		System.out.println(raavare.getRaavareId());
		return raavare;
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> list = new ArrayList<RaavareDTO>();
		ResultSet rs;
		try { 
			rs = connector.doQuery("select * from getraavare;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			RaavareDTO raavare;
			while(rs.next()){
			raavare = new RaavareDTO();
			raavare.setRaavareId(rs.getInt("raavare_id"));
			raavare.setRaavareNavn(rs.getString("raavare_navn"));
			raavare.setLeverandoer(rs.getString("leverandoer"));			
			list.add(raavare);
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}


	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		try { 
			PreparedStatement stmt = connector.getConnection().prepareStatement("call add_raavare(?,?,?);");
			stmt.setInt(1, raavare.getRaavareId());
			stmt.setString(2, raavare.getRaavareNavn());
			stmt.setString(3, raavare.getLeverandoer());

			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());

		}

	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		try {
			PreparedStatement stmt = connector.getConnection().prepareStatement("call update_raavare(?,?,?);");
			stmt.setInt(1, raavare.getRaavareId());
			stmt.setString(2, raavare.getRaavareNavn());
			stmt.setString(3, raavare.getLeverandoer());
			stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage());

		}
	}
}