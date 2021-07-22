package br.ufsm.csi.so.server;



import com.sun.deploy.net.MessageHeader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

public class Servidor {



    public static void main(String[] args) throws IOException {
        ArrayList <Reserva> reservas = new ArrayList();

        ServerSocket serverSocket = new ServerSocket(80);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    InputStream in = socket.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len = in.read(buffer);
                    String requisicao = new String(buffer, 0, len);
                    System.out.println("Conexão recebida: " + requisicao);
                    String[] lines = requisicao.split("\n");
                    String[] linha0 = lines[0].split(" ");
                    System.out.println("Comando: " + linha0[0] + " Recurso: " + linha0[1]);
                    OutputStream out = socket.getOutputStream();
                    File f = new File("resources" + File.separator + linha0[1]);

                    if (linha0[1].equals("/")) {
                        f = new File("resources" + File.separator + "index.html");

                    }else if (linha0[1].equals("/solicitar")){
                        f = new File("resources" + File.separator + "solicitar.html");

                    }else if (linha0[1].equals("/finalizar")){
                        Reserva res= new Reserva(1,true,"123","02/02/02");
                        reservas.add(res);

                    }else if (linha0[1].equals("/js/index.js")){
                        String js = Service.montaJS();
                        out.write(js.getBytes());
                    }
                    if (f.exists() && !linha0[1].equals("/js/index.js") ) {
                        FileInputStream fin = new FileInputStream(f);
                        String mimeType = Files.probeContentType(f.toPath());
                        System.out.println("servindo o arquivo " + f + "(" + mimeType + ")");
                        out.write(("HTTP/1.1 200 OK\n" +
                                "Content-Type: " + mimeType + ";charset=UTF-8\n\n").getBytes());
                        len = fin.read(buffer);

                        while (len > 0) {
                            out.write(buffer, 0, len);
                            len = fin.read(buffer);
                        }
                    }
                    out.flush();
                    socket.close();

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
    }
}
