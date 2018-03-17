/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.httpserver;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielagonzalez
 */
public class HttpServer implements Runnable{
    
    private Socket clientSocket = null;

    public HttpServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine, formato, datos;
            byte[] bytes;
            inputLine = in.readLine();
            if (inputLine != null){
                inputLine = inputLine.split(" ")[1];
                if (inputLine.contains(".html")){
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    datos = "" + bytes.length;
                    formato = "text/html";
                }else if (inputLine.contains(".jpg")){
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    datos = "" + bytes.length;
                    formato = "image/html";
                }else{
                    bytes = Files.readAllBytes(new File("./index.html").toPath());
                    datos = "" + bytes.length;
                    formato = "text/html";
                }
            }else {
                bytes = Files.readAllBytes(new File("./index.html").toPath());
                datos = "" + bytes.length;
                formato = "text/html";
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " 
                    + formato
                    + "\r\n"
                    + "Content-Length: " 
                    + datos
                    + "\r\n\r\n"; 
            byte[] bytesOut = outputLine.getBytes();
            byte[] ans = new byte[bytes.length + bytesOut.length];
            for (int i=0; i<bytesOut.length; i++){
                ans[i] = bytesOut[i];
            }
            for (int i=bytesOut.length; i<bytesOut.length + bytes.length; i++){
                ans[i] = bytes[i - bytesOut.length];
            }
            clientSocket.getOutputStream().write(ans);
            clientSocket.close(); 
        }catch (IOException e){
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
