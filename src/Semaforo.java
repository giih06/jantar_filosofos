class Semaforo {
    private int permits;

    public Semaforo(int initialPermits) {
        if (initialPermits < 0) {
            throw new IllegalArgumentException("Initial permits must be non-negative.");
        }
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notify();
    }
}

