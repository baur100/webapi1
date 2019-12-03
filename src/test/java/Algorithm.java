import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    public static void main(String[] args) {
        System.out.println("Algirithms");
        ///////////FizzBuzz////////////
//        for(var i =1;i<100;i++){
//            fizzBuzz(i);
//            }
//        /////////////GetRemainder
        //       int reminder = getRemainder(20,6);
//        int reminder = getReminder1(6, 6);
//        System.out.println("Remainder = "+ reminder);
        //поменять две переменные местами swap 2 variables
//        int a=10;
//        int b=20;
//        a=a+b;
//        b=a-b;
//        a=a-b;

        //доказать что число симметричное Lucky string "tyuiuyt"
//       //////////reverse sentence
//        final String sentence ="Today is awful weather";
//        reverseSentence(sentence);
        /////////вывести на экран все повторяющиеся цыфры
        int[] array ={5,6,7,8,5,9,0,1,2,5,6};
        printRetited(array);


        }

    private static void printRetited(int[] array) {
        List repite = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    if (!repite.contains(array[i])) {
                        repite.add(array[i]);
                    }
                }
            }
            repite.forEach(x -> System.out.print(x));
        }
    }
    private static void reverseSentence(String sentence) {
        var words = sentence.split(" ");
        var reverse = "";
        for(var i=words.length-1;i>=0;i--){
            reverse+=words[i]+" ";
        }
        System.out.println(reverse);
    }

    private static boolean isLucky (String str){
        String reverse="";
        for(var i=str.length()-1;i>=0;i--){
            reverse+=str.charAt(i);

        }
        for(var i=0;i<str.length()/2;i++){
            if(str.charAt(i)!=reverse.charAt(i)){
                return false;
            }
        }
        return true;

//        private static int getReminder1(int a, int b) {
//            int x=a/b;
//            return a-x*b;
//       }
//        private static int getRemainder(int a, int b){
//        boolean cond = true;
//        if(a<b){
//            return a;
//
//        }
//        while(cond){
//            a= a-b;
//            if(a<b){
//                cond = false;
//            }
//        }
//        return a;
        }



    private static void fizzBuzz(int i) {
        if(i%5==0 && i%3==0){
            System.out.println("FizzBuzz");
    }else{
            if(i%3==0){
                System.out.println(i+" Fizz");
            }else{
                if(i%5==0){
                    System.out.println(i+" Bizz");
                }
            }
        }
}
}
