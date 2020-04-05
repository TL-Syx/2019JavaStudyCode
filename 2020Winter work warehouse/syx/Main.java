import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public static void main(String[] args) {
        JFrame jf=new JFrame("贪吃蛇");//获取窗体
        Container container=jf.getContentPane();//获取容器
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);//设置关闭方式
        jf.setBounds(20,20,910,720);//设置窗体的大小
        jf.setResizable(false);//设置不可更改窗体大小
        container.add(new MyPanel());//调用并且向窗体中添加MyPanel
        jf.setVisible(true);//设置窗体可见
    }
}
