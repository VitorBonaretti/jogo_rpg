package br.com.rpg.modelo.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Inventario {
    private final List<Item> itens;

    public Inventario(){
        this.itens = new ArrayList<>();
    }

    private Inventario(List<Item> itensCopiados) {
        this.itens = itensCopiados;
    }

    public void adicionar(Item novo){
        if (novo == null || novo.getQuantidade() <= 0) return;

        for (Item existente : itens){
            if (existente.equals(novo)){
                existente.incrementar(novo.getQuantidade());
                return;
            }
        }
        itens.add(new Item(novo));
    }

    public boolean remover(String nome, int quantidade){
        if (nome == null || quantidade < 0) return false;

        for (Iterator<Item> it = itens.iterator(); it.hasNext();) {
            Item i = it.next();
            if (i.getNome().equalsIgnoreCase(nome)) {
                i.decrementar(quantidade);
                if (i.getQuantidade() <= 0) it.remove();
                return true;
            }
        }
        return false;
    }

    public Item buscarItem(String nome) {
        for(Item i : itens){
            if(i.getNome().equalsIgnoreCase(nome)){
                return i;
            }
        }
        return null;
    }

    public boolean temItemDe(TipoEfeito tipo){
        for(Item i : itens){
            if(i.getEfeito() == tipo && i.getQuantidade() > 0){
                return true;
            }
        }
        return false;
    }

    public String nomeDeUmItemCom(TipoEfeito tipo) {
    for (Item i : itens) {
        if (i.getEfeito() == tipo && i.getQuantidade() > 0)
            return i.getNome();
    }
    return null;
}

    public List<Item> ListaOrdenada(){
        List<Item> copia = new ArrayList<>();
        for(Item i : itens) {
            copia.add(new Item(i));
        }
        Collections.sort(copia);
        return copia;
    }

    public void listar() {
        System.out.println("=== Itens do Inventario ===");
        for (Item i : itens) {
            System.out.println(i.getNome() + " - " + i.getQuantidade() + "x");
        }
    }


    public Inventario CopiaProfunda() {
        List<Item> nova = new ArrayList<>();
        for (Item i : this.itens){
            nova.add(new Item(i));
        }
        return new Inventario(nova);
    }
}