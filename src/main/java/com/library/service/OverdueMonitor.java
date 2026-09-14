package com.library.service;

public class OverdueMonitor extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {

        System.out.println(
                "\n[Monitor] Overdue book monitor started."
        );

        int checkCount = 1;

        while (running && checkCount <= 3) {

            System.out.println(
                    "[Monitor] Checking overdue books... "
                            + "Check #" + checkCount
            );

            try {

                Thread.sleep(2000);

            } catch (InterruptedException e) {

                System.out.println(
                        "[Monitor] Monitor interrupted."
                );

                Thread.currentThread().interrupt();

                break;
            }

            checkCount++;
        }

        System.out.println(
                "[Monitor] Overdue monitor stopped."
        );
    }

    public void stopMonitor() {

        running = false;

        interrupt();
    }
}