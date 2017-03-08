# ToolsDemo

------------

## 功能清单
1. [浮动按钮菜单](#图片选择器 "浮动按钮菜单")
2. 自定义下拉列表
3. 图片选择器
4. 网络框架（封装json解析）
5. 日志工具（发布时一键清理代码中log输出，避免数据泄露）
6. 崩溃日志自动记录器

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
	        compile 'com.github.Mystery00:ToolsDemo:1.1.0'
	}
## 浮动按钮菜单
```java
void setNumber(int number)//设置菜单项数量（最大为5）
void setIcon(int resId)//设置菜单图标
void setIcons(int[] ids)//设置菜单项图标（和数量匹配）
void setMenuVisibility(int visibility)//设置菜单可见性
void setMenuClickListener(MenuClick menuClick)//设置菜单项点击监听
```
## 自定义下拉列表
```java
void setStrings(String[] strings)//设置列表数据
void setSelected(int position)//设置默认选中
int getIndex()//获取选中的项
void setListBackground(int color)//设置列表背景
void setViewBackground(int color)//设置图罩背景
ListView getList()//获取列表对象
boolean isOpen()//获取列表是否显示
void setLayoutVisiblity(int visibility)//设置列表可见性
void setOnItemClickListener(SpinnerItemClickListener spinnerItemClickListener)//列表项点击监听
```
## 图片选择器
```java
void setDataList(int defaultImage, iPictureChooserListener listener)//设置监听并初始化图片选择按钮资源
setDataList(iPictureChooserListener listener)//设置监听
List<String> getList()//获取选择的图片的路径的list
void setUpdatedPicture(Uri uri)//用于回调更新列表（此方法在选择回调中必须设置）
void setList(List<String> list)//初始化选择图片列表
```
## 网络框架
```java
HttpUtil setRequestMethod(RequestMethod requestMethod)//设置请求方式
HttpUtil setUrl(String url)//设置请求地址
HttpUtil setResponseListener(ResponseListener responseListener)//数据返回监听
HttpUtil setMap(Map<String, String> map)//设置网页输入数据
void open()//开始链接
<T> T fromJson(String json, Class<T> classOfT)//解析json数据
String toJson(Object object)//封装json数据
```
## 日志工具
使用方法：在Application中调用`setLevel(LogLevel level)`方法，传参`Release`即是发布时使用，隐藏app中除了错误之外`log`，传参`Debug`则是编写过程中使用。
## 崩溃日志自动记录器
使用方法：在Application中调用以下代码即可。

    CrashHandler.getInstance().init(this);
配置方法：
```java
CrashHandler setDirectory(String name)//设置SD卡根目录下创建的文件夹名（默认log）
CrashHandler setCustomFileName(String fileName)//设置log文件默认头部名（默认crash）
```
