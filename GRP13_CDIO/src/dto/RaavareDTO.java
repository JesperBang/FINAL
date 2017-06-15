package dto;

import java.io.Serializable;

public class RaavareDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3616698865321072048L;
	/** raavare id i området 1-99999999 vælges af brugerne */
	int raavareId; 
	
	public int getRaavareId() {return raavareId;}
	public void setRaavareId(int raavareId) {this.raavareId = raavareId;}
	
	
	/** min. 2 max. 20 karakterer */
	String raavareNavn; 
	
	public String getRaavareNavn() {return raavareNavn;}
	public void setRaavareNavn(String raavareNavn) {this.raavareNavn = raavareNavn;}
	
	
	/** min. 2 max. 20 karakterer */
	String leverandoer;
	
	public String getLeverandoer() {return leverandoer;}
	public void setLeverandoer(String leverandoer) {this.leverandoer = leverandoer;}
	


}
