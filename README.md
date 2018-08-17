# Curve
贝塞尔曲线的滑动条
这是我直接写的一给自定义控件
首先看看他的效果<br>
![图片1](https://github.com/zengwei123/Curve/blob/master/image/GIF.gif)
![图片2](https://github.com/zengwei123/Curve/blob/master/image/GIF1.gif)<br>
有这两种样子曲线的和直线的，下面说使用这个控件的方法
有这么几个属性
![图片3](https://github.com/zengwei123/Curve/blob/master/image/1.png)
最大值：不能小于1，不能大于100
app:MaxNum="88"
最小值：不能小于0，不能大于100，可以与比最大值大这样显示的数组就是递减
app:MinNum="20"
图片的大小：我设置了为屏幕宽度的1/7，当然你也可以自己来设置
 app:Radii="100"
 滑动按钮的图片设置
 app:Image="@mipmap/but_g1"
 曲线(true)还是直线(false)：
 app:Is="false"
 字体的大小
 app:TextSize="50"
 
 线条的底色color1
 填充颜色color3
 字体颜色color2
 
 还有一个回调方法分别是按下，移动，抬起
 ![图片4](https://github.com/zengwei123/Curve/blob/master/image/2.png)
 ![图片5](https://github.com/zengwei123/Curve/blob/master/image/3.png)
 
 嗯，还有一个获取值的方法
 ![图片6](https://github.com/zengwei123/Curve/blob/master/image/6.png)
 
 东西就这么多东西，如果想设置线条的宽度自己去看代码改就行，如果想要其他乱七八糟的形状的，改贝塞尔曲线的公式就行，看代码就行，就一个类，200多行很容易懂的
