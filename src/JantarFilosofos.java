import java.util.concurrent.Semaphore;  // Importa a classe Semaphore do pacote java.util.concurrent.
// comentários explicando a classe semaphore:
/*
 * acquire() é utilizado para adquirir (ou bloquear) um semáforo
 * release() é utilizado para liberar (ou desbloquear) um semáforo
 */
public class JantarFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;  // Define o número de filósofos.
        Filosofo[] filosofos = new Filosofo[numFilosofos];  // Cria um array de filósofos.
        Semaphore[] garfos = new Semaphore[numFilosofos];  // Cria um array de semáforos para representar os garfos.

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Semaphore(1); // Inicializa cada garfo com disponibilidade (1).
        }

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % numFilosofos]); // Inicializa os filósofos com seus garfos correspondentes.
            filosofos[i].start(); // Inicia as threads dos filósofos.(5)
        }
    }
}

class Filosofo extends Thread {
    // atributos
    private int id;  // Identificação do filósofo.
    private Semaphore garfoesquerdo;  // Semáforo para o garfo esquerdo.
    private Semaphore garfodireito;  // Semáforo para o garfo direito.

    // construtor
    public Filosofo(int id, Semaphore garfoesquerdo, Semaphore garfodireito) {
        this.id = id;  // Inicializa a identificação do filósofo.
        this.garfoesquerdo = garfoesquerdo;  // Inicializa o semáforo do garfo esquerdo.
        this.garfodireito = garfodireito;  // Inicializa o semáforo do garfo direito.
    }

    // metodo que executa a thread
    public void run() {
        try {
            while (true) {  // Entra em um loop infinito para que o filósofo continue a agir.
                pensar();  // Chama o método pensar para simular o pensamento do filósofo.
                pegueGarfos();  // Chama o método pegueGarfos para pegar os garfos e se preparar para comer.
                comer();  // Chama o método comer para simular o ato de comer.
                abaixarGarfos();  // Chama o método abaixarGarfos para colocar os garfos de volta na mesa.
            }
        } catch (InterruptedException e) {  // Tratamento de exceção para interrupção da thread.
            Thread.currentThread().interrupt();  // Marca a thread como interrompida.
        }
    }

    // métodos encapsulados
    private void pensar() throws InterruptedException {
        System.out.println("Filosofo " + id + " está pensando.");  // Imprime mensagem indicando que o filósofo está pensando.
        Thread.sleep((long) (Math.random() * 1000));  // Usa Thread.sleep para simular um período de pensamento aleatório.(long é em milissegundos)
    }

    private void pegueGarfos() throws InterruptedException {
        garfoesquerdo.acquire();  // Tenta adquirir a permissao do semáforo do garfo esquerdo (bloqueia se não disponível).
        System.out.println("Filosofo " + id + " pega o garfo esquerdo.");  // Imprime mensagem indicando que o filósofo pegou o garfo esquerdo.
        garfodireito.acquire();  // Tenta adquirir o semáforo do garfo direito (bloqueia se não disponível).
        System.out.println("Filosofo " + id + " pega o garfo direito.");  // Imprime mensagem indicando que o filósofo pegou o garfo direito.
    }

    private void comer() throws InterruptedException {
        System.out.println("Filosofo " + id + " está comendo.");  // Imprime mensagem indicando que o filósofo está comendo.
        Thread.sleep((long) (Math.random() * 1000));  // Simula um período de alimentação (atraso aleatório).
    }

    private void abaixarGarfos() {
        garfoesquerdo.release();  // Libera o semáforo do garfo esquerdo.
        System.out.println("Filosofo " + id + " abaixa o garfo esquerdo.");  // Imprime mensagem indicando que o filósofo colocou o garfo esquerdo de volta.
        garfodireito.release();  // Libera o semáforo do garfo direito.
        System.out.println("Filosofo " + id + " abaixa o garfo direito.");  // Imprime mensagem indicando que o filósofo colocou o garfo direito de volta.
    }
}
