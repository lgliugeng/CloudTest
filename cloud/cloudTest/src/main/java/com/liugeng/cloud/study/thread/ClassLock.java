package com.liugeng.cloud.study.thread;

import java.math.BigDecimal;
import java.util.Random;

public class ClassLock {

    private static int year = 0;//年数

    private static int people = 1;//多少代人

    private static int dead = 0;//死去多人代人

    public static void main(String[] args) {
        Acount acount = new Acount();
        acount.add(100000000);//存入一亿
        while(acount.getCount()){//每循环一次代表一年，余额大于50万可继续循环
            Role bankRole = new Role(acount);
            Thread bank = new Thread(bankRole);
            bank.setName("银行");
            bank.start();//银行每年存入一次利息
            year++;//加一年
            System.out.println("第" + year + "年》》》》》》》》》》》》》》》》》消费情况：");
            for (int i = 0; i < (people - dead); i++) {//每代人每年花费随机数
                Thread t = new Thread(new Role(acount));
                t.setName(dead + (i+1) + "");
                t.start();
            }
            if(year%20 == 0){//每过二十年加一代人消费
                people++;
                System.out.println("第" + people + "代人开始消费");
            }
            if(dead == 0){
                if(year%20 == 0){//第一代人过二十年后死去
                    dead++;
                    System.out.println("第" + dead + "代人开始停止消费");
                }
            }else{
                if(year%60 == 0){//非第一代人每过60年死去
                    dead++;
                    System.out.println("第" + dead + "代人开始停止消费");

                }
            }
            try {//休眠1秒代表一年
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
        System.out.println("1亿元存入银行包含利息总共使用" + year + "年");
        System.out.println("1亿元存入银行包含利息总共被" + people + "代人使用过");
        System.out.println("1亿元存入银行包含利息用完前死去" + people + "代人");
    }

}

class Acount{
    private static BigDecimal count = new BigDecimal(0);

    public boolean getCount(){
        synchronized (Acount.class){
            System.out.println("当前余额：" + count);
            if(count.subtract(new BigDecimal(500000)).doubleValue()> 0){
                return true;
            }else{
                System.out.println("钱快用完了，准备开始工作吧");
                return false;
            }
        }
    }

    public synchronized void add(double add){
        count = count.add(new BigDecimal(add)).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println("第1代人存入：" + new BigDecimal(add).setScale(2,BigDecimal.ROUND_HALF_UP) + "元，余额：" + count);
    }

    public synchronized  void sub(double sub){
        if(!getCount()){
            System.out.println("钱用完了！");
            return;
        }
        count = count.subtract(new BigDecimal(sub)).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println("第" + Thread.currentThread().getName() + "代人取出：" + new BigDecimal(sub).setScale(2,BigDecimal.ROUND_HALF_UP) + "元，余额：" +count);
    }

    public synchronized void interest(){
        BigDecimal interest = count.multiply(new BigDecimal(0.027));
        count = count.add(interest).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(Thread.currentThread().getName() + "存入利息：" + interest.setScale(2,BigDecimal.ROUND_HALF_UP) + "元，余额：" + count);
    }
}

class Role implements Runnable{

    private Acount acount;

    public Role(Acount acount){
        this.acount = acount;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if("银行".equals(name)){
            acount.interest();
        }else{
            Random random = new Random();
            double money = random.nextInt(3000000) + 300000;
            acount.sub(money);
        }
    }
}
