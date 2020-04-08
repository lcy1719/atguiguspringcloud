package com.atguigu.springcloud.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Date;
/**
 * @author wsk
 * @date 2020/3/15 18:10
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***********come in MyLogGateWayFilter: "+new Date());
        //从地址栏的参数获得uname的值
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");//每次进来后判断带不带uname这个key
        //如果为空说明非法访问
        if(uname == null){
            log.info("*********用户名为null ，非法用户，o(╥﹏╥)o");
            //uname为null非法用户
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        //不为空就放行
        return chain.filter(exchange);
    }
    //加载过滤器的优先级，0是最高
    @Override
    public int getOrder() {
        return 0;
    }
}
//public class MyLogGateWayFilter{
//
//}