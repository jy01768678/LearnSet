package com.lorin.spring4;

import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taodong on 16/2/1.
 * 从Spring4开始，Spring以Servlet3为进行开发，
 * 如果用Spring MVC 测试框架的话需要指定Servlet3兼容的jar包（因为其Mock的对象都是基于Servlet3的）。
 * 另外为了方便Rest开发，通过新的@RestController指定在控制器上，
 * 这样就不需要在每个@RequestMapping方法上加 @ResponseBody了。
 * 而且添加了一个AsyncRestTemplate ，支持REST客户端的异步无阻塞支持
 */
@RestController
public class UserController {
}
