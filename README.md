# cache-check
A cache check frame.

## TODO

1. 增量比较应该只比较复杂结构中的部分冲突字段，而不是全量比较key
    [1] string 和 list 每次都要重新比较所有field value。list有lpush、lpop，会导致field value平移，所以需要重新比较所有field value。
    [2] hash、set、zset, 只比较前一轮有不一致的field。
2. 不支持redis stream结构比较
