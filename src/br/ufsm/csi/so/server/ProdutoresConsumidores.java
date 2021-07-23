package br.ufsm.csi.so.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ProdutoresConsumidores {

    static int tamBuf = 200;
    static byte[] logBuffer = new byte[tamBuf];
    Socket Socket;
    InetAddress ipReservado;
    static Semaphore vazio = new Semaphore(tamBuf);
    static Semaphore cheio = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static int iStatic;
    static File file = new File("log.txt");

    public ProdutoresConsumidores(Socket socket) {

        Socket = socket;

        try {
            if (file.createNewFile()) {
                System.out.println("Arquivo criado: " + file.getName());
            } else {
                System.out.println("O arquivo já existe.");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        new Thread(new ProdutoresConsumidores.Produtor()).start();
        new Thread(new ProdutoresConsumidores.Consumidor()).start();

    }

    private class Produtor implements Runnable {

        @Override
        public void run() {

            ipReservado = Socket.getInetAddress();
            String ipString = ipReservado.toString();
            ipString = ipString + " \n";
            byte[] ipBype = ipString.getBytes();
            int tamBufAtual = logBuffer.length - iStatic;

            if (ipBype.length > tamBufAtual) {
                cheio.release();
            }
            try {
                vazio.acquire(ipBype.length);
            } catch (InterruptedException e) {
            }

            for (int i = 0; i < ipBype.length; i++) {
                logBuffer[iStatic] = ipBype[i];
                iStatic++;
            }
        
        }

    }

    private class Consumidor implements Runnable {

        @Override
        public void run() {

            try {
                cheio.acquire();
            } catch (InterruptedException e) {
                // TODO: handle exception
            }

            String logString = new String(logBuffer, 0, iStatic);
            iStatic = 0;
            logBuffer = new byte[tamBuf];
            System.out.println("esvaziou");

            vazio.release(tamBuf);

            try {
                FileWriter myWriter = new FileWriter("log.txt", true);
                myWriter.write(logString);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Erro ao gravar os dados");
                e.printStackTrace();
            }
        
        }

    }
}