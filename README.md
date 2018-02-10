# Tools

------------

## 功能清单
1. [图片选择器](#图片选择器 "图片选择器")
2. [自动版本号](#自动版本号 "自动版本号")
3. [日志工具（发布时一键清理代码中log输出，避免数据泄露）](#日志工具 "日志工具（发布时一键清理代码中log输出，避免数据泄露）")
4. [崩溃日志自动记录器](#崩溃日志自动记录器 "崩溃日志自动记录器")
5. [文件工具包](#文件工具包 "文件工具包")

## 添加使用
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Mystery00:Tools:x.x.x'
	}
具体的版本号请进入release页查看
以下只是部分用法，具体用法请参考sample
## 自动版本号
本项目的自动版本号源代码来自于[https://github.com/nillith/AutoVersion](https://github.com/nillith/AutoVersion)，
我只是添加了beta和alpha字段，适用于自动生成如1.2.3-beta4或者1.2.3-alpha4的版本名称，版本号依旧是按照git的commit次数获取。

用法（除了上面的添加之外，继续在Project的build.gradle中添加以下代码）：

    buildscript {
        repositories {
            ……
            maven { url 'https://jitpack.io' }
    }
        dependencies {
            ……
            classpath 'com.github.Mystery00.Tools:autoversion:x.x.x'
    }
此处版本号跟随上面的ToolsDemo，然后继续在app的build.gradle中添加以下代码
    
       apply plugin: 'vip.mystery0.autoversion'
       autoVersion {
           major 1
           minor 2
           patch 3
           beta 4
           alpha 5
       }
这时，你可以通过autoVersion.name获取自动生成的版本名称（例如此处生成的是1.2.3-beta4，如果beta的值是0，则名称为1.2.3-alpha5，若beta和alpha都是0，则版本号为1.2.3，beta优先于alpha）
如下所示：

    versionCode autoVersion.code
    versionName autoVersion.name
## 图片选择器
```java
void setDataList(int defaultImage, iPictureChooserListener searchButtonOnClickListener)//设置监听并初始化图片选择按钮资源
setDataList(iPictureChooserListener searchButtonOnClickListener)//设置监听
List<String> getList()//获取选择的图片的路径的list
void setUpdatedPicture(Uri uri)//用于回调更新列表（此方法在选择回调中必须设置）
void setList(List<String> list)//初始化选择图片列表
```
## 日志工具
使用方法：在Application中调用`setLevel(LogLevel level)`方法，传参`Release`即是发布时使用，隐藏app中除了错误之外`log`，传参`Debug`则是编写过程中使用。
## 崩溃日志自动记录器
使用方法：在Application中调用以下代码即可。

    CrashHandler.getInstance(this).init();
配置方法：
```java
CrashHandler setDirectory(String name)//设置SD卡根目录下创建的文件夹名（默认log）
CrashHandler setPrefixName(String fileName)//设置log文件默认头部名（默认crash）
CrashHandler setExtensionName(String fileName)//设置log文件默认头部名（默认txt，不需要加点）
CrashHandler isAutoClean(boolean isAutoClean)//设置是否自动清理log文件
CrashHandler sendException(CrashHandler.CatchExceptionListener catchExceptionListener)//导出异常之后的回调，可在此处进行上传log文件的操作
void clean(AutoCleanListener autoCleanListener)//调用清理log文件的方法
```
## 文件工具包
```java
String getPath(Context context,Uri uri)//根据uri获取对应文件的路径
String FormatFileSize(long fileSize)//格式化文件大小的显示，long转换为String，默认采用两位小数
```