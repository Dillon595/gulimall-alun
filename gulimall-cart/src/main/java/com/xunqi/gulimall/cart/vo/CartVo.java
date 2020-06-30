package com.xunqi.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 整个购物车存放的商品信息   需要计算的属性需要重写get方法，保证每次获取属性都会进行计算
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-30 16:42
 **/

public class CartVo {

    /**
     * 购物车子项信息
     */
    List<CartItemVo> items;

    /**
     * 商品数量
     */
    private Integer countNum;

    /**
     * 商品类型数量
     */
    private Integer countType;

    /**
     * 商品总价
     */
    private BigDecimal amount = new BigDecimal("0");

    /**
     * 减免价格
     */
    private BigDecimal reduce;

    public List<CartItemVo> getItems() {
        return items;
    }

    public void setItems(List<CartItemVo> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItemVo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItemVo item : items) {
                count += 1;
            }
        }
        return count;
    }


    public BigDecimal getAmount() {
        BigDecimal count = BigDecimal.ZERO;
        //计算购物项总价
        if (items != null && items.size() > 0) {
            for (CartItemVo item : items) {
                count = count.add(item.getTotalPrice());
            }
        }
        //总价格减去商品优惠价格
        return count.subtract(getReduce());
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
