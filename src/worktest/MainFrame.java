package worktest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainFrame extends JFrame implements KeyListener, Runnable {

    private Thread test1 = new Thread(this);  //线程

    private final int window_Width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取显示屏宽度
    private final int window_Height = Toolkit.getDefaultToolkit().getScreenSize().height; // 获取显示屏高度

    private final int size_Width = 900;
    private final int size_Height = 600;

    private final int rightMove_KeyCode = 39;
    private final int leftMove_KeyCode = 37;
    private final int upMove_KeyCode = 38;
    private final int downMove_KeyCode = 40;
    private final int enter_KeyCode = 32;
    private final int return_KeyCode = 27;                                    //不同的键盘值


    private Mario mario1 = null;                                       //实例化一个马里奥类

    private List<BackGround> all_BackGround = new ArrayList<BackGround>();        //获取不同的背景图片

    private BackGround _BackGround = null;                                //第一个背景图片

    private boolean is_Start = false;
    private boolean is_Help = false;                        //开始或者帮助
    private boolean is_Dead = false;        //马里奥
    private boolean is_Win = false;      //马里奥
    private boolean is_Pause = false;   //给马里奥

    private int _choose = 0;

    private int _flash = 0;

    public static void main(String[] args)          //程序的入口
    {
        new MainFrame();
    }

    public MainFrame() {
//        final int window_Width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取显示屏宽度
//        final int window_Height = Toolkit.getDefaultToolkit().getScreenSize().height;  //获取显示屏高度
        setSize(size_Width, size_Height);                                          //设置窗口的大小
        setTitle("马里奥");                                                              //设置窗口的标题
        setDefaultCloseOperation(EXIT_ON_CLOSE);                                      //设置关闭的方式
        setLocation((window_Width - size_Height) / 2, (window_Height - size_Height) / 2);
        setResizable(false);                                                           //设置为不可修改窗口大小
        StaticValue.init();                                                            //将图片全部导入
        for (int i = 0; i < 3; i++)
            this.all_BackGround.add(new BackGround(i + 1, i == 2 ? true : false));     //将背景图片导入
        this._BackGround = all_BackGround.get(0);                                     //初始化背景
        this.mario1 = new Mario();                                          //初始化马里奥的位置
        this.mario1.set_BackGround(this._BackGround);                                   //放在地图中(地图就是信息)
        this.repaint();                                                                    //执行repaint操作
        this.addKeyListener(this);                                                      //键盘监听器
        test1.start();
        //开始线程
        setVisible(true);                                                              //窗口可见
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(size_Width, size_Height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g2 = image.getGraphics();                             //画笔将图片缓冲
        if (is_Start && !is_Pause)  //此处应判断是否开始
        {
            g2.drawImage(this._BackGround.getBg_Image(), 0, 0, size_Width, size_Height, this);
            g2.setFont(new Font("微软雅黑", Font.BOLD, 15));
            g2.setColor(Color.RED);
            g2.drawString("           ×" + mario1.get_life(), 60, 70);
            g2.drawImage(StaticValue.allMarioImage.get(0),60, 30, 30, 60, this);
            Iterator<Enemy> iter_Enemy = this._BackGround.getAll_Enemy().iterator();
            while (iter_Enemy.hasNext()) {
                Enemy e = iter_Enemy.next();
                g2.drawImage(e.get_ImageShow(), e.get_x(), e.get_y(), e.getEnemy_Width(), e.getEnemy_Height(), this);
                g2.drawImage(e.get_BloodImage(), e.get_x(), e.get_y() - 13, e.getEnemy_Width(), 6, this); //显示血条
            }
            Iterator<Obstruction> iter_Obstr = this._BackGround.getAll_Obstruction().iterator();
            while (iter_Obstr.hasNext()) {
                Obstruction o = iter_Obstr.next();
                g2.drawImage(o.get_ImageShow(), o.get_x(), o.get_y(), o.getObstruction_width(), o.getObstruction_height(), this);
            }
            Iterator<Bullet> iter_Bullet = this._BackGround.getAll_New_Bullet().iterator();
            while (iter_Bullet.hasNext()) {
                Bullet b = iter_Bullet.next();
                g2.drawImage(b.get_imageShow(), b.get_x(), b.get_y(), b.getBullet_width(), b.getBullet_height(), this);
            }
            g2.drawImage(this.mario1.get_imageShow(), mario1.get_x(), mario1.get_y(), mario1.getMario_width(), mario1.getMario_height(), this);
            if(mario1.isHas_Fire())
            {
                if(mario1.is_left())
                    g2.drawImage(mario1.get_imageFire(), mario1.get_x() - 40, mario1.get_y() + 20, mario1.getFire_width(), mario1.getFire_height(), this);
                else
                    g2.drawImage(mario1.get_imageFire(), mario1.get_x() + 35, mario1.get_y() + 20, mario1.getFire_width(), mario1.getFire_height(), this);
            }
        } else if (is_Start && is_Pause)   // continue / restart  / exit
        {
            g2.drawImage(StaticValue.PauseGorundImage, 0, 0, size_Width, size_Height, this);
            drawChooseImage(g2, this._choose);

        } else if (is_Dead)  //restart /  exit
        {
            g2.drawImage(StaticValue.DeadGroundImage, 0, 0, size_Width, size_Height, this);
            drawChooseImage(g2, this._choose);
        } else if (is_Win)   //restart /  exit
        {
            g2.drawImage(StaticValue.WinGroundImage, 0, 0, size_Width, size_Height, this);
            drawChooseImage(g2, this._choose);
        } else {
            if (!is_Help) {
                g2.drawImage(StaticValue.StartGroundImage, 0, 0, size_Width, size_Height, this);
                drawChooseImage(g2, this._choose);
            } else {
                g2.drawImage(StaticValue.HelpGroundImage, 0, 0, size_Width, size_Height, this);
                g2.drawImage(StaticValue.allMarioImage.get(0), 40, 170, mario1.getMario_width(), mario1.getMario_height(), this);
                g2.setFont(new Font("微软雅黑", Font.BOLD, 30));
                g2.setColor(Color.BLUE);
                g2.drawString("你当前的人物  ↑跳跃, ←向左移动, →向右移动, 空格射击", 100, 220);
                g2.drawImage(StaticValue.allTriangleImage.get(0), 40, 270, 40, 40, this);
                g2.drawString("牛头人 在障碍物之间冲撞  生命值为5点", 100, 320);
                g2.drawImage(StaticValue.allGebulinImage.get(0), 40, 370, 40, 40, this);
                g2.drawString("烈焰哥布林 会扔火球 生命值3点 小心点!", 100, 420);
            }
        }
        g.drawImage(image, 0, 0, this);
    }

    void drawChooseImage(Graphics p, int i) {
        if (_flash < 30)
            p.drawImage(StaticValue.MarioChooseImage, 140, i * 120 + 300, 70, 45, this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (is_Start) { //如果在游戏阶段
            if (!is_Pause) {   // 如果非暂停阶段 只有两个操作 一个是暂停  一个是正常操作
                if (e.getKeyCode() == rightMove_KeyCode)   //向右移动
                {
                    mario1.rightMove();
                } else if (e.getKeyCode() == leftMove_KeyCode)     //向左移动
                {
                    mario1.leftMove();
                } else if (e.getKeyCode() == upMove_KeyCode)        //跳跃
                {
                    mario1.Jump();
                } else if (e.getKeyCode() == enter_KeyCode) {
                    mario1.shoot();
                } else if (e.getKeyCode() == return_KeyCode) {
                    this.mario1.Pause();
                    this._BackGround.Pause();
                    _choose = 0;                            //每次按enter/esc时都要重置_choose
                }
            } else //暂停阶段
            {
                if (e.getKeyCode() == downMove_KeyCode) //选择选项
                {
                    if (this._choose != 2)
                        this._choose++;
                    else
                        this._choose = 0;
                } else if (e.getKeyCode() == upMove_KeyCode) {
                    if (this._choose != 0)
                        this._choose--;
                    else
                        this._choose = 2;
                } else if (e.getKeyCode() == return_KeyCode) //继续游戏
                {
                    this.mario1.dePause();
                    this._BackGround.dePause();
                    this._choose = 0;
                } else if (e.getKeyCode() == enter_KeyCode) {
                    if (this._choose == 0) //继续游戏
                    {
                        this.mario1.dePause();
                        this._BackGround.dePause();
                        this._choose = 0;
                    } else if (this._choose == 1) //重新开始游戏
                    {
                        //重新开始游戏的函数(重置所有数据)
                        resetData();
                        this.mario1.dePause();
                        this._BackGround.dePause();
                        this._choose = 0;
                    } else if (this._choose == 2) {
                        this.mario1.dePause();
                        this._BackGround.dePause();
                        this.is_Pause = false;
                        this.is_Start = false;
                        this._choose = 0;
                    }
                }
            }
        } else if (is_Dead) {  //三个选项 重新开始游戏或者返回主菜单或者离开游戏
            if (e.getKeyCode() == upMove_KeyCode) {
                if (this._choose != 0)
                    this._choose--;
                else
                    this._choose = 2;
            } else if (e.getKeyCode() == downMove_KeyCode) {
                if (this._choose != 2)
                    this._choose++;
                else
                    this._choose = 0;
            } else if (e.getKeyCode() == enter_KeyCode) {
                if (this._choose == 0) {
                    this.is_Dead = false;
                    this.is_Start = true;
                    resetData();         //重置为第一关开始的样子
                    this._choose = 0;
                } else if (this._choose == 1) {
                    this.is_Dead = false;
                    this.is_Start = false;
                    _choose = 0;
                    resetData();         //重置为第一关开始的样子
                } else
                    System.exit(0);
            }

        } else if (is_Win) {
            if (e.getKeyCode() == upMove_KeyCode) {
                if (this._choose != 0)
                    this._choose--;
                else
                    this._choose = 2;
            } else if (e.getKeyCode() == downMove_KeyCode) {
                if (this._choose != 2)
                    this._choose++;
                else
                    this._choose = 0;
            } else if (e.getKeyCode() == enter_KeyCode) {
                if (this._choose == 0) {
                    this.is_Win = false;
                    this.is_Start = true;
                    resetData();         //重置为第一关开始的样子
                    this._choose = 0;
                } else if (this._choose == 1) {
                    this.is_Win = false;
                    this.is_Start = false;
                    _choose = 0;
                    resetData();         //重置为第一关开始的样子
                } else
                    System.exit(0);
            }
        } else { // 主界面
            this.resetData();
            if (!is_Help) {
                if (e.getKeyCode() == enter_KeyCode) {
                    if (this._choose == 0) {
                        this.is_Start = true;
                        this.is_Help = false;
                        this.mario1.set_x(0);
                        this.mario1.set_y(220);
                        _choose = 0;
                    } else if (this._choose == 1) {
                        this.is_Help = true;
                        this.is_Start = false;
                        _choose = 0;
                    } else if (this._choose == 2) {
                        System.exit(0);
                    }
                } else if (e.getKeyCode() == upMove_KeyCode) {
                    if (this._choose == 0) {
                        _choose = 2;
                    } else {
                        _choose--;
                    }
                } else if (e.getKeyCode() == downMove_KeyCode) {
                    if (this._choose == 2) {
                        _choose = 0;
                    } else {
                        _choose++;
                    }
                }
            } else if (e.getKeyCode() == return_KeyCode) {
                this.is_Help = false;
                this.is_Start = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (is_Start) {
            if (e.getKeyCode() == rightMove_KeyCode)   //释放按键停止移动
            {
                mario1.rightStop();
            } else if (e.getKeyCode() == leftMove_KeyCode) {
                mario1.leftStop();
            }
        }
    }

    public void resetData() {
        mario1.reset();
        this._BackGround = all_BackGround.get(0);
        this.mario1.set_BackGround(this._BackGround);
        this.mario1.setIs_Pause(false);
        for (int i = 0; i < this.all_BackGround.size(); i++) {
            this.all_BackGround.get(i).reset();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (is_Start && !is_Pause) {
            if (e.getKeyCode() == leftMove_KeyCode) {
                mario1.leftMove();
            } else if (e.getKeyCode() == rightMove_KeyCode) {
                mario1.rightMove();
            }
        }
    }


    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                _flash++;
                if (_flash == 60)
                    _flash = 0;
                Thread.sleep(20);
                if (this.mario1.get_x() <= 880 && this.mario1.get_x() >= 850 && this.mario1.get_y() == 480 && !this._BackGround.is_isOver()) {
                    this._BackGround = this.all_BackGround.get(this._BackGround.get_sort());
                    this.mario1.set_BackGround(this._BackGround);
                    this.mario1.nextSort();
                     //怪物开始移动
                }
                if (this.mario1.isIs_Dead()) {
                    this.is_Start = false;
                    this.is_Help = false;
                    this.is_Dead = true;
                    this.is_Win = false;
                    this.is_Pause = false;
                }
                if (this.mario1.isIs_Win()) {
                    this.is_Start = false;
                    this.is_Help = false;
                    this.is_Dead = false;
                    this.is_Win = true;
                    this.is_Pause = false;
                }
                if (this.mario1.isIs_Pause() && this.is_Start) {
                    this.is_Start = true;
                    this.is_Pause = true;
                    this.is_Win = false;
                    this.is_Help = false;
                    this.is_Dead = false;
                }
                if (!this.mario1.isIs_Pause() && this.is_Start) {
                    this.is_Start = true;
                    this.is_Pause = false;
                    this.is_Win = false;
                    this.is_Help = false;
                    this.is_Dead = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
