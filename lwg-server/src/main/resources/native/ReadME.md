### java jni使用

#### 1. 创建java本地接口类
```
public class JniTest {
    public native int add(int x, int y);
}
```
#### 2. 生成对应的头文件
```
javah -classpath /Users/alonglamp/Desktop/other/tmp/LightweightGateway/target/classes com.scnu.lwg.jni.JniTest
```
   注：先编译java生成class; 类名为全名，包含包名。
#### 3. 根据头文件，编写对应的c文件
   
   xxx.c
   
   注：生成的头文件中有些参数没有形参
#### 4. 编译库文件
```
sudo gcc -fPIC -I /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/include -I /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/include/darwin/  -shared -o libadd.so add.c
```
   指定jni.h和jni_md.h路径在 jdk的include目录下
   生成的so库，在resources下的native中
#### 5. 加载库文件
```
static {
    URL url = Thread.currentThread().getContextClassLoader().getResource("native");
    String path = url.getPath();
    System.load(path+"/JniTest.so");
}
```
#### 6. 调用
   在需要使用的地方调用即可。
   
#### 7.gcc编译参数
-fPIC 编译jni调用库 不可直接执行
-I 指定头文件路径
-L 指定依赖库路径
-l 指定依赖库(去掉后缀，去掉前缀lib)
-o 指定生成文件名
gcc  -I ./  -shared -o JniTest.so JniTest.c  
