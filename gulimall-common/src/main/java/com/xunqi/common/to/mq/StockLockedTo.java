package com.xunqi.common.to.mq;

import lombok.Data;

/**
 * @Description: 发送到mq消息队列的to
 * @Created: with IntelliJ IDEA.
 * @author: 阿伦啊
 * @createTime: 2021-07-06 21:03
 **/

@Data
public class StockLockedTo {

    /** 库存工作单的id **/
    private Long id;

    /** 工作单详情的所有信息 **/
    private StockDetailTo detailTo;
}
