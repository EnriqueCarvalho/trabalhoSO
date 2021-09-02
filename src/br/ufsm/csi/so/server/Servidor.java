
package br.ufsm.csi.so.server;


import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class Servidor {
    public static Semaphore mutex = new Semaphore(1 );
    public static ArrayList <Reserva> reservas = new ArrayList();
    public static Integer suc = 2 ;
    public static void main(String[] args) throws IOException {



        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Rodando...");
            while (true) {
                try {
                   new Thread(new Requisicao(serverSocket,suc,mutex,reservas)).start();
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
    }
}