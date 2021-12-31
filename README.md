# elastic-job-spring-annotation
elastic-job-spring support annotation

 # Required
     - Java 8 or above required.
     - elastic-job 3.0.1 +
     - spring freeemwork 5.0.0+

## Feature

- Spring-Boot

  >If your project depencies  elastic-job- spring - boot-start .such like 
  >
  >```
  ><dependency>
  >    <groupId>org.apache.shardingsphere.elasticjob</groupId>
  >    <artifactId>elasticjob-lite-spring-boot-starter</artifactId>
  >    <version>${elastic-job.version}</version>
  ></dependency>
  >```
  >
  >in you pom.xml.
  >
  >but you want to use annotation(@ElasticjobHandler) to config you job. It is all compatible.

-  Spring-nameSpace

  > If your project depencies  spring-name-space. Such like 
  >
  > ```
  > <dependency>
  >     <groupId>org.apache.shardingsphere.elasticjob</groupId>
  >       <artifactId>elasticjob-lite-spring-namespace</artifactId>
  >        <version>${elastic-job.version}</version>
  >     </dependency>
  > ```
  >
  >   in you pom.xml.
  >
  > but but you want to use annotation(@ElasticjobHandler) to config you job. It is all compatible.
  
-   support feishu notice(only support group robot notice)

  >  add depency to you pom.xml 
  >
  >  
  >
  > ```java
  >  <dependency>
  >             <groupId>com.mc.study</groupId>
  >              <artifactId>elastic-job-error-handler-feishu</artifactId>
  >             <version>${elastic-job.version</version>
  >         </dependency>
  > ```
  >
  > Use Annotation  suuch like 
  >
  > ```
  > @Component
  > @ElasticJobHandler(jobName = "simpleJob", corn = "0/25 * * * * ?", jobParameter = "my-job", shardingTotalCount = 1,
  >         shardingItemParameters = "0=change,1=Shanghai,2=Guangzhou", overWrite = true,
  >         jobErrorHandlerType = FlyBookPropertyConstants.HANDLER_ERROR_TYPE,
  >         jobProperties = {@ElasticJobProperty(key = FlyBookPropertyConstants.WEB_HOOK, value = "${feishu.webHook}"),
  >                 @ElasticJobProperty(key = FlyBookPropertyConstants.SECRET, value = "${feishu.secret}")
  >         }
  > )
  > public class SelfSimpleJob implements SimpleJob {
  > 
  >     private static int i = 0;
  > 
  >     @Override
  >     public void execute(ShardingContext shardingContext) {
  >         if (i > 1) {
  >             System.out.println("SelfDataflowJob int > 1 不能执行");
  >             throw new RuntimeException("SelfDataflowJob int > 1 can not run ");
  >         }
  >         i++;
  >         System.out.println("SelfSimpleJob,shardingContext = " + JSONObject.toJSONString(shardingContext));
  >         System.out.println("SelfSimpleJob, scheduled time :  " + LocalTime.now() + "thread info :" + Thread.currentThread().getName());
  >     }
  > ```
  >
  > Sometimes, you company has many project in some environment , so you can use `${your.address}` in annotation, config the real address to spring container.

### How to Use

1. Use  @EnableElasticjob to you configuration class

   1. ```java
      @SpringBootApplication
      @EnableElasticJob
      public class ElasticJobSpringBootApplication {
      
      	public static void main(String[] args) {
      		SpringApplication.run(ElasticJobSpringBootApplication.class, args);
      	}
      
      }
      ```

      

2.  Just use @ElasticJobHandler   to your job  bean.

   1. ```
      @Component
      @ElasticJobHandler(jobName = "simpleJob", corn = "0/25 * * * * ?", jobParameter = "my-job", shardingTotalCount = 2,
              shardingItemParameters = "0=change,1=Shanghai,2=Guangzhou", overWrite = true)
      public class SelfSimpleJob implements SimpleJob {
      
          @Override
          public void execute(ShardingContext shardingContext) {
              System.out.println("SelfSimpleJob,shardingContext = " + JSONObject.toJSONString(shardingContext));
              System.out.println("SelfSimpleJob, time info :  " + LocalTime.now() + "thread info :" + Thread.currentThread().getName());
          }
      }
      ```

      

    

​    

  

