import java.util.Scanner;

public class test {
    public static void main(String[] args) {

        Scanner in=new Scanner(System.in);
        while(in.hasNext()){
            String firstnum=in.next().substring(2);

            String finalnum=Integer.valueOf(firstnum,16).toString() ;
            System.out.println(finalnum);
        }
    }
}
