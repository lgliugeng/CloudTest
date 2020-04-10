package com.liugeng.cloud.study;

import java.util.Random;

/**
 * 抽奖（1% 一等奖 5% 二等奖 8% 三等奖 12% 四等奖 15% 五等奖 59% 六等奖）
 */
public class Lottery {

    public static void main(String[] args) {
        int value = new Random().nextInt(10000) + 1;
        System.out.print("当前幸运号：" + value + " ");
        if (value == 1) {
            System.out.println("抽中一等奖");
        } else if (value <= 6) {
            System.out.println("抽中二等奖");
        } else if (value <= 14) {
            System.out.println("抽中三等奖");
        } else if (value <= 26) {
            System.out.println("抽中四等奖");
        } else if (value <= 41) {
            System.out.println("抽中五等奖");
        } else if (value <= 100){
            System.out.println("抽中六等奖");
        } else {
            System.out.println("谢谢惠顾");
        }
    }
}
