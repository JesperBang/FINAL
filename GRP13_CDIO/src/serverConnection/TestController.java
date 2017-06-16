package serverConnection;

public class TestController {

		 public static void main(String[] args) {
			 
			 ServerConnection[] connections = new ServerConnection[1];
			 for (int i = 0; i < connections.length; i++) {
				connections[i] = new ServerConnection(8000+i);
			}
			 
		        for(int i=0; i < connections.length; i++){
		        	
		        	new Thread(connections[i]).start();
		        }
			 
			 
		 }

}
