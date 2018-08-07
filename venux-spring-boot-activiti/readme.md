Activiti是Apache许可的业务流程管理（BPM）引擎。

Spring Boot 集成Activiti Modeler
1.SpringBoot 1.3.5.RELEASE + activiti 5.22.0
   在acitiviti官网下载完整包https://github.com/Activiti/Activiti/releases/tag/activiti-5.22.0

2.将Activiti-activiti-5.22.0\modules\activiti-webapp-explorer2\src\main\webapp下的diagram-viewer、editor-app以及modeler.html文件放置在项目resources\static文件夹下。

3.将Activiti-activiti-5.22.0\modules\activiti-webapp-explorer2\src\resources下的stencilset.json放置在项目resources\static文件夹下。

4.将Activiti-activiti-5.22.0\modules\activiti-modeler\src\main\java\org\activiti\rest\editor下的main以及model中的java文件放置到项目mian\java目录下

5.首先将ModelEditoeJsonRestResource.java、ModelSaveRestResource.java、StencilsetRestResource.java上添加 @RequestMapping(value = "/service")

6.修改resources\static\editor-app\app-cfg.js 
  contextRoot值修改为/service

7。访问http://localhost:8080/modeler.html，发现页面未显示内容，这是因为目前还未创建任何model 
	创建ActivitiModelController.java

8.访问路径
通过访问http://localhost:8080/create创建一个空白的model并跳转到编辑页面
在绘制流程完成后，访问http://localhost:8080/deploy?modelId=1 对该流程进行部署
http://localhost:8080/start?keyName=hello 启动流程
http://localhost:8080/run?processInstanceId=1 提交

注意：
1.org.venux.activiti.rest.editor.main.StencilsetRestResource.java中stencilset.json加载方式调整
2.汉化
	 将stencilset.json文件汉化