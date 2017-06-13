package database;

import java.util.List;



public interface UserDAO {
	UserDTO getUser(int UserId) throws DALException;
	List<UserDTO> getUserList() throws DALException;
	void createOperatoer(UserDTO opr) throws DALException;
	void updateOperatoer(UserDTO opr) throws DALException;
	void deactivateOperatoer(UserDTO opr) throws DALException;

}
