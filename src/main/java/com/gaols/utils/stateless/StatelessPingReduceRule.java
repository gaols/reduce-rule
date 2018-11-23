package com.gaols.utils.stateless;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 本砍价规则默认为：前1/3的人砍掉总砍价金额的一半，剩余的2/3的人瓜分另外一半总砍价金额。
 */
public class StatelessPingReduceRule implements StatelessReduceRule {

    private DefaultStatelessReduceRule rule;

    public StatelessPingReduceRule() {
        this.rule = new DefaultStatelessReduceRule();
    }

    public BigDecimal getReduce(BigDecimal totalReduce, BigDecimal leftTotalReduce, int totalReduceTimes, int leftReduceTimes, int scale) {
        if (totalReduceTimes == 1) {
            return totalReduce;
        }

        int firstN = Math.max(totalReduceTimes / 3, 1); // 前1/3的人
        BigDecimal halfReduce = totalReduce.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN).setScale(scale, RoundingMode.HALF_EVEN);
        int leftN = totalReduceTimes - firstN;
        BigDecimal theOtherHalf = totalReduce.subtract(halfReduce);

        if (totalReduceTimes - leftReduceTimes < firstN) {
            // 前1/3的人砍掉一半
            BigDecimal leftTotal = halfReduce.subtract(totalReduce.subtract(leftTotalReduce));
            return rule.getReduce(halfReduce, leftTotal, firstN, firstN - (totalReduceTimes - leftReduceTimes), scale);
        } else {
            // 剩下的人砍掉一半
            return rule.getReduce(theOtherHalf, leftTotalReduce, leftN, leftReduceTimes, scale);
        }
    }

}
