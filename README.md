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
              System.out.println("SelfSimpleJob,定时任务执行, 现在的时间:  " + LocalTime.now() + "线程信息:" + Thread.currentThread().getName());
          }
      }
      ```

      

    

​    

  

