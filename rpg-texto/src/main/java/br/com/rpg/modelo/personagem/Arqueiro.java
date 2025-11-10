package br.com.rpg.modelo.personagem;

import br.com.rpg.sistema.Dado;

public class Arqueiro extends Personagem{
    public Arqueiro(String nome){
        super(nome, 28, 6, 5, 1);
    }

    public Arqueiro(Arqueiro outro){
        super(outro);
    }

    @Override
    public void atacar(Personagem alvo){
        int rolagem = Dado.rolar(6);
        int dano = (this.ataque + rolagem) - alvo.defesa;
        if(dano < 0) dano = 0;
        alvo.receberDano(dano);
        System.out.println(nome + " dispara uma flecha (d6=" + rolagem + ") e causa " + dano + " de dano!");
    }
}
