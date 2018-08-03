Activiti是Apache许可的业务流程管理（BPM）引擎。

1.当流程开始时，求职者的简历存储在外部系统中。
2.然后，该过程一直等到进行电话采访。这是由用户完成的
3.如果电话采访没有通过，则发送礼貌拒绝电子邮件。否则，技术面试和财务谈判都应该发生。
4.请注意，在任何时候，申请人都可以取消。这在图中显示为大矩形边界上的事件。当事件发生时，内部的所有内容都将被终止并且进程停止。
5.如果一切顺利，将发送欢迎电子邮件。


Spring Boot Activiti Modeler搭建过程中注意事项：
1.org.venux.activiti.rest.editor.main.StencilsetRestResource.java中stencilset.json加载方式调整
	
2.汉化
	 将stencilset.json文件汉化