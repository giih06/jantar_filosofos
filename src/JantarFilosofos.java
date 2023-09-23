public class JantarFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Filosofo[] filosofos = new Filosofo[numFilosofos];
        Semaforo[] garfos = new Semaforo[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Semaforo(1); // Inicializa cada garfo com disponibilidade (1)
        }

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % numFilosofos]);
            filosofos[i].start();
        }
    }
}

class Filosofo extends Thread {
    private int id;
    private Semaforo garfoesquerdo;
    private Semaforo garfofireito;

    public Filosofo(int id, Semaforo garfoesquerdo, Semaforo garfofireito) {
        this.id = id;
        this.garfoesquerdo = garfoesquerdo;
        this.garfofireito = garfofireito;
    }

    public void run() {
        try {
            while (true) {
                think();
                pickUpgarfos();
                eat();
                putDowngarfos();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Filosofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pickUpgarfos() throws InterruptedException {
        garfoesquerdo.acquire();
        System.out.println("Filósofo " + id + " pega o garfo esquerdo.");
        garfofireito.acquire();
        System.out.println("Filósofo " + id + " pega o garfo direito.");
    }

    private void eat() throws InterruptedException {
        System.out.println("Filósofo " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void putDowngarfos() {
        garfoesquerdo.release();
        System.out.println("Filósofo " + id + " abaixa o garfo esquerdo.");
        garfofireito.release();
        System.out.println("Filósofo " + id + " abaixa o garfo direito.");
    }
}
