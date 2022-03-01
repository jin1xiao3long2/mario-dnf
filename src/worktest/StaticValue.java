package worktest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {

    public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>(); // 导入马里奥图片缓存

    public static BufferedImage BackGroundImage = null;                           //导入背景图片缓存

    public static BufferedImage StartGroundImage = null;                     //导入开始背景图片

    public static BufferedImage DeadGroundImage = null;                      //导入死亡背景图片

    public static BufferedImage EndGroundImage = null;                        //最后一关背景图片

    public static BufferedImage WinGroundImage = null;                       //导入胜利的图片

    public static BufferedImage PauseGorundImage = null;                        //导入暂停的图片

    public static BufferedImage HelpGroundImage = null;                         //导入帮助的图片

    public static List<BufferedImage> allGebulinImage = new ArrayList<BufferedImage>();   //导入哥布林图片缓存

    public static List<BufferedImage> allTriangleImage = new ArrayList<BufferedImage>(); //导入栗子怪缓存

    public static List<BufferedImage> allObstructionImage = new ArrayList<BufferedImage>(); //导入砖块图片

    public static List<BufferedImage> allEnemyBloodImage = new ArrayList<BufferedImage>();

    public static List<BufferedImage> allBulletImage = new ArrayList<BufferedImage>();

    public static List<BufferedImage> allFireImage = new ArrayList<BufferedImage>();

    public static BufferedImage MarioDeadLeftImage = null;

    public static BufferedImage MarioDeadRightImage = null;

    public static BufferedImage MarioChooseImage = null;


    private final static int MarioPictureNum = 18;

    private final static int TrianglePictureNum = 4;

    private final static int ObstrutionPictureNum = 4;

    private final static int EnemyBloodPictureNum = 5;

    private final static int BulletPictureNum = 4;

    private final static int FirePictureNum = 2;

    private final static int GebulinPictureNum = 6;


    public static void init() {
        for (int i = 0; i < MarioPictureNum; i++) {
            try {
                allMarioImage.add(ImageIO.read(new File("images/Mario/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < BulletPictureNum; i++) {
            try {
                allBulletImage.add(ImageIO.read(new File("images/Bullet/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < EnemyBloodPictureNum; i++) {
            try {
                allEnemyBloodImage.add(ImageIO.read(new File("images/EnemyBlood/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BackGroundImage = ImageIO.read(new File("images/BackGround/backGround.png"));
            StartGroundImage = ImageIO.read(new File("images/BackGround/startGround.png"));
            DeadGroundImage = ImageIO.read(new File("images/BackGround/deadGround.png"));
            EndGroundImage = ImageIO.read(new File("images/BackGround/endGround.png"));
            WinGroundImage = ImageIO.read(new File("images/BackGround/WinGround.png"));
            PauseGorundImage = ImageIO.read(new File("images/BackGround/PauseGround.png"));
            HelpGroundImage = ImageIO.read(new File("images/BackGround/HelpGround.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ObstrutionPictureNum; i++) {
            try {
                allObstructionImage.add(ImageIO.read(new File("images/Obstruction/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < FirePictureNum; i++) {
            try {
                allFireImage.add(ImageIO.read(new File("images/Mario/Fire" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GebulinPictureNum; i++)
        {
            try {
                allGebulinImage.add(ImageIO.read(new File("images/Enemy/Gebulin/" + i + ".png")));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < TrianglePictureNum; i++) {
            try {
                allTriangleImage.add(ImageIO.read(new File("images/Enemy/Triangle/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        try {
            MarioChooseImage = ImageIO.read(new File("images/Mario/choose.png"));
            MarioDeadLeftImage = ImageIO.read(new File("images/Mario/overLeft.png"));
            MarioDeadRightImage = ImageIO.read(new File("images/Mario/overRight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //导入各种图片
    }
}


