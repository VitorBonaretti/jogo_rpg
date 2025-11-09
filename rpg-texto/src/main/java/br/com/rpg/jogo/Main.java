package br.com.rpg.jogo;

import br.com.rpg.modelo.item.*;

public class Main {
    public static void main(String[] args) {
        Inventario inv = new Inventario();
        inv.adicionar(new Item("Pocao de cura", TipoEfeito.CURA, 1));
        System.out.println(inv.ListaOrdenada().size());
        System.out.println("Antes de remover Item");
        inv.listar();

        inv.remover("Pocao de cura", 1);
        inv.listar();
        
    }
}
