Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题。

单节点模式：

Spring Cloud Hystrix 实现了断路器、线路隔离等一系列服务保护功能。它也是基于 Netflix 的开源框架 Hystrix 实现的，该框架的目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix 具备服务降级、服务熔断、线程和信号隔离、请求缓存、请求合并以及服务监控等强大功能。

要确保被监控的服务打开了Actuator（依赖spring-boot-starter-actuator），开启了断路器（@EnableCircuitBreaker注解）

实心圆：颜色代表健康度，（绿-黄-红-橙递减）；大小代表并发量。

曲线：请求量的变化