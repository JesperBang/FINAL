package serverConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dao.MySQLProduktBatchDAO;
import dao.MySQLReceptDAO;
import dao.MySQLUserDAO;
import database.UserDTO;
import dto.ProduktBatchDTO;
import dto.ProduktBatchKompDTO;
import dto.ReceptDTO;


public class ServerConnection implements Runnable{
	
	int portNumber;
	MySQLUserDAO userDAO = new MySQLUserDAO();
	MySQLProduktBatchDAO pBatchDAO = new MySQLProduktBatchDAO(); 
	MySQLReceptDAO receptDAO = new MySQLReceptDAO();
	
	public ServerConnection(int port){
		portNumber = port;
	}
  
	public void run() {
    	String hostName = "127.0.0.1";
    	Socket sock;
    	PrintWriter out = null;
    	BufferedReader in = null;
    	
    	
    	//Connects a socket to the weight
    	try {
    	   	sock = new Socket(hostName, portNumber);
            out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	    
    	String fromServer;
    	    
    	try {
    		while(true){
    		out.println("");
    		fromServer = in.readLine();
    		System.out.println("Server: " + fromServer);
    		
    		
    		UserDTO user = null;
    		//Gets user ID
    		out.println("RM20 8 usernr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				
				try {
				user = userDAO.getUser(Integer.parseInt(fromServer.split(" ")[2]));
				if(user.getFirstname().length()>0){
					
					out.println("P111 "+user.getFirstname() +" " + user.getLastname()+"?");
					fromServer = in.readLine();
					fromServer = in.readLine();
					break;
				} 
				} catch (Exception e) {
					out.println("RM20 8 Prøv_igen");
				}
			}
			
			ProduktBatchDTO pBatch = null;
			ReceptDTO recept = null;
			//Gets batch nr
			out.println("RM20 8 batchnr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				int a = Integer.parseInt(fromServer.split(" ")[2]);
				
				try {
				pBatch = pBatchDAO.getProduktBatch(a);
				recept = receptDAO.getRecept(pBatch.getReceptId());
				} catch(Exception e) {
					e.printStackTrace();
				}
				if(pBatch == null || recept == null){
					out.println("RM20 8 Prøv_igen");
				} else {
					out.println("P111 " + recept.getReceptNavn());
					fromServer = in.readLine();
					fromServer = in.readLine();
					break;
				}
			}
			pBatch.setStatus(1);
			pBatchDAO.updateStatus(pBatch);
			
			List<ProduktBatchKompDTO> oldKomp =  pBatch.getKomp();
			List<ProduktBatchKompDTO> newKomp =  new ArrayList<ProduktBatchKompDTO>();
			
			System.out.println(oldKomp.size());
			
			for (int i = 0; i < oldKomp.size(); i++) {
				
				ProduktBatchKompDTO currKomp = oldKomp.get(i);
				
				currKomp.setOprId(user.getUserId());
				
				//Instructions for user
			
				out.println("P111 Tøm vægten");
				fromServer = in.readLine();
				fromServer = in.readLine();
				
				out.println("T");
				fromServer = in.readLine();
				
				out.println("P111 Placer tara");
				fromServer = in.readLine();
				fromServer = in.readLine();
				
				out.println("T");
				fromServer = in.readLine();
				double tara = Double.parseDouble(fromServer.split(" ")[2]);
				
				currKomp.setTara(tara);
				System.out.println("tara: " + tara);
				
				out.println("P111 Placer netto rb " + currKomp.getRbId());
				fromServer = in.readLine();
				fromServer = in.readLine();
				
				out.println("S");
				fromServer = in.readLine();
				double netto = Double.parseDouble(fromServer.split(" ")[2]);
				
				currKomp.setNetto(netto);
				System.out.println("netto: " + netto);
				
				out.println("T");
				fromServer = in.readLine();
				
				out.println("P111 Fjern brutto");
				fromServer = in.readLine();
				fromServer = in.readLine();
				
				out.println("S");
				fromServer = in.readLine();
				double brutto = Double.parseDouble(fromServer.split(" ")[2]);
				
				System.out.println("brutto: " + brutto);
			
				
				//Check if weight was correctly used
				if (brutto + tara + netto == 0){
					out.println("P111 OK");
				} else {
					out.println("P111 Kasseret");
				}
				fromServer = in.readLine();
				fromServer = in.readLine();
				
				out.println("T");
				fromServer = in.readLine();
				
				newKomp.add(currKomp);
				
			}
				
			pBatch.setKomp(newKomp);
			
			pBatch.setStatus(2);
			
			pBatchDAO.updateProduktBatch(pBatch);
			
			
    		}
		} catch (Exception e) {
			System.out.println("Vægt slukket");
		}
    	
    	
    	
    }
}