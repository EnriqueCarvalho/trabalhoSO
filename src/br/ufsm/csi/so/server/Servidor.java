package br.ufsm.csi.so.server;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Servidor {
    public static void main(String[] args) throws IOException {
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
                    }
                    if (f.exists()) {
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
