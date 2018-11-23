package com.gaols.utils.stateless;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class StatelessPingReduceRuleTest {

    @Test
    public void testReduceList() {
        for (int i = 0; i < 1000; i++) {
            int t = new Random().nextInt(20) + 1;
            int scale = new Random().nextInt(3);
            BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 10000 + 1).setScale(scale, RoundingMode.HALF_UP);
            StatelessPingReduceRule rule = new StatelessPingReduceRule();
            BigDecimal reduced = BigDecimal.valueOf(0);
            for (int j = 0; j < t; j++) {
                BigDecimal current = rule.getReduce(tt, tt.subtract(reduced), t, t - j, scale);
                reduced = reduced.add(current);
            }
            Assert.assertEquals(reduced, tt);
        }
    }
}
