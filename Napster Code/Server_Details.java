import java.io.Serializable;

public class Server_Details implements Serializable{
	//Data structure to store the indexing information
	public String filename;
	int[] files=new int[20];
	public int choice;
	public int peer_id;
	public String sharedpath;
}
