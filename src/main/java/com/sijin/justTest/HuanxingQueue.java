package com.sijin.justTest;

/**
 * @Author: 思进
 * @Description:
 * @Date: 下午5:18 2017/4/24
 */
public class HuanxingQueue {

    private Object[] m_pQueue;//队列
    private int m_iQueue;//队列元素个数
    private int m_iQueueCapacity;//队列数组容量
    private int m_iHead;//对头
    private int m_iTail;//队尾


    public static void main(String[] args){
        HuanxingQueue demo=new HuanxingQueue(5);
        demo.EnQueue("aaa");
        demo.EnQueue("bbb");
        demo.EnQueue("ccc");
        demo.EnQueue("ddd");
        demo.DeQueue();
        demo.DeQueue();
        demo.EnQueue("ffff");
        demo.EnQueue("vvvv");
        demo.queueTraverse();
    }

    //创建队列
    public HuanxingQueue(int num){
        m_iQueueCapacity=num;
        m_pQueue=new Object[num];
        clearQueue();

    }
    //销毁队列
    public void destryQueue(){
        m_pQueue=null;
    }
    //清空队列
    public void clearQueue(){
        m_iHead=0;
        m_iTail=0;
        m_iQueue=0;
    }
    //判断队列是否为空
    public boolean isEmpotyQueue(){
        if(m_iQueue==0){
            return true;
        }
        return false;

    }
    //判断队列是否已满
    public boolean queueFull(){
        if(m_iQueue==m_iQueueCapacity){
            return true;
        }
        return false;
    }
    //队列长度
    public int queueLength(){
        return m_iQueue;
    }
    //新增元素
    public boolean EnQueue(Object element){
        if(queueFull()){
            return false;
        }
        m_pQueue[m_iTail]=element;
        m_iTail++;
        m_iTail=m_iTail%m_iQueueCapacity;
        m_iQueue++;
        return true;

    }
    //删除元素
    public Object DeQueue(){
        if(isEmpotyQueue()){
            return null;
        }
        Object element=m_pQueue[m_iHead];
        m_iHead++;
        m_iHead=m_iHead%m_iQueueCapacity;
        m_iQueue--;
        return element;

    }
    //遍历队列
    public void queueTraverse(){
        for(int i=m_iHead;i<m_iQueue+m_iHead;i++){

            System.out.println(m_pQueue[i%m_iQueueCapacity]);


        }
    }
}
