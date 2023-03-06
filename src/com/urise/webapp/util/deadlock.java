package com.urise.webapp.util;

class Table {
}

public class deadlock {
    static final Table table1 = new Table();
    static final Table table2 = new Table();
    static public void algoritm1() {
        synchronized (table1) {
            System.out.println("Алгоритм 1 |Работа с таблицей 1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (table2) {
                System.out.println("Алгоритм 1 |Работа с таблицей 1");
            }
        }
    }

    static public void algoritm2() {
        synchronized (table2) {
            System.out.println("Алгоритм 2 | Работа с таблицей 2");
            synchronized (table1) {
                System.out.println("Алгоритм 2 | Работа с таблицей 1");
            }
        }

    }

    public static void main(String[] args) {
        new Thread(deadlock::algoritm1).start();
        new Thread(deadlock::algoritm2).start();
    }
}
