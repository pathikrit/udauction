How to run (on Linux/Mac):

1. Download source:
svn checkout http://udauction.googlecode.com/svn/ udauction

2. Go to the udauction directory:
cd udauction/

3. Compile and run server:
./run-server

4. Edit the .client file to have the IP address of the server (for local testing, you can set IP = 127.0.0.1)

5. Compile and run client (in a new terminal):
./run-client

6. For list of valid commands, examine server/ServerProtocol.java .
Alternatively you can examine/execute client/ConsoleTester.java