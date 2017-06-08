package dao;

import java.util.List;

import database.DALException;
import dto.RaavareDTO;

public interface IRaavareDAO {
	
	RaavareDTO getRaavare(int raavareId) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;


}
