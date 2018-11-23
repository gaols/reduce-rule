package com.gaols.utils.stateless;

import java.math.BigDecimal;

/**
 * 该接口表示无状态的砍价规则，每次调用只生成当次的砍价金额。
 * 记录总砍价次数、剩余的可砍价次数、总砍价金额以及剩余的可砍价金额是应用应该考虑的问题。
 */
public interface StatelessReduceRule {
    /**
     * 获取砍价列表。
     *
     * @param totalReduce      总共可砍价的金额
     * @param leftReduce       剩下的可砍价金额，首次调用时 totalReduce 应该等于 leftReduce
     * @param totalReduceTimes 总共可砍价的次数
     * @param leftReduceTimes  当前剩下的砍价次数，首次调用时 totalReduceTimes 应该等于 leftReduceTimes
     * @return 砍价列表
     */
    BigDecimal getReduce(BigDecimal totalReduce, BigDecimal leftReduce, int totalReduceTimes, int leftReduceTimes, int scale);
}
