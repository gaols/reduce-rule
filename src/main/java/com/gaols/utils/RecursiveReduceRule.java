package com.gaols.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 砍价的几个因素：
 * 1. 可砍价的人数(N)
 * 2. 可砍掉的总金额(T)
 * 3. 每次砍价的最低砍价金额(M)
 * 4. 总砍价次数(K)
 * <p>
 * 本砍价规则将'每次砍价的最低砍价金额'默认为 := T / (K * 2)。
 *
 * 本砍价规则只是{@link com.gaols.utils.SfReduceRule}的递归版本。
 */
public class RecursiveReduceRule implements ReduceRule {

    private final int scale;

    public RecursiveReduceRule(int scale) {
        this.scale = scale;
    }

    @Override
    public List<BigDecimal> getReduceList(BigDecimal totalReduce, int totalReduceTimes) {
        if (totalReduceTimes <= 0) {
            throw new IllegalArgumentException("total reduce times should gte 1");
        }

        // 最低砍价金额默认为： 总砍价金额 / (砍价次数 * 2)
        BigDecimal minReduce = totalReduce.divide(BigDecimal.valueOf(totalReduceTimes).multiply(BigDecimal.valueOf(2)), BigDecimal.ROUND_HALF_EVEN);
        return getReduceList(totalReduce, totalReduceTimes, minReduce);
    }

    public List<BigDecimal> getReduceList(BigDecimal totalReduce, int totalReduceTimes, BigDecimal minReduce) {
        List<BigDecimal> ret = new ArrayList<>();
        doReduce(totalReduce, totalReduceTimes, minReduce, ret);
        return ret;
    }

    private void doReduce(BigDecimal leftTotalReduce, int leftReduceTimes, BigDecimal minReduce, List<BigDecimal> vs) {
        if (leftReduceTimes <= 0) {
            return;
        } else if (leftReduceTimes == 1) {
            vs.add(leftTotalReduce);
            return;
        }
        double min = minReduce.doubleValue();
        double max = leftTotalReduce.doubleValue() - min * leftReduceTimes;
        double adjustedMax = min + (leftReduceTimes > 10 ? max / 3 : max);
        double ratio = new Random().nextDouble();
        BigDecimal current = BigDecimal.valueOf(min + (adjustedMax - min) * ratio).setScale(this.scale, RoundingMode.HALF_EVEN);
        vs.add(current);
        doReduce(leftTotalReduce.subtract(current), --leftReduceTimes, minReduce, vs);
    }
}
