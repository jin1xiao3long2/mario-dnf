package worktest;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {  //栗子怪与乌龟怪

    private int _x;
    private int _y;  //怪物当前位置

    private int _xStart;
    private int _yStart;  //怪物初始位置

    private int _moving;  //移动状态

    private int _type;   //怪物类型

    private int enemy_Width;  //确定大小  主页面也要修改
    private int enemy_Height;

    private boolean is_Pause = false;

    private boolean is_dead = false;

    private BufferedImage _ImageShow = null; //图片
    private BufferedImage _BloodImage = null;

    private BackGround _backGround = null;

    private String _status;
  //  private Mario _mario;

    private Thread _thread = new Thread(this);

    private final int x_Touch_Distance = 40;
    private final int y_Touch_Distance = 40;

    private int moving_Speed;

    private int _bloodStart;

    private int _blood;

    public Enemy(int x, int y, String status, int type, BackGround back_ground) // 还有个参数为BackGround back_ground
    {
        this._x = x;
        this._y = y;
        this._xStart = x;
        this._yStart = y;
        this._status = status;
        this._type = type;
        this._backGround = back_ground;
        this._moving = 0;
  //      this._mario = mario;
        switch (_type) {
            case 1:
                _bloodStart = 5;
                enemy_Height = 50;
                enemy_Width = 50;
                moving_Speed = 10;
                break;
            case 2:
                _bloodStart = 3;
                enemy_Height = 35;
                enemy_Width = 50;
                moving_Speed = 10;
                break;
             default:
                 break;
        }
        _blood = _bloodStart;
        _BloodImage = StaticValue.allEnemyBloodImage.get(_blood - 1);
        //修改imageshow
        _thread.start();
    }


    @Override
    public void run() {
        while (true) {
            if (!this.is_Pause) {
                if (this._type == 1) {
                    if (this._status.indexOf("left") != -1) {
                        this._x -= moving_Speed;
                    } else {
                        this._x += moving_Speed;
                    }//变换移动
                    if (_moving == 0) {
                        _moving = 1;
                    } else {
                        _moving = 0;
                    }
                    boolean can_LeftMove = true;
                    boolean can_RightMove = true;

                    for (int i = 0; i < this._backGround.getAll_Obstruction().size(); i++) {
                        Obstruction o = this._backGround.getAll_Obstruction().get(i);
                        if ((o.get_x() == this._x + x_Touch_Distance) && (o.get_y() - y_Touch_Distance < this._y && o.get_y() + y_Touch_Distance > this._y)) //因为是等于,所以要控制每次移动的步数
                        {
                            can_RightMove = false;
                        }
                        if ((o.get_x() == this._x - x_Touch_Distance) && (o.get_y() - y_Touch_Distance < this._y && o.get_y() + y_Touch_Distance > this._y)) {
                            can_LeftMove = false;
                        }
                    }
                    if ((this._status.indexOf("left") != -1) && (!can_LeftMove || this._x == 0)) {  //控制移动和方向
                        this._status = "right-moving";
                    } else if ((this._status.indexOf("right") != -1) && (!can_RightMove || this._x == 840)) {
                        this._status = "left-moving";
                    }
                    if (!is_dead) {
                        for (int i = 0; i < this._backGround.getAll_New_Bullet().size(); i++) {
                            Bullet b = this._backGround.getAll_New_Bullet().get(i);
                            if (this._x - b.getBullet_width() <= b.get_x() && this.get_x() + this._x >= b.get_x() && this._y - b.getBullet_height() < b.get_y() && this._y + this.enemy_Height > b.get_y()) {
                                if(b.get_type() == 0) {
                                    this.hurt(b.get_damage());
                                    b.dead();
                                }
                            }
                        }
                    }
                    int temp = ((this._status.indexOf("left") != -1) ? 2 : 0);
                    this._ImageShow = StaticValue.allTriangleImage.get(temp + _moving);
                }
                else if(this._type == 2) {

                    int temp = ((this._status.indexOf("left") != -1) ? 3 : 0);

                    int s = 0;
                    if (!is_dead) {
                        _moving++;

                        if(_moving > 14)
                            s++;
                        if(_moving == 19)
                        {
                            if(temp == 3)
                            {
                                Bullet b = new Bullet(this._x, this._y + 15, false, 1, this._backGround);
                                this._backGround.getAll_New_Bullet().add(b);
                            }
                            else
                            {
                                Bullet b = new Bullet(this._x, this._y + 15, true, 1, this._backGround);
                                this._backGround.getAll_New_Bullet().add(b);
                            }
                        }
                        if(_moving > 19)
                            s++;
                        if(_moving == 28)
                            _moving = 0;
                        for (int i = 0; i < this._backGround.getAll_New_Bullet().size(); i++) {
                            Bullet b = this._backGround.getAll_New_Bullet().get(i);
                            if (this._x - b.getBullet_width() <= b.get_x() && this.get_x() + this._x >= b.get_x() && this._y - b.getBullet_height() < b.get_y() && this._y + this.enemy_Height > b.get_y()) {
                                if(b.get_type() == 0) {
                                    this.hurt(b.get_damage());
                                    b.dead();
                                }
                            }
                        }
                    }

                    this._ImageShow = StaticValue.allGebulinImage.get(temp + s);
                    }

            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        this._x = this._xStart;
        this._y = this._yStart;
        this._blood = _bloodStart;
        this.is_dead = false;
        this._BloodImage = StaticValue.allEnemyBloodImage.get(_blood - 1);
        this._ImageShow = StaticValue.allTriangleImage.get(_type - 1);
    }

    public void hurt(int damage) {
        this._blood = this._blood - damage;
        if (_blood <= 0) {
            this.dead();
            return;
        } else
            this._BloodImage = StaticValue.allEnemyBloodImage.get(_blood - 1);
    }

    public void dead() {  //取消死亡动画
        this.is_dead = true;

        this._backGround.getAll_Enemy().remove(this);
        this._backGround.getAll_Dead_Enemy().add(this);
    }


    public int get_y() {
        return _y;
    }

    public int get_x() {
        return _x;
    }

    public int get_type() {
        return _type;
    }

    public BufferedImage get_ImageShow() {
        return _ImageShow;
    }

    public BufferedImage get_BloodImage() {
        return _BloodImage;
    }

    public void setIs_Pause(boolean is_Pause) {
        this.is_Pause = is_Pause;
    }

    public int getEnemy_Height() {
        return enemy_Height;
    }

    public int getEnemy_Width() {
        return enemy_Width;
    }
}
