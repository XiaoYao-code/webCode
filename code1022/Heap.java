package code1022;

import java.util.Arrays;

public class Heap {
    public int []elem;//底层是一个数组
    public  int  usedSize;//存放数据个数

    public Heap() {
        this.elem = new int[10];
    }
    public void creatHeap(int[] arr){
        for (int i = 0; i <arr.length; i++) {
            this.elem[i]=arr[i];
            this.usedSize++;
        }
        //从最后一个子树开始调整
        for (int i = (this.usedSize-1-1)/2; i >=0 ; i--) {//this.usedSize-1代表最后一个节点,(this.usedSize-1-1)/2每颗子树的根节点
                adjustDown(i,this.usedSize);
        }
    }
    public  void  adjustDown(int root,int len){//root代表每个子树的开始位置，len代表的是结束位置。
       int parent=root;
       int child=2*parent+1;//左孩子
       while (child<len){//孩子节点的下标一定会小于等总节点的数量-1。
           if(child+1<len){
               //有右孩子
               if (elem[child]<elem[child+1]){//找到左右孩子的最大值，child保存最大孩子的坐标
                   child=child+1;
               }
           }
           //比较child节点和父节点的值，如果大则交换
           if (elem[child]>elem[parent]){
               int tmp=elem[child];
               elem[child]=elem[parent];
               elem[parent]=tmp;
               parent=child;
               child=2*parent+1;
           }else {//如果小则不用交换，说明已经调整好了
               break;
           }
       }
    }
    public void  push(int value){
        //0.判断是否扩容
        if (this.elem.length==this.usedSize){

            this.elem= Arrays.copyOf(this.elem,elem.length*2);
        }
        elem[this.usedSize]=value;
        this.usedSize++;
        adjustUp(this.usedSize-1);
    }

    private void adjustUp(int child) {
        int parent=(child-1)/2;
        while (child>0){
            if (elem[parent]<elem[child]){
                int tmp=elem[parent];
                elem[parent]=elem[child];
                elem[child]=tmp;
                child=parent;
                parent=(child-1)/2;
            }else {
                break;
            }
        }

    }
  public void pop(){//每次移除的都是堆的元素
        if (this.usedSize==0){//是否为空
            return;
        }
        int tmp=this.elem[this.usedSize-1];
        this.elem[this.usedSize-1]=this.elem[0];
        this.elem[0]=tmp;
        this.usedSize--;//删除这个元素
        adjustDown(0,this.usedSize);

  }
    public void show(){
        for (int i = 0; i <this.usedSize ; i++) {
            System.out.print(elem[i]+" ");
        }
    }
}
