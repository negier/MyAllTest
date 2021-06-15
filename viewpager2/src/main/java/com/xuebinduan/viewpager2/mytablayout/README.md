TabLayout:
如果你想设置tabIndicator的宽度为不暂满TabView，只需要在布局文件中添加一个属性：app:tabIndicatorFullWidth="false"
同时，你的那个drawable，比如写的shape_xxx.xml中添加：
<size android:height="6dp"/>
最后的效果是：tabIndicator的宽度和那个文字的宽度一样宽，高度是6dp。