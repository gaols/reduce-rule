package com.gaols.utils.stateless;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class StatelessReduceRuleTest {

    @Test
    public void testReduceList() {
        for (int i = 0; i < 1000; i++) {
            int t = new Random().nextInt(20) + 1;
            int scale = 2;
            BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 200 + 1).setScale(scale, RoundingMode.HALF_UP);
            DefaultStatelessReduceRule rule = new DefaultStatelessReduceRule();
            BigDecimal reduced = BigDecimal.valueOf(0);
            for (int j = 0; j < t; j++) {
                BigDecimal current = rule.getReduce(tt, tt.subtract(reduced), t, t - j, scale);
                reduced = reduced.add(current);
            }
            Assert.assertEquals(reduced, tt);
        }
    }
}
