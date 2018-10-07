package week05;



import org.junit.Test;
import test.AbstractJUnitBase;
import java.util.Arrays;

import static org.junit.Assert.fail;

/**
 * This class tests how many times a side is rolled and check several conditions.
 *
 * @author Emmanuel Cruz
 *
 */

public class Week05JUnitTest extends AbstractJUnitBase {

    private static int[] facets = new int[7];
    private static int size = facets.length;
    private static int runs = 0;
    private static int newDiff;

    /**
     * This method create multiple Die instances and verify the rolls are unique
     * @return void
     */
    @Test
    public void testMultipleDie() {

        Die one;
        Die two;
        Die three;
        Die four;

        one = new Die(false);
        two = new Die(true);
        three = new Die(false);
        four = new Die(true);


        one.roll();
        three.roll();

        if (one.getNumber() == two.getNumber() && two.getNumber() == three.getNumber() && three.getNumber() == four.getNumber()) {
            fail("Duplicate rolls indicates random roll isn't working");
        }
    }

    /**
     * This method will run a large number of tests (at least 1000) and check that each facet (1-6)
     * has a value (non-zero) count, calculate the mean and standard deviation, and verify that the counts are evenly
     * distributed .
     * @return void
     */
    @Test
    public void testRandomDie() {
        String msg;
       int[] count = {1, 2, 3, 4, 5, 6};

        getCount(count);

        double one = (double) facets[1] / runs;
        double two = (double) facets[2] / runs;
        double three = (double) facets[3] / runs;
        double four = (double) facets[4] / runs;
        double five = (double) facets[5] / runs;
        double six = (double) facets[6] / runs;

        System.out.printf("Distribution:  %.2f, %.2f, %.2f, %.2f, %.2f, %.2f", one, two, three, four, five, six);
        System.out.printf("\n");

        System.out.printf("mean: %.2f, std dev: %.2f", calcMedian(), getStdDev());
        System.out.printf("\n");

        if (contains(count, 0) && betweenExclusive(calcMedian(), calcMedian(), count))
        {
            trace("Die failed to be randomly distributed");
            trace("A facet has a zero count");
            fail("Test Failed");
        }
        else if(contains(count, 0))
        {
            trace("A facet has a zero count");
            fail("Test Failed");
        }
        else if(betweenExclusive(calcMedian(), calcMedian(), count))
        {
            trace("Die failed to be randomly distributed");
            fail("Test Failed");
        }


    }

    /**
     * This method calculates the Median
     * @return double
     */
    public static double calcMedian() {
        Arrays.sort(facets);
        if (facets.length % 2 == 0)
            return (facets[(facets.length / 2) - 1] + facets[facets.length / 2]) / 2.0;
        return facets[facets.length / 2];
    }

    /**
     * This method calculates the standard deviation
     * @return double
     */
    public static double getStdDev() {
        return Math.sqrt(getVariance());
    }

    /**
     * This method calculates the variance
     * @return double
     */
    public static double getVariance() {
        double mean = calcMedian();
        double temp = 0;
        for (double a : facets)
            temp += (a - mean) * (a - mean);
        return temp / (size - 1);
    }


    /**
     * This method calculates the count for each facet
     * @return void
     * @param count int array as param
     */
    public static void getCount(int count[]) {
        runs = 1000;

        Die die = new Die(false);

        for (int i = 0; i < runs; i++) {
            die.roll();
            facets[die.getNumber()]++;

            if (die.getNumber() == 1) {
                count[0]++;
            } else if (die.getNumber() == 2) {
                count[1]++;
            } else if (die.getNumber() == 3) {
                count[2]++;
            } else if (die.getNumber() == 4) {
                count[3]++;
            } else if (die.getNumber() == 5) {
                count[4]++;
            } else if (die.getNumber() == 6) {
                count[5]++;
            }
        }

        System.out.printf("Counts: %d, %d, %d, %d, %d, %d", count[0], count[1], count[2], count[3], count[4], count[5]);
        System.out.printf("\n");
    }

    /**
     * This method checks if the count for the die facets is 0.
     * @return boolean
     * @param array, T object
     */
    public static <T> boolean contains(final int[] array, final T v) {
        for (final Integer e : array)
            if (e == v || v != null && v.equals(e))
                return true;

        return false;
    }

    /**
     * This method checks if the count for the die facets is 0.
     * @param min - parameter to subtract median minus stddev
     * @param max - parameter to add median plus stddev
     * @param count - int array
     * @return boolean
     */
    public static boolean betweenExclusive(double min, double max, int[] count)
    {
        min -= getStdDev();
        max += getStdDev();

        for(int i = 0; i < count.length; i++)
        {
            if(count[i] >= min && count[i] <= max)
            {
                return true;
            }
        }
        return false;
    }
}


