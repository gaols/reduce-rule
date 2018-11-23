package com.gaols.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class RecursiveReduceRuleTest {

    @Test
    public void testReduceList() {
        for (int i = 0; i < 1000; i++) {
            int t = new Random().nextInt(100) + 1;
            BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 10000 + 1).setScale(2, RoundingMode.HALF_UP);
            List<BigDecimal> reduceList = new RecursiveReduceRule(2).getReduceList(tt, t);
            BigDecimal total = new BigDecimal(0d);
            for (BigDecimal d : reduceList) {
                total = total.add(d);
            }
            total = total.setScale(2, RoundingMode.HALF_UP);
            Assert.assertEquals(total, tt);
        }
    }
}
