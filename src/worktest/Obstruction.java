package worktest;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable { //线程仅针对马里奥利用旗帜下降时

    private int _x;
    private int _y;  //建筑物的位置

    private int _xStart;
    private int _yStart;  //建筑物初始位置

    private int _type;
    private int _startType;

    private int Obstruction_width = 30;
    private int Obstruction_height = 30;

    private final int flag_width = 25;
    private final int flag_height = 360;

    private boolean is_Pause = false;

    private BufferedImage _ImageShow = null;

    private BackGround _BackGround;

    private Thread _t = new Thread(this);

    public Obstruction(int x, int y, int type, BackGround backGround) {
        this._x = x;
        this._y = y;
        this._xStart = x;
        this._yStart = y;
        this._type = type;
        this._startType = type;
        this._BackGround = backGround;
        this._ImageShow = StaticValue.allObstructionImage.get(type);
        this.Obstruction_height = 30;
        this.Obstruction_width = 30;
        if (this._type == 2) {
            this.Obstruction_height = flag_height;
            this.Obstruction_width = flag_width;
        }
        if (this._type == 3) {
            this.Obstruction_height = 60;
            this.Obstruction_width = 60;
            _t.start();
        }


    }

    public void reset() {
        this._x = _xStart;
        this._y = _yStart;
        this._type = _startType;
        this._ImageShow = StaticValue.allObstructionImage.get(this._type);
    }

    @Override
    public void run() {
        while (true) {
            if (!this.is_Pause) {

                if (this._BackGround.is_isOver()) {
                    if (this._y < 480) {
                        this._y += 7.5;
                    } else {
                        this._BackGround.set_isDown(true);
                    }
                }

            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }

    public int get_type() {
        return _type;
    }

    public BufferedImage get_ImageShow() {
        return _ImageShow;
    }

    public void setIs_Pause(boolean is_Pause) {
        this.is_Pause = is_Pause;
    }

    public int getObstruction_height() {
        return Obstruction_height;
    }

    public int getObstruction_width() {
        return Obstruction_width;
    }
}
