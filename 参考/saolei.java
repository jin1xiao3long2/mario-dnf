package saolei;

import java.util.Random;
import java.util.Scanner;

public class saolei {

      public static void main(String[] args)
      {
        saolei a=new saolei(3,2);
        while(true)//1代表要开始扫雷 其他代表插旗
        {
            a.show();
            Scanner  sca=new Scanner(System.in);
            if(sca.nextInt()==1)
            {
                a.dianji();
            }
            else {
                a.chaqi();
            }
            if(!a.getzhuangtai())
            {
                break;
            }
        }
        a.showall();
    }
    int length;//对于扫雷的接口问题
    int shuzi[][];//对于格子的显示数字问题
    int stage[][];//对于每个格子的状态问题 0代表普通 1代表有雷 2代表有旗子
    boolean  fankai[][];//对于每个格子是否翻开问题 false代表不掀开 true代表展示
    int leishu;//雷的个数
    int qishu;//能插的旗的数量
    int kongbaige;//用于判断是否直接结束
    boolean juzhuangtai=false;//局状态初始为false
    saolei(int len,int num)//生成len*len 的一个棋盘 ,num 代表的是雷的个数
    {
        juzhuangtai=true;
        length = len;
        qishu=num;
        leishu=num;
        kongbaige=len*len;
        shuzi = new int[len][len];
        stage = new int[len][len];
        fankai=new boolean[len][len];
        //对两个数组进行初始化
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                shuzi[i][j] = 0;
                stage[i][j] = 0;
                fankai[i][j]=false;
            }
        }
        //生成两个随机数
        Random x = new Random();
        Random y = new Random();
        //对于生成雷的数目进行变化
        int leinum = num;
        while (leinum > 0) {
            if (stage[x.nextInt(len)][y.nextInt(len)] == 0) {
                stage[x.nextInt(len)][y.nextInt(len)] = 1;
                leinum--;
            }
        }
        //对于数字数组的初始化
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (stage[i][j] == 0) {
                    if (i > 0 && i < len - 1) {
                        if (j > 0 && j < len - 1) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 3; n++) {
                                for (int k = 0; k < 3; k++) {
                                    if (stage[i - n + 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else if (j == 0) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 3; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i - n + 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 3; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i - n + 1][j + k - 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        }
                    } else if (i == 0) {
                        if (j > 0 && j < len - 1) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 3; k++) {
                                    if (stage[i - n + 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else if (j == 0) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i - n + 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i - n + 1][j + k - 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        }
                    } else {
                        if (j > 0 && j < len - 1) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 3; k++) {
                                    if (stage[i + n - 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else if (j == 0) {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i + n - 1][j - k + 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        } else {
                            int xianshi = 0;//显示的数字是多少
                            for (int n = 0; n < 2; n++) {
                                for (int k = 0; k < 2; k++) {
                                    if (stage[i + n - 1][j + k - 1] == 1) {
                                        xianshi++;
                                    }
                                }
                            }
                            shuzi[i][j] = xianshi;
                        }
                    }
                }
            }

        }
    }
    //对于x和y坐标的点击扫雷
    void dianji() {
        if (juzhuangtai) {
            Scanner sc = new Scanner(System.in);
            //对于x ，y坐标的使用
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x < length && y < length) {
                int m=judgedainji(x, y);
                if (m==1) {
                    over();
                }
                else if(m==3) { shengli();}
                else {

                }
        }
    }
    }
    //插旗问题
    void chaqi()
    {
        if (juzhuangtai) {
            Scanner sc = new Scanner(System.in);
            //对于x ，y坐标的使用
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x < length && y < length) {
                if (judgechaqi(x, y)==2) {
                    shengli();
                }
                else  {
                }
            }
        }
    }
    //递归显示出来这个点击后的效果
    void diguixianshi(int x,int y)
    {
        if(shuzi[x][y]==0&&stage[x][y]==0&&fankai[x][y]==false)
        {
            fankai[x][y]=true;
            kongbaige--;
            if(x>0&&x<length-1)
            {
                if(y>0&&y<length-1) {
                    diguixianshi(x - 1, y - 1); diguixianshi(x - 1, y);    diguixianshi(x - 1, y + 1);
                    diguixianshi(x, y - 1);diguixianshi(x, y + 1);
                    diguixianshi(x + 1, y - 1);diguixianshi(x + 1, y);diguixianshi(x + 1, y + 1);
                }
                else if(y==0)
                {
                     diguixianshi(x - 1, y);    diguixianshi(x - 1, y + 1);
                    diguixianshi(x, y + 1);
                    diguixianshi(x + 1, y);diguixianshi(x + 1, y + 1);
                }
                else
                {
                    diguixianshi(x - 1, y - 1); diguixianshi(x - 1, y);
                    diguixianshi(x, y - 1);
                    diguixianshi(x + 1, y - 1);diguixianshi(x + 1, y);
                }
            }
            else if(x==0)
            {
                if(y>0&&y<length-1) {
                    diguixianshi(x, y - 1);diguixianshi(x, y + 1);
                    diguixianshi(x + 1, y - 1);diguixianshi(x + 1, y);diguixianshi(x + 1, y + 1);
                }
                else if(y==0)
                {
                    diguixianshi(x, y + 1);
                    diguixianshi(x + 1, y);diguixianshi(x + 1, y + 1);
                }
                else
                {
                    diguixianshi(x, y - 1);
                    diguixianshi(x + 1, y - 1);diguixianshi(x + 1, y);
                }
            }
            else
            {
                if(y>0&&y<length-1) {
                    diguixianshi(x - 1, y - 1); diguixianshi(x - 1, y);    diguixianshi(x - 1, y + 1);
                    diguixianshi(x, y - 1);diguixianshi(x, y + 1);
                }
                else if(y==0)
                {
                    diguixianshi(x - 1, y);    diguixianshi(x - 1, y + 1);
                    diguixianshi(x, y + 1);
                }
                else
                {
                    diguixianshi(x - 1, y - 1); diguixianshi(x - 1, y);
                    diguixianshi(x, y - 1);
                }
            }
        }
        else if(stage[x][y]==0)
        {
            if(fankai[x][y]==false) {
                fankai[x][y] = true;
                kongbaige--;
            }
        }
    }
    //判断情况问题 1代表扫到雷结束 2代表继续游戏 3代表胜利
    int judgedainji(int x, int y)
    {
        if(stage[x][y]==1)
        {
            return 1;
        }
        else if(stage[x][y]==2)
        {
            System.out.println("插旗之后不能左击");
        }
        else
        {
            diguixianshi(x,y);
            if(kongbaige==leishu)
            {
                return 3;
            }
        }
        return 2;
    }
    //判断情况 1代表继续，2代表胜利
    int judgechaqi(int x,int y)
    {
        if(qishu>0)
        {
            stage[x][y]=2;
            qishu--;
            if(qishu==0)
            {
                int num=0;
                for(int i=0;i<length;i++)
                {
                    for(int j=0;j<length;j++)
                    {
                        if(stage[i][j]==1)
                        {
                            num++;
                        }
                    }
                }
                if(num==0)return 1;

            }
            else
            {

            }
        }
        else
        {
            System.out.println("旗子不够了");
        }
        return 1;
    }
    //胜利之后
    void shengli()
    {
        juzhuangtai=false;
        System.out.println("你获胜了");
    }
    //游戏结束
    void over()
    {
        juzhuangtai=false;
        System.out.println("你失败了");
    }
    //展示函数 *代表不让看 $代表有旗子
    void show()
    {
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(fankai[i][j])
                {
                        System.out.print(shuzi[i][j]+" ");
                }
                else if(stage[i][j]==2)
                {
                    System.out.print("$ ");
                }
                else
                {
                    System.out.print("* ");
                }
            }
            System.out.println(" ");
        }
    }
    boolean getzhuangtai()
    {
        return juzhuangtai;
    }
    void showall()
    {
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(stage[i][j]==0)
                {
                    System.out.print(shuzi[i][j]+" ");
                }
                else  System.out.print("* ");
            }
            System.out.println(" ");
        }
    }
}
