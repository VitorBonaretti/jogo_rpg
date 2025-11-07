package testitem;

import br.com.rpg.modelo.item.Item;
import br.com.rpg.modelo.item.TipoEfeito;

public class TestItem {
    public static void main(String[] args) {
        Item pocaoCura = new Item("Pocao de cura", TipoEfeito.CURA, 1);
        Item pocaoDano = new Item("Pocao de dano", TipoEfeito.DANO_DIRETO, 1);
        Item pocaoDebuffArmadura = new Item("Pocao de debuff de armadura", TipoEfeito.DEBUFF_DEFESA, 1);
        Item pocaoBuffAtaque = new Item("Poca de buff em ataque", TipoEfeito.BUFF__ATAQUE, 1);
        
        System.out.println(pocaoCura);
        System.out.println(pocaoDano);
        System.out.println(pocaoDebuffArmadura);
        System.out.println(pocaoBuffAtaque);
    }
}