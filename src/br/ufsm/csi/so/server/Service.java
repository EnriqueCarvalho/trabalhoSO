package br.ufsm.csi.so.server;

import java.util.ArrayList;

public class Service {

    public static String montaJS(ArrayList<Reserva> reservas){
        String header;
        System.out.println("MOonta js");
        String body;
        header = "HTTP/1.1 200 OK\n"+
                " Content-Type: text/javascript;charset=UTF-8\n\n";

       body = "";
        for (Reserva res : reservas) {
            if(res.isReservado()) {
                body=body+" document.getElementById('" + res.getNumAssento() + "').classList.add('ocupado')\n";
                body = body+ " $('#teste').append('<tr> <th>"+res.getNumAssento()+"</th><td>"+res.getNome()+"</td><td>"+res.getData()+"</td></tr>')\n";
            }
        }


        System.out.println(header.concat(body));
        return header.concat(body);
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
}
