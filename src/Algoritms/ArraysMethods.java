package Algoritms;

import java.util.Arrays;

public class ArraysMethods {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        //System.out.println(Arrays.toString(reverseArray(array)));
    }

    public static int[] reverseArray(int[] array) {
        //напишите тут ваш код
        int tmp;
        for (int i = (array.length-1), j = 0; i > (array.length/2)-1; i--, j++) {  //9
                tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
        }
        return array;
    }
}
