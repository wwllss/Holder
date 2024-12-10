# holder View级别复用组件
## 集成

```java
root project --- build.gradle
dependencies {
	...
	//butterknife 插件
	classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0'
	//nav 插件
	classpath "zy.nav:register:$navLastestVersion"
}

module --- build.gradle
apply plugin: 'kotlin-kapt'
dependencies {
	implementation 'zy.holder:holder:1.0.0'
	kapt 'zy.holder:compiler:1.0.0'
}
kapt {
    arguments {
        arg("MODULE_NAME", project.name)
    }
}
```
**注：**
> butterknife插件与nav的插件为必需

##  使用
```java
@Holder(AModel.class)
public class AHolder extends BaseViewHolder<AModel> {

    public AHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onBindData(AModel data) {

    }
}

OneAdapter<Object> adapter = new OneAdapter<>();
adapter.register(AModel.class);
```
**注：**
> 由于Android主工程与Module之间R文件的编译区别
>> 1.如果在主工程中使用，`@Holder`注解的`layout`参数应使用`R.layout.xxx`
>> 
>> 2.反之，如果在Module中使用，该值请使用butterknife插件生成的`R2.layout.xxx`。