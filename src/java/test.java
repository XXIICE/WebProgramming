/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ariya boonchoo
 */
public class test {
    public static void main(String[] args) {
//       String[] search={"love","pop"};
//        for (int i = 0; i < search.length; i++) {
//            System.out.println(search[i]+i);
//        }
  double[] myList = {1.9, 2.9, 3.4, 3.5};
   double max = myList[0];
      for (int i = 1; i < myList.length; i++) {
         if (myList[i] > max) max = myList[i];
      }
      System.out.println("Max is " + max);  
    }
}