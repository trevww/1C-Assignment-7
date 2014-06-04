// CS1C, Assignment 7, Trevor Wineman, 20181253
import java.text.*;
import java.util.Locale;

import cs_1c.*;

public class Foothill 
{
   public static < E extends Comparable< ? super E > >
   void shellSortX( E[] a, int[] gapSeq )
   {
      int k, pos, arraySize, counter, gap;
      E tmp;
       
      arraySize = a.length;
      counter = gapSeq.length-1;

      while ( counter >= 0)
      {
         gap = gapSeq[counter];
         
         for(pos = gap; pos < arraySize; pos++ )
         {
            tmp = a[pos];
            for(k = pos; k >= gap && tmp.compareTo(a[k-gap]) < 0; k -= gap )
               a[k] = a[k-gap];
            a[k] = tmp;
         }
         counter--;
      }
   }

   public static void main(String[] args) 
   {
      final int ARRAY_SIZE = 200000;
      int k, h, randInt;
      long startTime, stopTime;
      // for formatting output neatly
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      Integer[] arrayOfInts1 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts2 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts3 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts4 = new Integer[ARRAY_SIZE];

      // Shell's gap sequence --------------------------------
      int[] gapArray = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
            2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
            1048576};
      // Sedgewick's gap sequence --------------------------------
      int[] sedgewickArray = new int[30]; // to be computed using formulas
      for ( k = 0; k < sedgewickArray.length; k++)
      {
         if ( k%2 == 0)
            sedgewickArray[k] = 
               (int)(( 9*Math.pow(2,k) ) - ( 9* Math.pow(2, k/2) ) + 1 );
         else 
            sedgewickArray[k] = 
               (int)( 8*Math.pow(2,k) - 6*Math.pow(2,( (k+1)/2 )) + 1 );
      }
      // Knuth's gap sequence --------------------------------
      int[] knuthArray = new int[15];
      h = 1;
      for ( k = 0; k < knuthArray.length; k++)
      {
         knuthArray[k] = h;
         h = 3*h + 1;
      }
      
      // fill all arrays with same random values
      for (k = 0; k < ARRAY_SIZE; k++)
      {
         randInt = (int)( ARRAY_SIZE * Math.random() );
         arrayOfInts1[k] = randInt;
         arrayOfInts2[k] = randInt;
         arrayOfInts3[k] = randInt;
         arrayOfInts4[k] = randInt;
      }

      // shellSort1()
      startTime = System.nanoTime();                       // START 
      FHsort.shellSort1(arrayOfInts1);
      stopTime = System.nanoTime();                        // STOP
      System.out.println("shellSort1() Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");
      
      // Shell's gap
      startTime = System.nanoTime();                       // START 
      shellSortX(arrayOfInts2, gapArray);
      stopTime = System.nanoTime();                        // STOP
      System.out.println("Shell's Gap Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");
      
      // Sedgewick's gap
      startTime = System.nanoTime();                       // START 
      shellSortX(arrayOfInts3, sedgewickArray);
      stopTime = System.nanoTime();                        // STOP
      System.out.println("Sedgewick's Gap Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");

      // Knuth's gap
      startTime = System.nanoTime();                       // START 
      shellSortX(arrayOfInts4, knuthArray);
      stopTime = System.nanoTime();                        // STOP
      System.out.println("Knuth's Gap Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");
   }
}

/* ------------------------- RESULTS -----------------------------------------
 *  
 *           shellSort1()      Shell's      Sedgewick's      Knuth's
 *   
 *    10K      0.0396 s        0.0716s       0.0034s         0.0033s
 *   
 *    20K      0.0411 s        0.0739s       0.0073s         0.0057s
 * 
 *    50K      0.0828 s        0.145s        0.0381s         0.0302s
 * 
 *   100K      0.1142 s        0.2272s       0.0939s         0.0839s
 * 
 *   150K      0.2522 s        0.4502s       0.1507s         0.1272s
 *   
 *   200K      0.3302 s        0.6051s       0.215s          0.1872s
 * 
 *   Why does Shell's gap sequence implied by shellSort1() give a different 
 *   timing result than the explicit array described above and passed 
 *   to shellSortX()? 
 *    
 *   The extra book keeping for the array adds additional complexity along 
 *   with actually accessing the array. We are adding array access plus
 *   a constant time subtraction equation for each outside loop pass.
 *   
 *   Which is faster and why?
 *   
 *   shellSort1() is faster because it seems basic integer division can be done 
 *   faster in the for loop than the time it takes to access an array element 
 *   even though array access is very fast ( O(1) ).
 *   
 * -------------------------------------------------------------------------*/
