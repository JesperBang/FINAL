package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class UserDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7272979590540794430L;
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	public int userId;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String firstname; 
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String lastname;     
	/** Operatoer cpr-nr 10 karakterer */
	String cpr;                 
	/** Operatoer password min. 7 max. 8 karakterer */
	String password; 
	List<String> roles;

	public UserDTO(){
		roles = new ArrayList<String>();
	}
    
    public int getUserId() { return userId; }
	public void setUserId(int UserId) { this.userId = UserId; }
	public String getFirstname() { return firstname; }
	public void setFirstname(String Firstname) { this.firstname = Firstname; }
	public String getLastname() { return lastname; }
	public void setLastname(String Lastname) { this.lastname = Lastname; }
	public String getCPR() { return cpr; }
	public void setCPR(String CPR) { this.cpr = CPR; }
	public String getPassword() { return password; }
	public void setPassword(String Password) { this.password = Password; }
	   public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
		public void addRole(String role){
			roles.add(role);
		}
	public String toString() { return "\n" + userId + "\t" + firstname + "\t" + lastname + "\t" + cpr + "\t" + password + "\t" + roles; }
}
