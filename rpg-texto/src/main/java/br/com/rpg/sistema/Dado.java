package br.com.rpg.sistema;


public class Dado{
    private int faces;
    private int numeroDados;

    public Dado(int faces, int numeroDados){
        this.faces = faces;
        this.numeroDados = numeroDados;
    }

    public int rolar(int faces){
        System.out.println("Hello World!");
        faces = 3;
        return faces;
    }
}