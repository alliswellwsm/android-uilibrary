# android-uilibrary

alliswellwsm创建的UI库
包括：<br/>
1）BaseToobarActivity 以Toolbar作为标题栏（ActionBar）的自定义Activity，标题居中显示，类似于苹果的标题栏


Usage
--------

Maven:
```xml
<dependency>
  <groupId>android.alliswell.wsm.uilibrary</groupId>
  <artifactId>android-uilibrary</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```
compile 'android.alliswell.wsm.uilibrary:android-uilibrary:1.0.0'
```

1）BaseToobarActivity
第一步：继承Activity
```
public class MainActivity extends BaseToobarActivity
```

第二步：设置Acitivity主题必须为NoActionBar
```xml
<style name="ActivityTheme" parent="Theme.AppCompat.Light.NoActionBar">
  <!-- Customize your theme here. -->
  <item name="colorPrimary">@color/colorPrimary</item>
  <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
  <item name="colorAccent">@color/colorAccent</item>
</style>
```
