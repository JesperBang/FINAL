package dto;

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
	public int opr_id;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String opr_fornavn; 
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String opr_efternavn;     
	
	String ini;
	/** Operatoer cpr-nr 10 karakterer */
	String cpr;                 
	/** Operatoer password min. 7 max. 8 karakterer */
	String password; 
	
	int aktiv;
	
	List<String> rollenavn;
	


	public UserDTO(){
		rollenavn = new ArrayList<String>();
		aktiv = 1;
	}
    
    public int getUserId() { return opr_id; }
	public void setUserId(int UserId) { this.opr_id = UserId; }
	public String getFirstname() { return opr_fornavn; }
	public void setFirstname(String Firstname) { this.opr_fornavn = Firstname; }
	public String getLastname() { return opr_efternavn; }
	public void setLastname(String Lastname) { this.opr_efternavn = Lastname; }
	public String getCPR() { return cpr; }
	public void setCPR(String CPR) { this.cpr = CPR; }
	public String getPassword() { return password; }
	public void setPassword(String Password) { this.password = Password; }
	   public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public int getAktiv() {
		return aktiv;
	}

	public void setAktiv(int aktiv) {
		this.aktiv = aktiv;
	}

	public List<String> getRoles() {
			return rollenavn;
		}

		public void setRoles(List<String> roles) {
			this.rollenavn = roles;
		}
		public void addRole(String role){
			rollenavn.add(role);
		}
	public String toString() { return "\n" + opr_id + "\t" + opr_fornavn + "\t" + opr_efternavn + "\t" + cpr + "\t" + password + "\t" + rollenavn + "\t" + aktiv; }
}
