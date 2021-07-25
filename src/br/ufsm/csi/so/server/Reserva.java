package br.ufsm.csi.so.server;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Reserva {
    private int numAssento;
    private boolean reservado;
    private String nome;
    private String data;


    public Reserva(int numAssento, boolean reservado, String nome, String data) {
        this.numAssento = numAssento;
        this.reservado = reservado;
        this.nome = nome;
        this.data = data;
    }



    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public int getNumAssento() {
        return numAssento;
    }

    public void setNumAssento(int numAssento) {
        this.numAssento = numAssento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static boolean verificarLugares(Integer numAssento, ArrayList<Reserva> res){
        for (Reserva reservas : res) {

            if (reservas.getNumAssento()==numAssento){
                return true;
            }
        }
        return false;
    }
}
