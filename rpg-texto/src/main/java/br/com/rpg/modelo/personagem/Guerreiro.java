package br.com.rpg.modelo.personagem;

import br.com.rpg.sistema.Dado;

public class Guerreiro extends Personagem{
    public Guerreiro(String nome){
        super(nome, 30, 7, 6, 1);
    }

    public Guerreiro(Guerreiro outro){
        super(outro);
    }

    @Override
    public void atacar(Personagem alvo){
        int rolagem = Dado.rolar(6);
        int dano = (this.ataque + rolagem) - alvo.defesa;
        if(dano < 0) dano = 0;
        alvo.receberDano(dano);
        System.out.println(nome + " ataca com a espada (d6=" + rolagem + ") e causa " + dano + " de dano!");
    }
}
