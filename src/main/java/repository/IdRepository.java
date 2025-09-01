// Software Management - Escolinha Raio de Sol
// Desenvolvido por Matheus Silva Rodrigues  - Discente da UEFS
// Projeto solicitado pela matéria MI - Algoritmos e Programação II
// Versão BETA 1.0

// Pacotes MVC
package repository;

// Importando bibliotecas
import java.util.concurrent.atomic.AtomicInteger;

// Início do código
public class IdRepository {
    private final AtomicInteger seq = new AtomicInteger(0);
}