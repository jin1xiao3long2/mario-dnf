package worktest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround { //导入背景图片并设置障碍物 //显示图片, 敌人, 障碍物

    private BufferedImage Bg_Image = null;  //当前图像显示

    private List<Enemy> all_Enemy = new ArrayList<Enemy>(); //导入所有敌人

    private List<Obstruction> all_Obstruction = new ArrayList<Obstruction>(); //导入所有的障碍物

    private List<Enemy> all_Dead_Enemy = new ArrayList<Enemy>();

    private List<Obstruction> all_Dead_Obstruction = new ArrayList<Obstruction>();

    private List<Bullet> all_New_Bullet = new ArrayList<Bullet>();  //导入子弹

    private int _sort;

    private boolean _flag = false;

    private boolean _isDown = false;

    private boolean _isOver = false;

    private Mario _mario;


    public BackGround(int sort, boolean flag) {
        Bg_Image = StaticValue.BackGroundImage;
        //初始化_sort, _flag
        this._sort = sort;
        this._flag = flag;
        if (!flag) {
            this.Bg_Image = StaticValue.BackGroundImage;
        } else {
            this.Bg_Image = StaticValue.EndGroundImage;
        }
        if (sort == 1)//_sort
        {
            for (int i = 0; i < 30; i++) {
                if(i != 2 && i != 3){
                this.all_Obstruction.add(new Obstruction(i * 30, 540, 0, this));
                this.all_Obstruction.add(new Obstruction(i * 30, 570, 0, this));
                }
            }
            this.all_Obstruction.add(new Obstruction(120, 510, 0, this));
            this.all_Obstruction.add(new Obstruction(120, 480, 0, this));
            this.all_Obstruction.add(new Obstruction(210, 510, 0, this));
            this.all_Obstruction.add(new Obstruction(210, 480, 0, this));
            this.all_Obstruction.add(new Obstruction(720, 510, 0, this));
            this.all_Obstruction.add(new Obstruction(720, 480, 0, this));
            this.all_Obstruction.add(new Obstruction(750, 510, 0, this));
            this.all_Obstruction.add(new Obstruction(750, 480, 0, this));
            this.all_Obstruction.add(new Obstruction(750, 450, 0, this));
            this.all_Obstruction.add(new Obstruction(750, 420, 0, this));
            this.all_Obstruction.add(new Obstruction(660, 360, 1, this));
            this.all_Obstruction.add(new Obstruction(600, 420, 0, this));
            this.all_Obstruction.add(new Obstruction(570, 420, 0, this));
            this.all_Enemy.add(new Enemy(300, 490, "right-moving", 1, this));
            this.all_Enemy.add(new Enemy(200, 445, "right-standing", 2, this));
            this.all_Enemy.add(new Enemy(740, 385, "left-standing", 2, this));
            //this.all_Enemy.add(new Enemy(500, 490, "left-standing", 2, this));
        }
        if (sort == 2) {
            for (int i = 0; i < 30; i++) {
                if (!(i  >= 7 && i <= 20)) {
                    this.all_Obstruction.add(new Obstruction(i * 30, 540, 0, this));
                    this.all_Obstruction.add(new Obstruction(i * 30, 570, 0, this));
                }
                if(!(i >= 12 && i <= 16 || i == 29 || i == 0 || i == 1 || i == 28)){
                    this.all_Obstruction.add(new Obstruction(i*30, 130, 0, this));
                }
            }
            this.all_Obstruction.add(new Obstruction(60, 100, 0, this));
            this.all_Obstruction.add(new Obstruction(330, 100, 0, this));
            this.all_Obstruction.add(new Obstruction(510, 100, 0, this));
            this.all_Obstruction.add(new Obstruction(810, 100, 0, this));

            this.all_Obstruction.add(new Obstruction(240, 480, 0, this));

            this.all_Obstruction.add(new Obstruction(410, 480, 0, this));

            this.all_Obstruction.add(new Obstruction(580, 480, 0, this));

            this.all_Obstruction.add(new Obstruction(310, 380, 1, this));

            this.all_Obstruction.add(new Obstruction(410, 380, 0, this));

            this.all_Obstruction.add(new Obstruction(510, 380, 1, this));
            this.all_Obstruction.add(new Obstruction(340, 280, 1, this));

            this.all_Obstruction.add(new Obstruction(480, 280, 1, this));
            this.all_Obstruction.add(new Obstruction(410, 180, 0, this));

            this.all_Enemy.add(new Enemy(120, 75, "left-moving", 1, this));
            this.all_Enemy.add(new Enemy(580, 75, "left-moving", 1, this));
            this.all_Enemy.add(new Enemy(400, 450, "left-standing", 2, this));
            this.all_Enemy.add(new Enemy(400, 350, "right-standing", 2, this));

        }
        if (sort == 3) {
            for (int i = 0; i < 30; i++) {
                this.all_Obstruction.add(new Obstruction(i * 30, 540, 0, this));
                this.all_Obstruction.add(new Obstruction(i * 30, 570, 0, this));
            }
            for(int i = 0 ; i < 5; i++)
            {
                for(int j = 0; j < i; j++)
                {
                    this.all_Obstruction.add(new Obstruction(  60 + i * 30, 510 - j * 30, 0, this));
                }
            }
            this.all_Enemy.add(new Enemy(420, 355, "left-standing", 2, this));
            this.all_Obstruction.add(new Obstruction(430, 385, 0, this));
            this.all_Enemy.add(new Enemy(420, 485, "left-moving", 1, this));
            this.all_Obstruction.add(new Obstruction(535, 190, 2, this));
            this.all_Obstruction.add(new Obstruction(550, 190, 3, this));

        }
    }

    public void reset() {
        this.all_Enemy.addAll(this.all_Dead_Enemy);
        this.all_Obstruction.addAll(this.all_Dead_Obstruction);
        this.all_Dead_Obstruction.removeAll(all_Dead_Obstruction);
        this.all_Dead_Enemy.removeAll(all_Dead_Enemy);
        for (int i = 0; i < this.all_Enemy.size(); i++) {
            this.all_Enemy.get(i).reset();
        }
        for (int i = 0; i < this.all_Obstruction.size(); i++) {
            this.all_Obstruction.get(i).reset();
        }
        this.all_New_Bullet.removeAll(all_New_Bullet);
        this._isDown = false;
        this._isOver = false;
    }

    public void Pause() {
        for (int i = 0; i < this.all_Enemy.size(); i++) {
            this.all_Enemy.get(i).setIs_Pause(true);
        }
        for (int i = 0; i < this.all_Obstruction.size(); i++) {
            this.all_Obstruction.get(i).setIs_Pause(true);
        }
        for (int i = 0; i < this.all_New_Bullet.size(); i++) {
            this.all_New_Bullet.get(i).setIs_Pause(true);
        }
    }

    public void dePause() {
        for (int i = 0; i < this.all_Enemy.size(); i++) {
            this.all_Enemy.get(i).setIs_Pause(false);
        }
        for (int i = 0; i < this.all_Obstruction.size(); i++) {
            this.all_Obstruction.get(i).setIs_Pause(false);
        }
        for (int i = 0; i < this.all_New_Bullet.size(); i++) {
            this.all_New_Bullet.get(i).setIs_Pause(false);
        }
    }

    public BufferedImage getBg_Image() {
        return Bg_Image;
    }

    public List<Enemy> getAll_Dead_Enemy() {
        return all_Dead_Enemy;
    }

    public List<Enemy> getAll_Enemy() {
        return all_Enemy;
    }

    public List<Obstruction> getAll_Dead_Obstruction() {
        return all_Dead_Obstruction;
    }

    public List<Obstruction> getAll_Obstruction() {
        return all_Obstruction;
    }

    public List<Bullet> getAll_New_Bullet() {
        return all_New_Bullet;
    }

    public int get_sort() {
        return _sort;
    }

    public boolean is_flag() {
        return _flag;
    }

    public void set_isOver(boolean _isOver) {
        this._isOver = _isOver;
    }

    public boolean is_isDown() {
        return _isDown;
    }

    public boolean is_isOver() {
        return _isOver;
    }

    public void set_isDown(boolean _isDown) {
        this._isDown = _isDown;
    }

    public void set_mario(Mario _mario) {
        this._mario = _mario;
    }
}
