import java.util.Scanner;

public class main {
	public static void main(String args[])
	{
		Scanner  scan=new Scanner(System.in);
		System.out.println("Select you Option");
		System.out.println("1 to Run as Server");
		System.out.println("2 to Run as Client");
		int ch=scan.nextInt();
		if(ch==1)
		{
			Server s=new Server();
		}
		else if(ch==2) 
		{
			Client c=new Client();
		}
		else
			System.out.println("Invalied Choice...!");

	}
}
