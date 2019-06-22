package com.liugeng.cloud.study.queue;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class PriorityQueueDemo {

    public static void main(String[] args) {
        //由大到小排序距离
        Comparator<PointComparator> distanctCopmarator = new Comparator<PointComparator>() {
            @Override
            public int compare(PointComparator o1, PointComparator o2) {
                if(o1.distance < o2.distance){
                    return 1;
                }
                if(o1.distance > o2.distance){
                    return -1;
                }
                return 0;
            }
        };

        Queue<PointComparator> priorityQueue = new PriorityQueue<>(10,distanctCopmarator);
        for (int i = 0;i < 8;i++){
            Random r = new Random(2);
            int ir = r.nextInt(10);
            Point p1 = new Point(i* (ir+1)+1,i*(ir+2)+2);
            Point p2 = new Point(i*(ir+3)+3,i*(ir+4)+4);
            PointComparator pointComparator = new PointComparator(p1,p2);
            priorityQueue.add(pointComparator);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        for (int i = 0;i < 8;i++){
            System.out.println(df.format(priorityQueue.poll().distance));
            System.out.println("队列数量：" + priorityQueue.size());
        }
    }

}

//定义一个点
class Point{
    double x;

    double y;

    public Point(double x,double y){
        this.x = x;
        this.y = y;
    }
}

//计算距离
class PointComparator{

    Point onePoint;

    Point twoPoint;

    public double distance;

    public PointComparator(Point onePoint,Point twoPoint){
        this.onePoint = onePoint;
        this.twoPoint = twoPoint;
        computeDistance();
    }

    private void computeDistance(){
        double val = Math.pow((this.onePoint.x - this.twoPoint.x),2) + Math.pow((this.onePoint.y - this.twoPoint.y),2);
        this.distance = Math.sqrt(val);
    }
}
