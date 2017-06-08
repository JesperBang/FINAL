package serverConnection;

import controller.Main;

public class TestController {

		 public static void main(String[] args) {
			 
			 ServerConnection[] connections = new ServerConnection[1];
			 for (int i = 0; i < connections.length; i++) {
				connections[i] = new ServerConnection(8000+i);
			}
			 
		        for(int i=0; i < 1; i++){
		        	String[] arg = {Integer.toString(8000+i)};
		        	Main.main(arg);
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		        	new Thread(connections[i]).start();
		        }
			 
			 
		 }

}
