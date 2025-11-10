package br.com.rpg.sistema;

import br.com.rpg.modelo.personagem.Inimigo;
import br.com.rpg.modelo.personagem.Personagem;

public final class SistemaDeCombate {
    public static void batalhar(Personagem jogador, Inimigo inimigo){
        System.out.printf("Combate iniciado: %s vs %s%n", jogador.getNome(), inimigo.getNome());

        while (jogador.estaVivo() && inimigo.estaVivo()){
            
        }
    }
}