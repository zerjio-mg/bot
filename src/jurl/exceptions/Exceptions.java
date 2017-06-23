package jurl.exceptions;

public class Exceptions {


   public static void main(String[] args) {

      try {
         new Exceptions().jurl();
      } catch (Exception e) {
         System.out.printf("ERROR: %s\n", e.getMessage());
      }
   }

   public void jurl() {

      System.out.println("pim pam pum ...");

      throw new RuntimeException("JURL");
   }
}