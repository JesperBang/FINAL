package dao;

import java.util.List;

import database.DALException;
import dto.UserDTO;



public interface IUserDAO {
	UserDTO getUser(int UserId) throws DALException;
	List<UserDTO> getUserList() throws DALException;
	void createOperatoer(UserDTO opr) throws DALException;
	void updateOperatoer(UserDTO opr) throws DALException;
	void deactivateOperatoer(UserDTO opr) throws DALException;

}
