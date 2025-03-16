package com.devd.spring.bookstorecommons.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: Devaraj Reddy, Date : 2019-07-02
 */
@FeignClient("honya-order-service")
public interface OrderFeignClient {


}
