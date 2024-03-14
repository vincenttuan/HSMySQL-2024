
package 摸彩;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {
    static List<Integer> nums;
    static List<Integer> o_nums = new ArrayList<>(); // 已經抽過的
    static int n;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("請輸入有效號碼(逗號隔開, 不可有空格):");
        String numString = sc.nextLine();
        Set<Integer> numsets = new LinkedHashSet<>();
        for(String num : numString.split(",")) {
            numsets.add(Integer.parseInt(num));
        }
        nums = new ArrayList<>(numsets);
        while (true) {            
            sc = new Scanner(System.in);
            System.out.println("按下 Enter 即可抽獎");
            sc.nextLine();
            do {
                int idx = new Random().nextInt(nums.size());
                n = nums.get(idx);
            }
            while(o_nums.stream().filter(i -> i == n).findAny().isPresent());
            System.out.println("抽中的號碼: " + n);
            o_nums.add(n);
            System.out.println("nums:" + nums);
            System.out.println("o_nums:" + o_nums);
            
            if(nums.size() == o_nums.size()) {
                System.out.println("抽獎 while 迴圈結束");
                break;
            }
        }
        System.out.println("程式結束");
    }
}
