package org.nitishm.producerconsumer;

import java.util.Vector;

public class Application {
   public static void main(String args[]) {

      Vector sharedQueue = new Vector();
      final int size = 4;

      Thread producerThread = new Thread(new Producer(sharedQueue, size), "Producer");
      Thread consumerThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
      producerThread.start();
      consumerThread.start();

      try {
         producerThread.join();
         consumerThread.join();
      } catch (InterruptedException ex) {
         System.out.println(ex.getStackTrace());
      }
   }
}

