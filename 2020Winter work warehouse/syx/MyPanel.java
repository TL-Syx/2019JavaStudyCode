import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    //创建随机数对象
    Random ran = new Random();
    //引入图片
    ImageIcon head =new ImageIcon("src\\tou.png");//蛇头图片
    ImageIcon body =new ImageIcon("src\\shen.png");//身体图片
    ImageIcon food =new ImageIcon("src\\food.png");//食物图片
    int snake_length=3;//蛇的初始长度
    int x[]=new int[200];//蛇的x坐标数组
    int y[]=new int[200];//蛇的y坐标数组
    int foodx;//食物x坐标
    int foody;//食物y坐标
    int time=200;//蛇移动间隔
    boolean gamestart=false;//判断游戏是否开始
    boolean pause=false;//判断游戏是否暂停
    boolean gameover=false;//判断游戏是否失败
    boolean win=false;//判断游戏是否胜利
    Timer timer=new Timer(time,this);//计时器，当过了500MS后调用actionPerformed方法
    char snake_direction='r';//u d l r(上下左右)
    //无参构造 直接调用Snake进行初始化
    public MyPanel() {
        Snake();
        this.setFocusable(true);
        this.addKeyListener(this);
    }
    //绘画方法
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(0,172,232));//设置大背景颜色
        g.fillRect(0,80,900,600);//将规定区域涂黑，与标题区域隔离开作为游戏区
        g.setFont(new Font("宋体",Font.BOLD,30));//设置标题字体加粗以及大小
        g.setColor(new Color(127,255,170));//设置标题颜色
        g.drawString("屎一样的贪吃蛇",330,50);//画标题
        //显示分数
        if(gamestart==true) {
            g.setColor(new Color(255, 255, 0));
            g.setFont(new Font("宋体", 0, 15));
            g.drawString("当前蛇长:" + snake_length, 700, 25);
            g.drawString("蛇长50以取得胜利", 700, 40);
        }
        //暂停字体提示
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体",0,20));
        if(pause==false) {
            g.drawString("按P键以暂停游戏", 25, 25);
        }else if(pause==true){
            g.drawString("游戏已暂停，按P以继续",25,25);
        }
        if(gamestart==true) {
            timer.start();
            head.paintIcon(this, g, x[0], y[0]);//在窗体中画蛇头
            //循环画蛇身
            x[snake_length]=x[snake_length-1];
            y[snake_length]=y[snake_length-1];
            for (int i = 1; i < snake_length; i++) {
                body.paintIcon(this, g, x[i], y[i]);
            }
            food.paintIcon(this,g,foodx,foody);
        }
        if(gamestart==false) {
            //游戏开始按键提示
            g.setColor(Color.white);
            g.setFont(new Font("宋体",Font.BOLD,20));
            g.drawString("按空格键以开始游戏", 350, 375);
        }
        if(gameover==true){
            //游戏失败提示
            g.setColor(Color.white);
            g.setFont(new Font("宋体",Font.BOLD,20));
            g.drawString("你死了，按R以重新开始",350,375);
        }
        if (win == true) {
            g.setColor(new Color(220,20,60));
            g.setFont(new Font("宋体",Font.BOLD,40));
            g.drawString("恭喜您成功吃了47坨屎，您赢辣!!",150,350 );
        }
    }
    public void Snake(){
        snake_length=3;//初始化蛇长
        snake_direction='r';//初始化蛇头方向
        time=200;//初始化间隔
        timer.setDelay(time);
        x[0]=100;//初始化蛇的坐标
        y[0]=80;
        x[1]=50;
        y[1]=80;
        x[2]=0;
        y[2]=80;
        for(int i=3;i<200;i++){
            x[i]=0;
            y[i]=0;
        }
        //初始化食物坐标
        foodx=50*ran.nextInt(18);
        foody=80+50*ran.nextInt(12);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int KeyPress=e.getKeyCode();//令此变量数值等于被按下键的数值
        if(KeyPress==KeyEvent.VK_SPACE&&gamestart==false) {//如果KeyPress等于空格的数值
            this.gamestart = true;//游戏开始
            repaint();//并且重画
        }else if(KeyPress==KeyEvent.VK_P&&gamestart==true&&gameover==false&& win==false){//用于游戏开始后的游戏暂停
            this.pause = !pause;
            repaint();
        }
        //监听按键上下左右
        if(KeyPress==KeyEvent.VK_LEFT&&gamestart==true&&pause==false&&gameover==false&&snake_direction!='r'&& win==false){
                snake_direction='l';
        }else if(KeyPress==KeyEvent.VK_RIGHT&&gamestart==true&&pause==false&&gameover==false&&snake_direction!='l'&& win==false){
                snake_direction='r';
        }else if(KeyPress==KeyEvent.VK_UP&&gamestart==true&&pause==false&&gameover==false&&snake_direction!='d'&& win==false){
                snake_direction='u';
        }else if(KeyPress==KeyEvent.VK_DOWN&&gamestart==true&&pause==false&&gameover==false&&snake_direction!='u'&& win==false){
                snake_direction='d';
        }
        //重新开始判定
        if(gameover==true&&KeyPress==KeyEvent.VK_R){
            Snake();
            gameover=false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //让后面身体的坐标变成前面身体的坐标 实现蛇身移动，且只有游戏开始且不暂停时可执行
        if(gamestart==true && pause==false && gameover==false && win==false) {
            for (int i = snake_length - 1; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            //判断舌头所在方向，并前进
            if(snake_direction=='l'){
                x[0]=x[0]-50;
            }else if(snake_direction=='r'){
                x[0]=x[0]+50;
            }else if(snake_direction=='u'){
                y[0]=y[0]-50;
            }else if(snake_direction=='d'){
                y[0]=y[0]+50;
            }
            //吃食物
            if(x[0]==foodx && y[0]==foody){
                //蛇长+1
                snake_length++;
                //重新生成食物
                foodx=50*ran.nextInt(18);
                foody=80+50*ran.nextInt(12);
            }
            //判断蛇头是否撞到蛇身
            for(int i=1;i<snake_length;i++){
                if(x[0]==x[i]&&y[0]==y[i]){
                    gameover=true;
                }
            }
            //判断蛇头是否撞墙
            if(x[0]<0||x[0]>850||y[0]>630||y[0]<80){
                gameover=true;
            }
            //判断蛇长是否达到胜利条件
            if(snake_length==50){
                win=true;
            }
            repaint();
        }
        //根据蛇身长度分阶段增加蛇的移动速度
        if(snake_length==10){
            time=180;
            timer.setDelay(time);
        }else if(snake_length==20){
            time=150;
            timer.setDelay(time);
        }else if(snake_length==30){
            time=130;
            timer.setDelay(time);
        }else if(snake_length==40){
            time=100;
            timer.setDelay(time);
        }
        timer.start();//继续调用timer 注：此处不可使用restart 使用restart会重新使用初始delay 导致无法实现难度升级
    }
}