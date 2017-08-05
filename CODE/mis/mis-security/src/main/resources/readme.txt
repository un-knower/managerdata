1、在需要使用shiro工程下的pom中引入
<dependency>
	<groupId>com.ufc</groupId>
	<artifactId>app-security</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>

2、在web.xml中加入如下过滤器

	<filter>
		<filter-name>shiroFilter</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>shiroFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
3、将src/main/resources下的permission文件夹复制到 web工程中的webapp下	
	
4、复写 com.ufc.shiro.service包下的 类 PermissionService 的下面方法
   public List<String> queryAllPermissionInfo(String username) 