package com.xunqi.gulimall.auth.feign;

import com.xunqi.common.utils.R;
import com.xunqi.gulimall.auth.vo.UserLoginVo;
import com.xunqi.gulimall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-27 17:10
 **/

@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping(value = "/member/member/register")
    R register(@RequestBody UserRegisterVo vo);


    @PostMapping(value = "/member/member//login")
    R login(@RequestBody UserLoginVo vo);

}
