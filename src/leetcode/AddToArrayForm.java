package leetcode;

import java.util.ArrayList;
import java.util.List;

public class AddToArrayForm {
    public static void main(String[] args) {

    }

    public static List<Integer> addToArrayForm(int[] num, int k) {
//        int carry = 0;
//        List<Integer> ans = new ArrayList<>();
//        int i = num.length - 1;
//        while (i >= 0 || carry > 0 || k > 0) {
//            int toadd = k % 10 + carry + (i >= 0 ? num[i--] : 0);
//            carry = toadd / 10;
//            toadd = toadd % 10;
//            k = k / 10;
//            ans.add(0, toadd);
//        }
//        return ans;
        int tmp;
        int c = 0;
        long res = 0;
        for (int i = num.length - 1, n = 0; i >= 0; --i, n++) {
            long pos = (long) Math.pow(10, i);
            res += num[n] * pos;
        }
        res += k;
        long j = res;
        for (; j > 0; j /= 10) {
            c += 1;
        }
        int[] arr = new int[c];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] += res % 10;
            res /= 10;
        }

        for (int i = (arr.length - 1), x = 0; i > (arr.length / 2) - 1; i--, x++) {
            tmp = arr[i];
            arr[i] = arr[x];
            arr[x] = tmp;
        }
        List<Integer> list = new ArrayList<>();
        for (int value : arr) {
            list.add(value);
        }
        return list;
    }
}
