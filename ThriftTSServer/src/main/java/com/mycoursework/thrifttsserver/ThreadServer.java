package com.mycoursework.thrifttsserver;

import com.mycoursework.thriftimplementation.TimeStampImplementation;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class ThreadServer implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                TServerSocket serverTransport = new TServerSocket(10000);
                Server.Processor processor = new Server.Processor(new TimeStampImplementation());
                TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
                System.err.println("Server listening on port 10000...");
                server.serve();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
