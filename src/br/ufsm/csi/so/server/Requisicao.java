package br.ufsm.csi.so.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Requisicao implements Runnable {

    public Requisicao(ServerSocket serverSocket, Integer suc, Semaphore mutex, ArrayList<Reserva> reservas) throws IOException, InterruptedException {

        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[2048];
        int len = in.read(buffer);
        String requisicao = new String(buffer, 0, len);


        String[] lines = requisicao.split("\n");
        String[] linha0 = lines[0].split(" ");

        /*  System.out.println("Comando: " + linha0[0] + " Recurso: " + linha0[1]);*/
        OutputStream out = socket.getOutputStream();
        File f = new File("resources" + File.separator + linha0[1]);

        if (linha0[1].equals("/")) {
            f = new File("resources" + File.separator + "index.html");


        } else if (linha0[1].startsWith("/solicitar")) {
            f = new File("resources" + File.separator + "solicitar.html");
            //produtorConsumidor


        } else if (linha0[1].startsWith("/finalizar")) {
            mutex.acquire();
            String nome = Service.getNomeStr(linha0[1]);
            Integer numAssento = Service.getAssentoInt(linha0[1]);
            String data = Service.getDateTime();

            if (!reservas.isEmpty()) {
                if (Reserva.verificarLugares(numAssento, reservas)) {
                    suc = 0;
                    System.out.println("[RESERVADO]");
                } else {
                    System.out.println("[NOVA RESERVA E JA EXISTIA]");
                    Reserva novaReserva = new Reserva(numAssento, true, nome, data);
                    reservas.add(novaReserva);
                    suc = 1;
                }
            } else {
                System.out.println("NOVA RESERVA");
                Reserva novaReserva = new Reserva(numAssento, true, nome, data);
                reservas.add(novaReserva);
                suc = 1;
            }
            mutex.release();
            f = new File("resources" + File.separator + "index.html");


        } else if (linha0[1].equals("/js/index.js")) {
            String js = Service.montaJS(reservas);
            out.write(js.getBytes());

        } else if (linha0[1].equals("/js/solicitar.js")) {
            String js = Service.montaJSSolicitar(reservas);
            out.write(js.getBytes());

        }
        if (f.exists() && !linha0[1].equals("/js/index.js") && !linha0[1].equals("/js/solicitar.js")) {
            FileInputStream fin = new FileInputStream(f);
            String mimeType = Files.probeContentType(f.toPath());


            out.write(("HTTP/1.1 200 OK\n" +
                    "Content-Type: " + mimeType + ";charset=UTF-8\n\n").getBytes());

            if (linha0[1].startsWith("/finalizar")) { //verifica se houve sucesso ou falha
                switch (suc) {
                    case 1:
                        out.write("<script type='text/javascript'>alert('Seu pedido foi realizado com sucesso')</script>".getBytes(StandardCharsets.UTF_8));
                        break;
                    case 0:
                        out.write("<script type='text/javascript'>alert('Opa :(, houve um problema com seu pedido, pedimos que escolha outro assento')</script>".getBytes(StandardCharsets.UTF_8));
                        break;
                    default:
                }
            }


            len = fin.read(buffer);


            while (len > 0) {
                out.write(buffer, 0, len);
                len = fin.read(buffer);
            }

        }


        out.flush();
        socket.close();
    }


    @Override
    public void run() {
    }
}


