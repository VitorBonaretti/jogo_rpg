package br.com.rpg.jogo;

import br.com.rpg.modelo.item.*;

public class Main {
    public static void main(String[] args) {
        Item pocao = new Item("Pocao de cura", TipoEfeito.CURA, 1);
        System.out.println(pocao);

        pocao.incrementar(2);
        System.out.println(pocao);

        Item pocaoDano = new Item("Pocao de dano", TipoEfeito.DANO_DIRETO, 2);
        System.out.println(pocaoDano);
    }
}
