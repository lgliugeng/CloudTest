package com.liugeng.cloud.study.thread.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int poolSize){
        if(poolSize > 0){
            // 初始化连接池，并加入到队列中
            for (int i = 0; i < poolSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if(null != connection){
            // 加入释放的连接
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public static void main(String[] args) {

    }

    public Connection fetchConnection(long mills) throws Exception{
        synchronized (pool){
            // 超时
            if (mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                // mills毫秒内没有没有获取连接将返回null
                while (pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection conn = null;
                if(!pool.isEmpty()){
                    conn = pool.removeFirst();
                }
                return conn;
            }
        }
    }
}
