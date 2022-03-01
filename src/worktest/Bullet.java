package worktest;

import java.awt.image.BufferedImage;

public class Bullet implements Runnable {

    private int _x;
    private int _y;

    private int _xStart;

    private boolean _dir; //方向 true为left, false 为right;

    private boolean is_Pause = false;

    private int _type;

    private int _damage;

    private int _movingSpeed;

    private BufferedImage _imageShow = null;

    private BackGround _BackGround;

    private int bullet_width;
    private int bullet_height;

    private Thread _t = new Thread(this);

    public Bullet(int xStart, int yStart, boolean Dire, int type, BackGround backGround) {
        this._x = xStart;
        this._y = yStart;
        _xStart = xStart;
        _dir = Dire;
        _type = type;
        _movingSpeed = 8;
        switch (_type) {
            case 0:
                _damage = 1;
                bullet_height = 8;
                bullet_width = 16;
                break;
            case 1:
                _damage = 1;
                bullet_height = 10;
                bullet_width = 20;
                break;
        }
        _BackGround = backGround;
        int temp;
        if (_dir)
            temp = 0;
        else
            temp = 1;

        _imageShow = StaticValue.allBulletImage.get(_type * 2 + temp);
        _t.start();
    }


    @Override
    public void run() {
        while (true) {
            if (!this.is_Pause) {
                if (_dir)
                    this._x += _movingSpeed;
                else
                    this._x -= _movingSpeed;
                if(_type == 0)
                {
                    if((this._x - this._xStart) * (this._x - this._xStart) >= 90000)
                        this.dead();

                }
                if(_type == 1)
                {
                    if(this._x < 0 || this._x > 900)
                        this.dead();
                }
                for(int i = 0; i < this._BackGround.getAll_Obstruction().size(); i++)
                {
                    Obstruction o = this._BackGround.getAll_Obstruction().get(i);
                    if(this._x + this.bullet_width >= o.get_x() && o.get_x() + o.getObstruction_width() >= this._x && this._y + this.bullet_height >= o.get_y() && o.get_y() + o.getObstruction_height() >= this._y)
                    {
                        this.dead();
                    }
                }
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void dead() {
        this._BackGround.getAll_New_Bullet().remove(this);
    }

    public void setIs_Pause(boolean is_Pause) {
        this.is_Pause = is_Pause;
    }

    public BufferedImage get_imageShow() {
        return _imageShow;
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }

    public int get_damage() {
        return _damage;
    }

    public int getBullet_height() {
        return bullet_height;
    }

    public int getBullet_width() {
        return bullet_width;
    }

    public int get_type() {
        return _type;
    }
}
