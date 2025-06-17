# dt-mongo-aspect

Aspect to add support for Mongo Reactive Streaming drivers.


* After building the aspect, you’ll have dt-mongo-aspect-1.0-SNAPSHOT.jar:.
 
 
* Change your startup arguments like this:
java  -javaagent:<aspectjpath>/aspectjweaver-1.9.24.jar -Xbootclasspath/a:<pathtoAspect>/dt-mongo-aspect-1.0-SNAPSHOT.jar:<aspectjpath>/aspectjrt-1.9.24.jar:<oneagensdkpath>/oneagent-sdk-1.9.0.jar <restofyoujvmargs>
 
 
* Here are the Maven dependencies of the aspectj*.jar  and the oneagent-sdk*.jar files above, which are also needed.
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.24<</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.9.24<</version>
        </dependency>
        <dependency>
            <groupId>com.dynatrace.oneagent.sdk.java</groupId>
            <artifactId>oneagent-sdk</artifactId>
            <version>1.9.0</version>
        </dependency>
 
