package info.sroman;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void utils_getRandomEntityClass_allClassesReturnedInFifteenCalls() {
        List<Class<?>> entityClasses = Lists.newArrayList(Utils.getEntityClasses());
        int[] counts = new int[entityClasses.size()];
        Arrays.fill(counts, 0);
        List<String> randomEntityNames = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            randomEntityNames.add(Utils.getRandomEntityClass().getSimpleName());
        }

        for (int c = 0; c < entityClasses.size(); c++) {
            for (int i = 0; i < randomEntityNames.size(); i++) {
                if (randomEntityNames.get(i).equalsIgnoreCase(entityClasses.get(c).getSimpleName())) {
                    counts[c]++;
                }
            }
        }

        // no more than 100% variance in each class name returned
        int maxVariance = 100 / counts.length;
        int max = 0;
        int min = 99;

        for (int mac: counts)
            max = Math.max(mac, max);

        for (int mic: counts)
            min = Math.min(mic, min);

        assertTrue((max - min) < maxVariance);
   }
}
