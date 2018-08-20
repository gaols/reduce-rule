## 砍价规则

受简书[拼多多砍价算法实现探究](https://www.jianshu.com/p/e16ac43a4c89)启发，写了Java版本的拼多多砍价规则。

## 使用说明

假如可砍价的总金额为100，需要砍价10次，前5个人可砍掉70%。

```java
PingDuoDuoReduceRule pingRule = new PingDuoDuoReduceRule(70, 5);
pingRule.getReduceList(100, 10);
```
