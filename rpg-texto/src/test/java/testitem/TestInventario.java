package testitem;

import br.com.rpg.modelo.item.Item;
import br.com.rpg.modelo.item.TipoEfeito;
import br.com.rpg.modelo.item.Inventario;

public class TestInventario {
    public static void main(String[] args) {
    
        Inventario inv = new Inventario();
        inv.adicionar(new Item("Pocao de cura", TipoEfeito.CURA, 1));
        inv.listar();
        inv.remover("pocao de cura", 1);
    }
    
}
