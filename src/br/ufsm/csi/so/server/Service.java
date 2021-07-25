package br.ufsm.csi.so.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Service {

    public static String montaJS(ArrayList<Reserva> reservas){
        String header = getHeader();
        System.out.println("MOonta js");
        String body = "";

        for (Reserva res : reservas) {
            if(res.isReservado()) {
                body=body+" document.getElementById('" + res.getNumAssento() + "').classList.add('ocupado')\n";
                body = body+ " $('#teste').append('<tr> <th>"+res.getNumAssento()+"</th><td>"+res.getNome()+"</td><td>"+res.getData()+"</td></tr>')\n";
            }
        }

        return header.concat(body);
    }
    public static String montaJSSolicitar(ArrayList<Reserva> reservas){
        String header = getHeader();
        System.out.println("Monta js");
        String body = "";

        for (Reserva res : reservas) {
            if(res.isReservado()) {
                body=body+" $('#"+res.getNumAssento()+"').remove(); ";
                body=body+" $('.icon0"+res.getNumAssento()+"').addClass('ocupado'); ";
            }
        }



        return header.concat(body);
    }
    public static String getHeader(){
        return "HTTP/1.1 200 OK\n Content-Type: text/javascript;charset=UTF-8\n\n";
    }


    public static String getNomeStr(String nome){
        String[] finUrl = nome.split("=");
        String[] nomeRet = finUrl[1].split("&");
        return nomeRet[0].toString();
    }
    public static Integer getAssentoInt(String num){
        String[] finUrl = num.split("=");
        return Integer.parseInt(finUrl[2].toString());
    }

    public static  String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}