package code1022;

public class test {
    public  static  void  mergeSort(int []arr){
        merge1(arr,0,arr.length-1);

    }

    private static void merge1(int []arr, int left, int right) {//分解方法
        if (left>=right){
            return;
        }
        int mid=(left+right)/2;
        merge1(arr,left,mid);
        merge1(arr,mid+1,right);//两个递归走完代表已经分解成一个一个元素
        merge2(arr,mid,left,right);//合并
    }

    private static void merge2(int[] arr, int mid, int left, int right) {//归并方法
      int  s1=left;
      int  s2=mid+1;

      int len=right-left+1;
      int[]ret=new int[len];//归并需要最大的空间，来存储每次归并后的数组
        int i=0;
      while (s1<=mid&&s2<=right){ //证明两段都有数据
         if (arr[s1]<=arr[s2]){
             ret[i++]=arr[s1++];
         }else {
             ret[i++]=arr[s2++];
         }
      }
      while (s1<=mid){
          ret[i++]=arr[s1++];
      }
      while (s2<=right){
          ret[i++]=arr[s2++];
      }
        for (int j = 0; j <ret.length ; j++) {//进行数组拷贝
          arr[j+left]=ret[j];
        }
    }

    public static  int   partition(int[] arr, int left, int right){
        int tmp=arr[left];
        while (left<right){
           while (left<right&&arr[right]>=tmp){//找到右边比tmp小的值
               right--;
           }
           arr[left]=arr[right];//把当前位置赋值这个找到的小的值
            while (left<right&&arr[left]<=tmp) {//找到左边比tmp大的值
                left++;
            }
            arr[right]=arr[left];//把当前位置赋值给这个找到的大的值
        }
        arr[left]=tmp;
        return left;
    }
    public static void quick(int []arr,int left,int right){
        if (left>=right){
            return;
        }
        int par=partition(arr,left,right);
        quick(arr,left,par-1);
        quick(arr,par+1,right);
    }
 public static void quickSort(int []arr){
    quick(arr,0,arr.length-1);
 }
    public  static void bubbleSort  (int[]arr){
        for (int i = 0; i <arr.length ; i++) {
            boolean  flag=false;//优化标志
            for (int j = 0; j <arr.length-1-i ; j++) {
               if (arr[j]>arr[j+1]){
                   int tmp=arr[j];
                   arr[j]=arr[j+1];
                   arr[j+1]=tmp;
                   flag=true;//发生交换则说明数组内部存在无序
               }
            }
            if (flag==false){//如果没有发生过交换，则说明这趟下来已经有序。直接返回即可。
                return;
            }
        }
    }
    public int[] elem;//底层是一个数组
    public int usedSize;//存放数据个数
    //堆排序
    //1.建堆
    //从最后一棵子树的根节点开始遍历，创造堆
    public void createHeap(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            this.elem[i] = arr[i];
            this.usedSize++;
        }
    }
        public void heapSort ( int[] arr){
            int end = arr.length - 1;
            while (end > 0) {
                int tmp = arr[0];
                arr[0] = arr[end];
                arr[end] = tmp;
                adjustDown(0, end);
                end--;
            }
        }
        public void adjustDown ( int root, int len){//root代表每个子树的开始位置，len代表的是结束位置。
            int parent = root;
            int child = 2 * parent + 1;//左孩子
            while (child < len) {//孩子节点的下标一定会小于等总节点的数量-1。
                if (child + 1 < len) {
                    //有右孩子
                    if (elem[child] < elem[child + 1]) {//找到左右孩子的最大值，child保存最大孩子的坐标
                        child = child + 1;
                    }
                }
                //比较child节点和父节点的值，如果大则交换
                if (elem[child] > elem[parent]) {
                    int tmp = elem[child];
                    elem[child] = elem[parent];
                    elem[parent] = tmp;
                    parent = child;
                    child = 2 * parent + 1;
                } else {//如果小则不用交换，说明已经调整好了
                    break;
                }
            }
        }


        //选择排序的基本思想是，从第一个位置开始。每次与该位置之后的元素作比较，如果大于后面元素则交换，
        // 直到遍历完该位置后边的所有元素再遍历第二个位置以此类推
        public static int[] selectSort ( int[] arr){
            for (int i = 0; i < arr.length; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] > arr[j]) {
                        int tmp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = tmp;
                    }
                }
            }
            return arr;
        }
        public static void main (String[]args){
            int[] arr = new int[]{1, 3, 5, 6, 7, 9, 4, 8, 2, 10};
            //bubbleSort(arr);
            //maopao(arr);
            //insertSort(arr);
            // selectSort(arr);
      /*  Heap heap=new Heap();
        heap.creatHeap(arr);
        heap.show();*/
         //   quickSort(arr);
            mergeSort(arr);
        for (int i = 0; i <10 ; i++) {
            System.out.print(arr[i]+" ");
        }
        }

    }

