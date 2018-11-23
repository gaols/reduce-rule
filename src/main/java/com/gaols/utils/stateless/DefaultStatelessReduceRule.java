package com.gaols.utils.stateless;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class DefaultStatelessReduceRule implements StatelessReduceRule {

    @Override
    public BigDecimal getReduce(BigDecimal totalReduce, BigDecimal leftReduce, int totalReduceTimes, int leftReduceTimes, int scale) {
        if (leftReduceTimes <= 0) {
            throw new IllegalArgumentException("leftReduceTimes should gte 1");
        }
        if (leftReduceTimes == 1) {
            return leftReduce;
        }

        BigDecimal minReduce = totalReduce.divide(BigDecimal.valueOf(totalReduceTimes).multiply(BigDecimal.valueOf(2)), RoundingMode.HALF_EVEN);
        double min = minReduce.doubleValue();
        double max = leftReduce.doubleValue() - min * leftReduceTimes;
        double adjustedMax = min + (leftReduceTimes > 10 ? max / 3 : max);
        double ratio = new Random().nextDouble();
        return BigDecimal.valueOf(min + (adjustedMax - min) * ratio).setScale(scale, RoundingMode.HALF_EVEN);
    }

}
