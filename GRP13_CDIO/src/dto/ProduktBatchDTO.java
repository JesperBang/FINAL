package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1935293472876734913L;
	/** produkt batch id i området 1-99999999. Vælges af brugerne */   
	int pbId;                   
	/** status 0: ikke påbegyndt, 1: under produktion, 2: afsluttet */
	int status;					
	/** recept id i området 1-99999999. Vælges af brugerne */
	int receptId;
	
	List<ProduktBatchKompDTO> komp = new ArrayList<ProduktBatchKompDTO>();
	
	public ProduktBatchDTO() {
	}
	public List<ProduktBatchKompDTO> getKomp() {
		return komp;
	}
	public void setKomp(List<ProduktBatchKompDTO> komp) {
		this.komp = komp;
	}
	
	public void addKomp(ProduktBatchKompDTO komp) {
		this.komp.add(komp);
	}
	
	public int getPbId() {
		return pbId;
	}
	public void setPbId(int pbId) {
		this.pbId = pbId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getReceptId() {
		return receptId;
	}
	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}


}
