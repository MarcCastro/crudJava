/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud4sem;

/**
 *
 * @author Felipe
 */
// Classe que serve para criar uma instância de cada cliente dentro do banco
public class Cliente {
    private String cod, nome, nasc, cpf;
    
    /*public Cliente(){
        
    }
    
    public Cliente(String cod, String nome, String nasc, String cpf){
        this.cod = cod;
        this.nome = nome;
        this.nasc = nasc;
        this.cpf = cpf;
    }*/

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the nasc
     */
    public String getNasc() {
        return nasc;
    }

    /**
     * @param nasc the nasc to set
     */
    public void setNasc(String nasc) {
        this.nasc = nasc;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }
}
