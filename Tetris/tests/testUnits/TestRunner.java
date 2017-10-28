package testUnits;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
	  int[] results = new int[3];
      Result result = JUnitCore.runClasses(BlockPieceTests.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println("--- Failures " + result.getFailureCount() + " ---");
      results[0] = result.getFailureCount();

      result = JUnitCore.runClasses(TetrisBlockTests.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println("--- Failures " + result.getFailureCount() + " ---");

      results[1] = result.getFailureCount();

      System.out.println(result.wasSuccessful());

      result = JUnitCore.runClasses(BlockDeletionTests.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println("--- Failures " + result.getFailureCount() + " ---");

      results[2] = result.getFailureCount();
      System.out.println(result.wasSuccessful());

      for(int i = 0 ; i<results.length ; i++){
    	  System.out.println("Fail count in case #" + i + ". : " + results[i]);
      }

	}
}
