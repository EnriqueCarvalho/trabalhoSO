package br.ufsm.csi.so.server;

import java.util.concurrent.Semaphore;

public class Reserva {
    private int numAssento;
    private boolean reservado;
    private String nome;
    private String data;
    private Semaphore mutex = new Semaphore(1 );

    public Reserva(int numAssento, boolean reservado, String nome, String data) {
        this.numAssento = numAssento;
        this.reservado = reservado;
        this.nome = nome;
        this.data = data;
    }
}
