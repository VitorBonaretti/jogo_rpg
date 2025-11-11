package br.com.rpg.modelo.personagem;

import br.com.rpg.sistema.Dado;

public class Mago extends Personagem{
    public Mago(String nome){
        super(nome, 23, 12, 3, 1);
    }

    public Mago(Mago outro){
        super(outro);
    }

    @Override
    public void atacar(Personagem alvo){
        int rolagem = Dado.rolar(6);
        int dano = (this.ataque + rolagem) - alvo.defesa;
        if(dano < 0) dano = 0;
        alvo.receberDano(dano);
        System.out.println(nome + " lança um feitiço (d6=" + rolagem + ") e causa " + dano + " de dano!");
    }
}
