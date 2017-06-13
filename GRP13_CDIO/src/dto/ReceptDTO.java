package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReceptDTO implements Serializable {
	
	private static final long serialVersionUID = -7272979590540794430L;
	/** recept id i området 1-99999999 */
	int receptId;
	/** Receptnavn min. 2 max. 20 karakterer */
	String receptNavn;
	public int getReceptId() {
		return receptId;
	}
	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}
	public String getReceptNavn() {
		return receptNavn;
	}
	public void setReceptNavn(String receptNavn) {
		this.receptNavn = receptNavn;
	}

	List<ReceptKompDTO> komp = new ArrayList<ReceptKompDTO>();
	
	public void ReceptKompDTO() {
	}
	public List<ReceptKompDTO> getKomp() {
		return komp;
	}
	public void setKomp(List<ReceptKompDTO> komp) {
		this.komp = komp;
	}
	
	public void addKomp(ReceptKompDTO komp) {
		this.komp.add(komp);
	}

}


