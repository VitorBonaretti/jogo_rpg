package br.com.rpg.modelo.item;

import  java.util.Objects;

public class Item implements Comparable<Item>{
    private String nome;
    private TipoEfeito efeito;
    private int quantidade;

    public Item(String nome, TipoEfeito efeito, int quantidade){
        this.nome = nome;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    public Item(Item outro){
        this.nome = outro.nome;
        this.efeito = outro.efeito;
        this.quantidade = outro.quantidade;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public TipoEfeito getEfeito(){
        return efeito;
    }

    public String getNome(){
        return this.nome;
    }

    public void setQuantidade(int quantidade){
        if(quantidade < 0)
            throw new IllegalArgumentException("Quantidade invalida de itens!");
        this.quantidade = quantidade;
    }

    public void setEfeito(TipoEfeito efeito){
        this.efeito = efeito;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void incrementar(int delta){
        if (delta > 0){
            this.quantidade += delta;
        }
    }

    public void decrementar(int delta){
        if (delta > 0){
            this.quantidade -= delta;

            if (this.quantidade < 0){
                this.quantidade = 0;
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Item outro = (Item) obj;
        return nome.equalsIgnoreCase(outro.nome) && efeito == outro.efeito;
    }

    @Override
    public int hashCode(){
        return Objects.hash(nome.toLowerCase(), efeito);
    }

    @Override
    public int compareTo(Item outro){
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    @Override
    public String toString(){
        return nome + " (" + quantidade + "x)";
    }

}