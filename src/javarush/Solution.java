package javarush;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        //task11();
    }

    public static void task1() {
        int[] firstArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] secondArray = new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        int[] resultArray;
        resultArray = new int[firstArray.length + secondArray.length];
        for (int i = 0; i < firstArray.length; i++) {
            resultArray[i] = firstArray[i];
        }
        for (int i = firstArray.length, n = 0; i < secondArray.length + firstArray.length; n++, i++) {
            resultArray[i] = secondArray[n];
        }
        for (int i = 0; i < resultArray.length; i++) {
            System.out.print(resultArray[i] + ", ");
        }
    }

    public static void task2() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        if (n > 0) {
            for (int i = 0; i < array.length; i++) {
                array[i] = in.nextInt();
            }
            if (n % 2 == 0) {
                for (int i = array.length - 1; i >= 0; i--) {
                    System.out.println(array[i]);
                }
            } else {
                for (int i = 0; i <= array.length - 1; i++) {
                    System.out.println(array[i]);
                }
            }
        }
    }

    public static void task3() {
        int[] array;
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        array = new int[n];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            array[i] = in.nextInt();
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        System.out.println(min);
    }

    public static void task4() {
        String[] strings;
        Scanner in = new Scanner(System.in);
        strings = new String[6];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = in.nextLine();
        }
//        for(int i = 0; i < strings.length; i++){
//            for (int j = 0; j<strings.length; j++){
//                if(strings[i] == null || strings[j] == null){
//                    continue;
//                }
//                else if (strings[j].equals(strings[i]) && (i != j)){
//                    strings[j] = null;
//                }
//            }
//        }
        for (int i = 0; i < strings.length; i++) {
            String currentString = strings[i];
            for (int j = i + 1; j < strings.length; j++) {
                if (currentString == null) {
                    break;
                }
                if (currentString.equals(strings[j])) {
                    strings[j] = null;
                    strings[i] = null;
                }
            }
        }

        for (int i = 0; i < strings.length; i++) {
            System.out.print(strings[i] + ", ");
        }
    }

    public static void task5() {
        /**
         * 	Каждая выведенная строка должна содержать 10 чисел, разделенных пробелом.
         *  Выведенные числа должны быть таблицей умножения.
         */
        int[][] table;
        table = new int[10][10];

        for (int i = 0; i < table.length; i++) {
            for (int n = 0; n < table[i].length; n++) {
                table[i][n] = (n + 1) * (i + 1);
            }
        }

        for (int[] ints : table) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public static void task6() {
        /**
         * Создай треугольный массив, где значение каждого элемента — это сумма его индексов
         * Выведенный текст должен содержать 10 строк.
         */
        int[][] result = new int[10][];
        for (int i = 0; i < 10; i++) {
            result[i] = new int[i + 1];
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
                result[i][j] = i + j;
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void task7() {
        /**
         * количеством строк N;
         * строки — массивами, размер которых соответствует числам, введенным в п.2 (в порядке ввода).
         */
        int[][] multiArray;
        Scanner in = new Scanner(System.in);
        int n1 = in.nextInt();


        multiArray = new int[n1][];
        for (int i = 0; i < n1; i++) {
            multiArray[i] = new int[in.nextInt()];

        }
        for (int i = 0; i < multiArray.length; i++) {
            for (int j = 0; j < multiArray[i].length; j++)
                System.out.print(multiArray[i][j] + " ");
            System.out.println();
        }
        //System.out.println(multiArray[1][3]);
    }

    public static void task8() {
        int[][][] multiArray = new int[][][]{{{4, 8, 15}, {16}}, {{23, 42}, {}}, {{1}, {2}, {3}, {4, 5}}};
        for (int[][] x : multiArray){
            for (int[] y : x){
                for (int z : y){
                    System.out.println(z);
                }
            }
        }
    }

    public static void task9(){
        int[] array = new int[21];
        int valueStart = 10;
        int valueEnd = 13;
        int i = (array.length)/2;
        int j = ((array.length)/2) + 1;
        if((array.length) % 2 == 0){
            Arrays.fill(array, 0,i , valueStart);
            Arrays.fill(array,i,(array.length), valueEnd);
        }
        else{
            Arrays.fill(array, 0,j , valueStart);
            Arrays.fill(array,j,(array.length), valueEnd);
        }
        System.out.println(Arrays.toString(array));
    }

    public static void task10(){
        /**
         * Реализуй метод, который делит массив array на два подмассива
         * и заполняет ими двумерный массив result. Если длина массива нечетная,
         * то большую часть нужно скопировать в первый подмассив.
         * Для разделения массива используй метод Arrays.copyOfRange(int[], int, int). Порядок элементов не меняй.
         * При тестировании значения будут разными.
         */
        int[][] result = new int[2][];
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        int i = (array.length)/2;
        int j = ((array.length)/2) + 1;

        if((array.length) % 2 == 0) {
            int[] x = Arrays.copyOfRange(array,0, i );
            int[] y = Arrays.copyOfRange(array,i,array.length);
            result[0] = x;
            result[1] = y;
        }
        else {
            int[] x = Arrays.copyOfRange(array,0, j );
            int[] y = Arrays.copyOfRange(array,j,array.length);
            result[0] = x;
            result[1] = y;
        }
        System.out.println(Arrays.deepToString(result));

    }

    public static void task11(){
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int element = 5;

        int[] array2 = Arrays.copyOf(array, array.length);
        Arrays.sort(array2);
        if (Arrays.binarySearch(array2, element) >=0){
            System.out.println(true);
        }
        else {
            System.out.println(false);
        }
    }

}
