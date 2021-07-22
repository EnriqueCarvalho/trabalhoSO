package br.ufsm.csi.so.server;

public class Service {

    public static String montaJS(){
        String header;
        String body;
        header = "\"HTTP/1.1 200 OK\\n\" +\n" +
                " \"Content-Type: text/javascript;charset=UTF-8\\n\\n\"";

        body = "";

        return header;
    }
}
