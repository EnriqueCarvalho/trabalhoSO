package br.ufsm.csi.so.server;

public class Service {

    public static String montaJS(){
        String header;
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


        return header.concat(body);
    }

        body = "";

        return header;
    }
}
