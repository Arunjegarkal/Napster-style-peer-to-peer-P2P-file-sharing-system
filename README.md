# Napster-style-peer-to-peer-P2P-file-sharing-system

    Implementation 

I have used Socket Implementation of Napster-style peer-to-peer (P2P) file sharing system. The main idea of this project is to Create the indexing server which stores all the file details and bringing it online so that all the peers connect to it and register its files on the server. Once files are registered it provided with 2 option to search for the file in other peer or can exit the execution, if search is selected file name is asked and port number and sent to the server, sever will return the peer id’s which as the respected file and then client has to select the peer id from which file to be downloaded. Downloaded file will be stored into the requested peer’s shared directory. Every Peer can provide its shared director and can register its files so that other peer’s can search and download them.
 
Every Client Server connection is done using Sockets, given the port number and the Server name client establishes a connection with the Server registering files and searching files among peers.

    How it Works

Server Socket

Server.java
Server run’s with the port number 8888, and continually polls for the client connection. Once connection is established to the client server create the serialized Inputstream to receive the client data and de serialize the data once it is received, whether client requested for registering the files or searching a file. Server performs requested operation and return the result in serialized object back to the client.

Client Socket

Client.java
Once client connects to the server, client sends the shared directory path from which all the files are fetched and registered to the server. Once the file’s are registered client is asked to choose for searching file or want to exit. On search user enter the file to be searched and this file name is sent to the server in the form of serialized objects, client receives the peer list which contains the searched file.
Client need to select the peer from which the file need to be downloaded by providing the peer’s server port number and then the connection is established to the peer’s server using the provided port number and then file will be downloaded in terms of byte stream



    Performance

Response Time

 
Improvements:
•	Instead of asking port number from user program can handle by auto generating numbers storing used port numbers in some data structure so that same port number cannot be used.
•	This Project can be improve by provide User interface
