import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public Server()
	{
		ServerSocket serversocket=null;
		Socket socket=null;
		ArrayList<Files> files=new ArrayList<Files>();//Data structure to store the file information 
		try 
		{
			serversocket=new ServerSocket(8008); //creating the server socket
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server Started at Port number 8888");
		while(true) //looping for connection from the clients
		{
			System.out.println("Waiting for Client...");
			try {
				socket=serversocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(" "+e.getMessage());
				e.printStackTrace();
			};
			
			new serverthread(socket,files).start(); //starting the server thread
		}
	}
}

class serverthread extends Thread{
	Socket server;
	int choice = 1;
	ArrayList<Files> files=new ArrayList<Files>(); 
	Files ff=new Files(); //Creating the object of File class for storing file information
	Server_Details sd=new Server_Details(); 
	public serverthread(Socket server,ArrayList<Files> files)
	{
		this.server=server;
		this.files=files;
	}
	public void run() //run the server thread
	{
		try {
			OutputStream os= server.getOutputStream();
			InputStream is=server.getInputStream();
			ObjectOutputStream oos;
			ObjectInputStream ois;
			
			System.out.println("Connnected to Client on port "+ server.getRemoteSocketAddress());
			while(choice == 1){
				ois=new ObjectInputStream(is);
				sd=(Server_Details) ois.readObject();
				choice=sd.choice;
				if(choice==2)
					break;
				else
				{
					//store all the file information into the data structure
					Files f=new Files();
					f.peer_id=sd.peer_id;
					f.filename=sd.filename;
					f.sharedpath=sd.sharedpath;
					files.add(f);
					System.out.println("File "+f.filename+" Registred from Peer "+f.peer_id+" Shared Dir "+f.sharedpath);
				}
				
				
			}
			if(choice==2) //search for the file
			{
				Files f;
				int count=0;
				for(int i=0;i<files.size();i++)
				{
					f=files.get(i);
					if(f.filename.equals(sd.filename))
					{	
						sd.files[count++]=f.peer_id;
						sd.sharedpath=f.sharedpath;
					}
				}
				oos=new ObjectOutputStream(os);
				oos.writeObject(sd); //return the peer id's that has the requested file
				oos.flush();
				oos.close();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found");
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Client Disconnected");
		} 
	}
}
