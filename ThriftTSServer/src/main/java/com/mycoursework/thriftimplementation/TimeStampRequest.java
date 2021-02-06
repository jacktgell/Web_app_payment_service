package com.mycoursework.thriftimplementation;

import com.mycoursework.thrifttsserver.ThreadServer;

public class TimeStampRequest {
    
    public static void main(String args[]){
        ThreadServer ts = new ThreadServer();
        Thread t = new Thread(ts);
        t.start();
    }
}
