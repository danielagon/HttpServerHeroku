/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author danielagonzalez
 */
public class ThreadHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try { 
            serverSocket = new ServerSocket(new Integer(System.getenv("PORT")));
            ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,3,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));
            while (true){
                executor.execute(new HttpServer(serverSocket.accept()));
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port.");
            System.exit(1);
        }
    }
}
