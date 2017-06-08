package dao;

import java.util.List;

import database.DALException;
import dto.RaavareBatchDTO;
import dto.ReceptDTO;

public interface IReceptDAO {
	
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;

}
