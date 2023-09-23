import java.util.concurrent.Semaphore;

public class JantarFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Filosofo[] filosofos = new Filosofo[numFilosofos];
        Semaphore[] forks = new Semaphore[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            forks[i] = new Semaphore(1); // Inicializa cada garfo com disponibilidade (1)
        }

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, forks[i], forks[(i + 1) % numFilosofos]);
            filosofos[i].start();
        }
    }
}

class Filosofo extends Thread {
    private int id;
    private Semaphore leftFork;
    private Semaphore rightFork;

    public Filosofo(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void run() {
        try {
            while (true) {
                think();
                pickUpForks();
                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Filosofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pickUpForks() throws InterruptedException {
        leftFork.acquire();
        System.out.println("Philosopher " + id + " picks up left fork.");
        rightFork.acquire();
        System.out.println("Philosopher " + id + " picks up right fork.");
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void putDownForks() {
        leftFork.release();
        System.out.println("Philosopher " + id + " puts down left fork.");
        rightFork.release();
        System.out.println("Philosopher " + id + " puts down right fork.");
    }
}