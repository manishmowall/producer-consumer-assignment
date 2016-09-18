package org.nitishm.producerconsumer;

import java.util.Vector;

class Consumer implements Runnable {
   private final Vector<Integer> sharedQueue;
   private final int size;

   public Consumer(Vector<Integer> sharedQueue, int size) {
      this.sharedQueue = sharedQueue;
      this.size = size;
   }

   @Override
   public void run() {
      while (true) {
         try {
            System.out.println("Consumed: " + consume());
            Thread.sleep(50);
         } catch (InterruptedException ex) {
            System.out.println(ex.getStackTrace());
         }

      }

   }

   private int consume() throws InterruptedException {
      //wait if queue is empty
      while (sharedQueue.isEmpty()) {
         synchronized (sharedQueue) {
            System.out.println("Queue is empty, " + Thread.currentThread().getName() + " is waiting , size: " + sharedQueue.size());

            sharedQueue.wait();
         }
      }

      //Otherwise consume element and notify waiting producer
      synchronized (sharedQueue) {
         sharedQueue.notify();
         return (Integer) sharedQueue.remove(0);
      }
   }


}
