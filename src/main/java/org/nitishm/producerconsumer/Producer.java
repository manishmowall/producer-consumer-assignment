package org.nitishm.producerconsumer;

import java.util.Vector;

class Producer implements Runnable {
   private final Vector<Integer> sharedQueue;
   private final int size;

   public Producer(Vector<Integer> sharedQueue, int size) {
      this.sharedQueue = sharedQueue;
      this.size = size;
   }

   @Override
   public void run() {
      for (int i = 0; i < 7; i++) {
         System.out.println("Produced : " + i);

         try {
            produce(i);
         } catch (InterruptedException ex) {
            System.out.println(ex.getStackTrace());
         }
      }
   }

   private void produce(int element) throws InterruptedException {
      //wait if queue is full
      while (sharedQueue.size() == size) {
         synchronized (sharedQueue) {
            System.out.println("Queue is full, " + Thread.currentThread().getName() + " is waiting, size : " + sharedQueue.size());

            sharedQueue.wait();
         }
      }

      //producing element and notify consumers
      synchronized (sharedQueue) {
         sharedQueue.add(element);
         sharedQueue.notify();
      }
   }
}