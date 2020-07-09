package com.xunqi.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xunqi.common.utils.R;
import com.xunqi.gulimall.seckill.feign.CouponFeignService;
import com.xunqi.gulimall.seckill.feign.ProductFeignService;
import com.xunqi.gulimall.seckill.service.SeckillService;
import com.xunqi.gulimall.seckill.to.SeckillSkuRedisTo;
import com.xunqi.gulimall.seckill.vo.SeckillSessionWithSkusVo;
import com.xunqi.gulimall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-09 19:29
 **/

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    private final String SESSION__CACHE_PREFIX = "seckill:sessions:";

    private final String SECKILL_CHARE_PREFIX = "seckill:skus";

    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";    //+商品随机码

    @Override
    public void uploadSeckillSkuLatest3Days() {

        //1、扫描最近三天的商品需要参加秒杀的活动
        R lates3DaySession = couponFeignService.getLates3DaySession();
        if (lates3DaySession.getCode() == 0) {
            //上架商品
            List<SeckillSessionWithSkusVo> sessionData = lates3DaySession.getData("data", new TypeReference<List<SeckillSessionWithSkusVo>>() {
            });
            //缓存到Redis
            //1、缓存活动信息
            saveSessionInfos(sessionData);

            //2、缓存活动的关联商品信息
            saveSessionSkuInfo(sessionData);
        }

    }

    /**
     * 缓存秒杀活动信息
     * @param sessions
     */
    private void saveSessionInfos(List<SeckillSessionWithSkusVo> sessions) {

        sessions.stream().forEach(session -> {

            //获取当前活动的开始和结束时间的时间戳
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();

            //存入到Redis中的key
            String key = SESSION__CACHE_PREFIX + session.getId() + ":" + startTime + "_" + endTime;

            //获取到活动中所有商品的skuId
            List<String> skuIds = session.getRelationSkus().stream()
                    .map(item -> item.getSkuId().toString()).collect(Collectors.toList());

            //缓存活动信息
            redisTemplate.opsForList().leftPushAll(key,skuIds);
        });

    }

    /**
     * 缓存秒杀活动所关联的商品信息
     * @param sessions
     */
    private void saveSessionSkuInfo(List<SeckillSessionWithSkusVo> sessions) {

        sessions.stream().forEach(session -> {
            //准备hash操作，绑定hash
            BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                Long skuId = seckillSkuVo.getSkuId();
                //缓存我们商品信息
                SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                //1、先查询sku的基本信息，调用远程服务
                R info = productFeignService.getSkuInfo(skuId);
                if (info.getCode() == 0) {
                    SkuInfoVo skuInfo = info.getData("skuInfo",new TypeReference<SkuInfoVo>(){});
                    redisTo.setSkuInfo(skuInfo);
                }

                //2、sku的秒杀信息
                BeanUtils.copyProperties(seckillSkuVo,redisTo);

                //3、设置当前商品的秒杀时间信息
                redisTo.setStartTime(session.getStartTime().getTime());
                redisTo.setEndTime(session.getEndTime().getTime());

                //4、设置商品的随机码（防止恶意攻击）
                String token = UUID.randomUUID().toString().replace("-", "");
                redisTo.setRandomCode(token);

                //5、使用库存作为分布式Redisson信号量（限流）
                RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + redisTo.getPromotionSessionId() + ":" + token);
                //商品可以秒杀的数量作为信号量
                semaphore.trySetPermits(seckillSkuVo.getSeckillCount());

                //序列化json格式存入Redis中
                String seckillValue = JSON.toJSONString(redisTo);
                operations.put(seckillSkuVo.getSkuId().toString(),seckillValue);
            });
        });

    }

}
