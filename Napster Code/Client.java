import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Client {
	public Client()
	{
		Scanner  scan=new Scanner(System.in);
		int ssport,sport;
		Socket client=null;
		//String Sname=args[0];
		String Sname="127.0.0.1";
		String sharedpath;
		Server_Details sd=new Server_Details();
		Server_Details sd1=new Server_Details();
		//BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		
		int peer_id;
		try {
			System.out.println("Connecting to Server " + Sname + "  on Port number 8008");
			client=new Socket(Sname,8008); //Connecting to the server with port number 8008 
			System.out.println("Just Connected to client on port no " + client.getRemoteSocketAddress()  + " ");
			OutputStream out=client.getOutputStream(); //Declaring variable of outputstream for writing data
			ObjectOutputStream oos;
			InputStream is=client.getInputStream(); //Declaring variable of inputstream for reading data
			ObjectInputStream ois;
			System.out.println("Enter Peer Id of this Client");
			peer_id=scan.nextInt(); //Reading the peer id
			//peer_id=buffer.read();
			System.out.println("Enter the Shared Directory Path");
			scan.nextLine(); //reading the shared folder path
			sharedpath=scan.nextLine();
			System.out.println("Enter the port number of the peer to act as the server");
			//scan.nextInt();
			sport=scan.nextInt();//reading port number to act as the server to send file to the requesting peer
			fileTransfer ft=new fileTransfer(sport,sharedpath); //creating the file transefer thread.
			ft.start(); //start the thread
			int c=1;
			if(c==1)
			{
				File f;
				File sfd=new File(sharedpath);
				String[] flist=sfd.list();
				//writing the file names into the Server_details object and transfer object to the server
				for(int i=0;i<flist.length;i++)
				{
					f=new File(flist[i]);
					oos=new ObjectOutputStream(out);
					sd.filename=f.getName();
					sd.peer_id=peer_id;
					sd.choice=1;
					sd.sharedpath=sharedpath;
					oos.writeObject(sd);
					System.out.println("Registering File "+f.getName()+" from "+sharedpath+" to server");
					
				}
			}
			System.out.println("Enter 1 to search for a file 2 to exit");
			c=scan.nextInt();
			if(c==1)
			{
				try{
				System.out.println("Enter the file name you need");
				scan.nextLine();
				String name=scan.nextLine();
				sd.filename=name;
				sd.choice=2;
				oos=new ObjectOutputStream(out);
				oos.writeObject(sd); //transfer the file name that need to be searched to the server
				oos.flush(); //clear the writer buffer
				
				ois=new ObjectInputStream(is);
				//Server_Details sd1=(Server_Details)ois.readObject();
				sd1=(Server_Details)ois.readObject(); //read the peer id's which has the required file
				int check1=0;
				if(sd1.files.length!=0) //check if file is present 
				{
					if(sd1.files[0]!=0)
						System.out.println(sd.filename+" is present in");
					for(int i=0;i<sd1.files.length;i++)
					{	
						if(sd1.files[i]!=0) //display all the peer id's
						{
							check1=1;
							System.out.println("Peer "+sd1.files[i]);
						}
					}
					if(check1==0)
					{
						System.out.println("File not found in any peer");	
					}
					else
					{
						System.out.println("Select the peer#");
						System.out.print("");
						int peer=scan.nextInt();
						int check=0;
						//check if the selected peer is in the peer list sent by the server
						for(int i=0;i<sd1.files.length;i++)
						{	
							if(sd1.files[i]!=0)
							{
								if(peer==sd1.files[i])
								{
									check=1;
									break;
								}
							}
						}
						//enter the condition only if the selected peer is in the peer list sent by the server
						if(check==1)
						{
							//reading the port number of the server peer that is transfering the file
							System.out.println("Enter the port number of selected peer which is acting as a server");
							System.out.print("");
							ssport=scan.nextInt();
							ClientasServer(peer,ssport,sd1.filename,sd1.sharedpath,sharedpath); 	// call method to download the file
							System.out.println(sd.filename+" Downloaded from peer "+peer);
						}
						else
						{
							System.out.println("Invalied Peer");
							client.close();
						}
					}
				}
				else //if file not exist in the server
				{
					System.out.println("File does not exist in any of the peer.");
				}
				client.close();
				System.out.println("Client Disconnected from server.");
				}
				catch (IOException e) {
					System.out.println(" "+e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} //Enter else condition if the selected choose is to exit
			else
			{
				System.out.println("Closing");
				System.exit(1);
			}
		} catch (UnknownHostException e) {
			System.out.println(" "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(" "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("");
			System.out.println("*********Start the server first*********");
			System.out.println("");
		}
	}
	public static void ClientasServer(int clientasserverpeerid,int clientasserverportno,String filename,String sharedDir,String sharedpath)   
	{
		try{											
			//Connecting to the peer sever thread to download the file	
			Socket clientasserversocket=new Socket("localhost",clientasserverportno);
			ObjectOutputStream ooos=new ObjectOutputStream(clientasserversocket.getOutputStream());
			ooos.flush();
			ObjectInputStream oois=new ObjectInputStream(clientasserversocket.getInputStream());
			ooos.writeObject(filename); //sending file name to be downloaded 
			int readbytes=(int)oois.readObject();
			System.out.println("bytes transferred: "+readbytes);
			byte[] b=new byte[readbytes];
			oois.readFully(b); //read the file data
			OutputStream fileos=new FileOutputStream(sharedpath+"//"+filename); //stored the file name
			BufferedOutputStream bos=new BufferedOutputStream(fileos);
			bos.write(b, 0,(int) readbytes);
	        System.out.println(filename+" file has be downloaded "+sharedDir);
	        bos.flush();
	    }catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

