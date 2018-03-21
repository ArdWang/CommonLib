# CommonLib
## 常用的时间获取
### gradle导入

添加到项目的dependency
```Java
	dependencies {
	        compile 'com.github.ArdWang:CommonLib:v1.0.2'
	}
```


添加到项目的build.gradle

```Java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### maven导入
添加到项目的dependency
```Java
	<dependency>
	    <groupId>com.github.ArdWang</groupId>
	    <artifactId>CommonLib</artifactId>
	    <version>v1.0.2</version>
	</dependency>
	
```
```Java
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

### 使用方式
1.获取当前的系统时间
```Java
	//设置要转换的时间格式->得到当前的时间
 	String currentDate = "yyyy-MM-dd HH:mm:ss";
        String a = DateUtil.getCurrentDate(currentDate);
        Log.i(TAG,a);
```
打印Log：TAG，2018-3-21 08:55:34;<br/>


2.将Long类型时间转为字符格式时间
```Java
	String currentDate = "yyyy-MM-dd HH:mm:ss";	
	Long time = 1555232343000;
        String a1 = DateUtil.getLongToString(time,currentDate);
        Log.i(TAG,a1);
```
打印Log:TAG,2019-10-13 12:33:44;<br/>

3.将字符转为Long类型
```Java
	String currentDate = "yyyy-MM-dd HH:mm:ss";
	String date = "2018-12-15 18:22:55";
        Long a2 = DateUtil.getStringToLong(date,currentDate);
        Log.i(TAG,a2+"");
```
打印Log:TAG,1554232343000;<br/>

4.输入年月日显示当前星期几
```Java
	int week = DateUtil.getDayWeek(2018,3,20);
        Log.i(TAG,week+"");
```
1.星期日 2.星期一....依次类推<br/>
打印Log:TAG,3;<br/>

5.将没有格式化的字符类型转为日期类型
```Java
	String a3 ="20180320083034";
        try {
            String currentDate1 = "yyyy-MM-dd HH:mm:ss";
            Date dd = DateUtil.parseStringToDate(a3);
            String dateString = DateUtil.getDateToString(dd,currentDate1);
            Log.i(TAG,dateString+"");
        }catch (Exception e){
            e.printStackTrace();
        }
	
	//或这种类型
	String a3 ="20180320 083034";
        try {
            String currentDate1 = "yyyy-MM-dd HH:mm:ss";
            Date dd = DateUtil.parseStringToDate(a3);
            String dateString = DateUtil.getDateToString(dd,currentDate1);
            Log.i(TAG,dateString+"");
        }catch (Exception e){
            e.printStackTrace();
        }
```
打印的值为 Log:TAG,2018-03-20 08:30:34;<br/>


