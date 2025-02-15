package de.dagere.kopeme.junit5;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.io.FileMatchers;
import org.junit.jupiter.api.Test;

import de.dagere.kopeme.datastorage.XMLDataLoader;
import de.dagere.kopeme.generated.Kopemedata;
import de.dagere.kopeme.junit5.exampletests.rules.ExampleBeforeWithMeasurementOrderTest;
import de.dagere.kopeme.junit5.exampletests.rules.ExamplePackageVisibilityTest;
import de.dagere.kopeme.junit5.exampletests.rules.JUnit5SetupTest;

/**
 * Tests just whether JUnit 5 execution works
 * 
 * @author reichelt
 * 
 */
public class TestJUnit5SetupExecution {

   public static Logger log = LogManager.getLogger(TestJUnit5SetupExecution.class);

   @Test
   public void testRegularExecution() throws JAXBException {
      File file = JUnit5RunUtil.runJUnit5Test(JUnit5SetupTest.class);

      MatcherAssert.assertThat("File " + file.getAbsolutePath() + " did not exist", file, FileMatchers.anExistingFile());

      Kopemedata data = XMLDataLoader.loadData(file);
      double averageDurationInMs = data.getTestcases().getTestcase().get(0).getDatacollector().get(0).getResult().get(0).getValue() / 1000000;
      System.out.println(file.getAbsolutePath() + "=" + averageDurationInMs);

      MatcherAssert.assertThat((int) averageDurationInMs, Matchers.greaterThan(900));
      MatcherAssert.assertThat((int) averageDurationInMs, Matchers.lessThan(1300));
   }
   
   @Test
   public void testPackageVisibleExecution() throws JAXBException {
      File file = JUnit5RunUtil.runJUnit5Test(ExamplePackageVisibilityTest.class);

      MatcherAssert.assertThat("File " + file.getAbsolutePath() + " did not exist", file, FileMatchers.anExistingFile());

      Kopemedata data = XMLDataLoader.loadData(file);
      double averageDurationInMus = data.getTestcases().getTestcase().get(0).getDatacollector().get(0).getResult().get(0).getValue() / 1000;
      System.out.println(file.getAbsolutePath() + "=" + averageDurationInMus);

      MatcherAssert.assertThat((int) averageDurationInMus, Matchers.greaterThan(90));
   }

   @Test
   public void testBeforeWithMeasurement() throws JAXBException {
      File file = JUnit5RunUtil.runJUnit5Test(ExampleBeforeWithMeasurementOrderTest.class);

      MatcherAssert.assertThat("File " + file.getAbsolutePath() + " did not exist", file, FileMatchers.anExistingFile());

      Kopemedata data = XMLDataLoader.loadData(file);
      double averageDurationInMs = data.getTestcases().getTestcase().get(0).getDatacollector().get(0).getResult().get(0).getValue() / 1000000;
      System.out.println(file.getAbsolutePath() + "=" + averageDurationInMs);

      MatcherAssert.assertThat((int) averageDurationInMs, Matchers.greaterThan(50));
      MatcherAssert.assertThat((int) averageDurationInMs, Matchers.lessThan(1500));
   }
   
}
