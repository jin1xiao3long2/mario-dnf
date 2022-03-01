package worktest;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {

    private int _life; //人物的生命值

    private int _x;
    private int _y;     //人物的坐标

    private int _xMove = 0;
    private int _yMove = 0;  //人物的移动 跳跃属性
    private int _upTime = 0;
    private int to_Dead_Time = 80;  //控制死亡动画
    private int shoot_time = 15;

    private String _status;   //人物的状态 用字符串表示
    private int _moving = 1;

    private int _bullet_type = 0;

    private boolean _touchFlag = false;

    private BufferedImage _imageShow;
    private BufferedImage _imageFire;

    private BackGround _BackGround;

    private Thread _t = null;  //线程

    private boolean is_Dead = false;
    private boolean is_Win = false;
    private boolean is_Pause = false;
    private boolean is_Shooting = false;
    private boolean has_Fire = false;

    private final int Start_x = 0;
    private final int Start_y = 220;

    private final int x_Touch_Distance = 30;
    private final int up_y_Touch_Distance = 30;
    private final int down_y_Touch_Distance = 60;

    private final int Mario_width = 30;
    private final int Mario_height = 60;

    private final int Fire_width = 35;
    private final int Fire_height = 15;

    private boolean is_to_dead = false;



    public Mario()  //初始状态
    {
        this._x = Start_x;
        this._y = Start_y;
        this._life = 3;
        this._imageShow = StaticValue.allMarioImage.get(2);
        this._t = new Thread(this);
        this._t.start();
        this._upTime = 0;
        this._status = "right-jumping";
    }

    public void reset() {
        this._x = Start_x;
        this._y = Start_y;
        this._life = 3;
        this._upTime = 0;
        this._status = "right-jumping";
        this._xMove = 0;
        this.is_Pause = false;
        this.is_Dead = false;
        this.is_Win = false;
        this._touchFlag = false;
    }

    public void nextSort(){
        this._x = Start_x;
        this._y = 450;
    }

    public void leftMove() //向左 不能改变跳跃/移动状态
    {
        if(is_Shooting)
            return ;
        _xMove = -5;
        if (this._status.indexOf("jumping") != -1) {
            this._status = "left-jumping";
        } else {
            this._status = "left-moving";
        }
    }

    public void rightMove()  //向右 不能改变跳跃/移动状态
    {
        if(is_Shooting)
            return ;
        _xMove = 5;
        if (this._status.indexOf("jumping") != -1) {
            this._status = "right-jumping";
        } else {
            this._status = "right-moving";
        }
    }

    public void leftStop()  //停止 不能改变方向与跳跃状态
    {
        if(is_Shooting)
            return ;
        _xMove = 0;
        if (this._status.indexOf("jumping") != -1) {
            this._status = "left-jumping";
        } else {
            this._status = "left-standing";
        }
    }

    public void rightStop()  //停止 不能修改方向与跳跃状态
    {
        if(is_Shooting)
            return ;
        _xMove = 0;
        if (this._status.indexOf("jumping") != -1) {
            this._status = "right-jumping";
        } else {
            this._status = "right-standing";
        }
    }

    public void Jump()  //跳跃 不能改变方向
    {
        if(is_Shooting)
            return ;
        if (this._status.indexOf("jumping") == -1) {
            if (this._status.indexOf("left") != -1) {
                this._status = "left-jumping";
            } else {
                this._status = "right-jumping";
            }
            _yMove = -5;
            _upTime = 23;
        }
    }

    public void Down() //跳跃 不能改变方向
    {
        if (this._status.indexOf("left") != -1) {
            this._status = "left-jumping";
        } else {
            this._status = "right-jumping";
        }
        _yMove = 5;
    }

    public void shoot() //射击
    {
        if (this._status.indexOf("jumping") != -1) {
            return;
        } else if(is_Shooting)
        {
            return ;
        }
        else {
            _xMove = 0;
            is_Shooting = true;
            shoot_time = 15;
            if (this._status.indexOf("left") != -1) {
                Bullet b = new Bullet(this._x, this._y + 30, false, this._bullet_type, this._BackGround);
                _BackGround.getAll_New_Bullet().add(b);
                _imageFire = StaticValue.allFireImage.get(1);
                this._status = "left-standing";
            } else {
                Bullet b = new Bullet(this._x, this._y + 30, true, this._bullet_type, this._BackGround);
                _BackGround.getAll_New_Bullet().add(b);
                _imageFire = StaticValue.allFireImage.get(0);
                this._status = "right-standing";
            }
        }
    }

    public void Pause() {
        this.is_Pause = true;
    }

    public void dePause() {
        this.is_Pause = false;
    }


    public void dead() {
        this._life--;
        if (this._life == 0) {
            this.is_Dead = true;
            this.is_to_dead = false;
            this._status = "right-standing";
            this._upTime = 0;
        } else {
            this._BackGround.reset();//重置this._BackGround
            this._x = 0;
            this._y = 220;
            this.is_to_dead = false;
            this._upTime = 0;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!is_Pause) {
                if (is_to_dead)  //实现死亡动画
                {
                    to_Dead_Time--;
                    if (to_Dead_Time > 55)
                        this._y -= 3;
                    else if (to_Dead_Time > 48) {
                        ;
                    } else {
                        this._y += 6;
                    }
                    if (to_Dead_Time == 0) {
                        to_Dead_Time = 80;
                        this.dead();
                    }
                } else {
                    if (this._BackGround.is_flag() && this._x > 510 && this._y > 120) { //删减
                        this._BackGround.set_isOver(true);
                        if (this._BackGround.is_isDown()) {
                            this._status = "right-moving";
                            if (this._x < 570) {
                                this._x += 5;
                            } else {
                                if (this._y < 440) {
                                    this._y += 5;
                                }
                                this._x += 7;
                                if (this._x > 780) {
                                    this.is_Win = true;
                                }
                            }
                        } else {
                            if (!_touchFlag) {
                                this._y = 180;
                                this._status = "right-standing";
                                _touchFlag = true;
                            }
                            if (this._y < 480) {
                                this._y += 3;
                            }

                        }
                    } else {
                        boolean can_LeftMove = true;
                        boolean can_RightMove = true;
                        boolean on_Land = false;

                        for (int i = 0; i < this._BackGround.getAll_Obstruction().size(); i++)   //碰撞检测 需要大幅度修改
                        {
                            Obstruction o = this._BackGround.getAll_Obstruction().get(i);
                            if (this.get_x() == 860)
                                can_RightMove = false;
                            if (this.get_x() == 0)
                                can_LeftMove = false;
                            if (this._status.indexOf("jumping") == -1) //普通移动的情况
                            {
                                if (o.get_x() == this._x + x_Touch_Distance && (o.get_y() > this._y - up_y_Touch_Distance && o.get_y() < this._y + down_y_Touch_Distance)) {
                                    can_RightMove = false;
                                }
                                if (o.get_x() == this._x - x_Touch_Distance && (o.get_y() > this._y - up_y_Touch_Distance && o.get_y() < this._y + down_y_Touch_Distance)) {
                                    can_LeftMove = false;
                                }
                                if (o.get_y() == this._y + down_y_Touch_Distance && (o.get_x() + x_Touch_Distance > this._x && o.get_x() - x_Touch_Distance < this._x)) {
                                    on_Land = true;
                                }
                            } else if (this._upTime != 0) {
                                if (o.get_x() == this._x + x_Touch_Distance && (o.get_y() >= this._y - up_y_Touch_Distance && o.get_y() <= this._y + down_y_Touch_Distance)) {
                                    can_RightMove = false;
                                }
                                if (o.get_x() == this._x - x_Touch_Distance && (o.get_y() >= this._y - up_y_Touch_Distance && o.get_y() <= this._y + down_y_Touch_Distance)) {
                                    can_LeftMove = false;
                                }
                                if (o.get_y() == this._y - up_y_Touch_Distance && (o.get_x() + x_Touch_Distance > this._x && o.get_x() - x_Touch_Distance < this._x)) {
                                    //还有特殊砖块特殊操作
                                    if (o.get_type() == 1) {
                                        this._BackGround.getAll_Obstruction().remove(o);
                                        this._BackGround.getAll_Dead_Obstruction().add(o);
                                    }
                                    _upTime = 0;
                                }
                            } else {
                                if (o.get_x() == this._x + x_Touch_Distance && (o.get_y() >= this._y - up_y_Touch_Distance - 5 && o.get_y() <= this._y + down_y_Touch_Distance + 5)) {
                                    can_RightMove = false;
                                }
                                if (o.get_x() == this._x - x_Touch_Distance && (o.get_y() >= this._y - up_y_Touch_Distance - 5 && o.get_y() <= this._y + down_y_Touch_Distance + 5)) {
                                    can_LeftMove = false;
                                }
                                if (o.get_y() == this._y + down_y_Touch_Distance && (o.get_x() + x_Touch_Distance >= this._x && o.get_x() - x_Touch_Distance <= this._x)) {
                                    on_Land = true;
                                }
                            }
                        }

                        for (int i = 0; i < this._BackGround.getAll_Enemy().size(); i++) //与敌人的交互 //修改为子弹
                        {
                            Enemy e = this._BackGround.getAll_Enemy().get(i);
                            if ((e.get_x() + x_Touch_Distance > this._x && e.get_x() - x_Touch_Distance < this._x) && (e.get_y() + down_y_Touch_Distance > this._y && e.get_y() - down_y_Touch_Distance < this._y)) {
                                is_to_dead = true;
                            }
                            if (e.get_y() == this._y + down_y_Touch_Distance && (e.get_x() + x_Touch_Distance > this._x && e.get_x() - x_Touch_Distance < this._x)) {
                                if (e.get_type() == 1) {
                                    e.hurt(1);
                                    this._upTime = 3;
                                    this._yMove = -5;
                                }
                            }
                        }

                        for(int i = 0 ; i < this._BackGround.getAll_New_Bullet().size(); i++)
                        {
                            Bullet b = this._BackGround.getAll_New_Bullet().get(i);
                            if(this._x + this.Mario_width >= b.get_x() && b.get_x() + b.getBullet_width() >= this._x && this._y + this.Mario_height >= b.get_y() && b.get_y() + b.getBullet_height() > this._y)
                            {
                                if(b.get_type() == 1)
                                    is_to_dead = true;
                            }
                        }
                        if (this._y > 600) {
                            this.dead();
                        }
                        if (on_Land && _upTime == 0) //判断为下降到地面
                        {
                            if (_xMove == 0) {
                                if (this._status.indexOf("left") != -1) {
                                    this._status = "left-standing";
                                } else {
                                    this._status = "right-standing";
                                }
                            } else {
                                if (this._status.indexOf("left") != -1) {
                                    this._status = "left-moving";
                                } else {
                                    this._status = "right-moving";
                                }
                            }
                        } else  //在空中
                        {
                            if (_upTime != 0) {
                                _upTime--;
                            } else {
                                this.Down();
                            }
                            _y += _yMove;
                            if(this._y == 0)
                                _upTime = 0;
                        }
                        if ((can_LeftMove && _xMove < 0) || (can_RightMove && _xMove > 0))  //马里奥的移动
                        {
                            _x += _xMove;
                            if (_x < 0) { //限制不能离开地图(最左边

                                _x = 0;
                            }
                        }
                    }
                }
            }
            if(is_Shooting)
            {
                shoot_time--;
                if(shoot_time == 0)
                    is_Shooting = false;
            }
            int temp = 0;
            if (this._status.indexOf("left") != -1)  //控制移动的图片
            {
                temp = 9;
            } else {
                temp = 0;
            }
            if (!is_Pause) {
                if (is_Shooting)
                {
                    temp += 7;
                    if(shoot_time < 10)
                    {
                        temp += 1;
                        has_Fire = true;//加入火花
                    }
                    if(shoot_time < 2)
                    {
                        has_Fire = false;//移除火花
                    }
                }
                else if (this._status.indexOf("moving") != -1) {
                    temp += this._moving;
                    _moving++;
                    if (_moving == 5) {
                        _moving = 1;
                    }
                }

            }

            if(this._status.indexOf("jumping") != -1){
                temp  += 5;
                if(_upTime == 0)
                    temp += 1;
            }
            //获取跳跃图片索引

            _imageShow = StaticValue.allMarioImage.get(temp);

            if (is_to_dead)
            {
                if (this._status.indexOf("left") != -1)
                    this._imageShow = StaticValue.MarioDeadLeftImage;
                else
                    this._imageShow = StaticValue.MarioDeadRightImage;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int get_life() {
        return _life;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public int get_y() {
        return _y;
    }

    public BufferedImage get_imageShow() {
        return _imageShow;
    }

    public void set_BackGround(BackGround _BackGround) {
        this._BackGround = _BackGround;
        this._BackGround.set_mario(this);
    }

    public boolean isIs_Dead() {
        return is_Dead;
    }

    public boolean isIs_Win() {
        return is_Win;
    }

    public boolean isIs_Pause() {
        return is_Pause;
    }

    public int get_upTime() {
        return _upTime;
    }

    public void setIs_Pause(boolean is_Pause) {
        this.is_Pause = is_Pause;
    }

    public boolean isIs_jumping() {
        return this._status.indexOf("jumping") != -1;
    }

    public int getMario_height() {
        return Mario_height;
    }

    public int getMario_width() {
        return Mario_width;
    }

    public boolean isHas_Fire() {
        return has_Fire;
    }

    public boolean is_left(){
        if(this._status.indexOf("left") != -1)
            return true;
        else return false;
    }

    public BufferedImage get_imageFire() {
        return _imageFire;
    }

    public int getFire_height() {
        return Fire_height;
    }

    public int getFire_width() {
        return Fire_width;
    }
}
