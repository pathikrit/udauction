A unit demand auction prototype in Java.

How to run:

0. You need SVN. Linux/Mac usually has svn available already or you can grab it from your default repository. Windows users should download SVN from here - http://tortoisesvn.tigris.org/
Go to next step after you have SVN running.

1. Download source:
`svn checkout http://udauction.googlecode.com/svn/ udauction`

2. Go to the udauction directory:
`cd udauction/`

3. Compile and run server:
`./run-server`

4. Edit the .client file to have the IP address of the server (for local testing, you can set IP = 127.0.0.1)

5. Compile and run client (in a new terminal):
`./run-client`

6. For list of valid commands, examine `src/server/ServerProtocol.java`.
Alternatively you can examine/execute `arc/client/ConsoleTester.java`
