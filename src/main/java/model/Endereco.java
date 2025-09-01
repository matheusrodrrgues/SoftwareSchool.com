// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package model;

/**
 * Classe que representa o endereço de uma pessoa.
 * Armazena rua, bairro, CEP, cidade e estado.
 * @author Matheus Silva Rodrigues
 * @version 1.0
 */
// Início do código
public class Endereco {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

    public Endereco() {}
    public Endereco(String rua, String bairro, String cep, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }
    /**
     * Retorna uma representação em texto do endereço.
     * @return String com dados do endereço
     */
    @Override public String toString() { return rua+", "+bairro+", CEP: "+cep+" — "+cidade+"/"+estado; }
    /**
     * Retorna a rua do endereço.
     * @return rua
     */
    public String getRua() {
        return rua;
    }
    /**
     * Retorna o bairro do endereço.
     * @return bairro
     */
    public String getBairro() {
        return bairro;
    }
    /**
     * Retorna o CEP do endereço.
     * @return cep
     */
    public String getCep() {
        return cep;
    }
    /**
     * Retorna a cidade do endereço.
     * @return cidade
     */
    public String getCidade() {
        return cidade;
    }
    /**
     * Retorna o estado do endereço.
     * @return estado
     */
    public String getEstado() {
        return estado;
    }
}