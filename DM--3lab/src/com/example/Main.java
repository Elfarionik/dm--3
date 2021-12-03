package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String f = "00110101";

        System.out.println("Задана функція:");
        System.out.println(f);

        System.out.println("Таблиця Істиності:");
        System.out.print(" " + Arrays.toString(truthTable(f)));

        System.out.println("Двоіста функція:");
        System.out.println(dvoista(f));

        System.out.println("ДДНФ:");
        System.out.println(ddnf(f));

        System.out.println("ДКНФ:");
        System.out.println(dcnf(f));

        System.out.println("Поліном Жигалкіна:");
        System.out.println(jigalkin(f));

        System.out.print("Чи зберігая функця константу 0?");
        System.out.println(zeroConst(f));

        System.out.print("Чи зберігая функця константу 1?");
        System.out.println(oneConst(f));

        System.out.print("Чи є задана функція самодвоістою?");
        System.out.println(selfDvoista(f));

        System.out.println("Чи є задана функція монотонною?");
        System.out.println(monotonna(f));

        System.out.println("Чи є задана функція лінійною");
        System.out.println(liniear(f));
    }

    static String[] truthTable(String f) {
        String[] x = f.split("");
        return new String[]{"x y z  F\n", "0 0 0  " + x[0] + "\n", "0 0 1  " + x[1] + "\n", "0 1 0  " + x[2] + "\n", "0 1 1  " + x[3] + "\n", "1 0 0  "
                + x[4] + "\n", "1 0 1  " + x[5] + "\n", "1 1 0  " + x[6] + "\n", "1 1 1  " + x[7] + "\n"};


    }

    static String dvoista(String f) {
        StringBuilder res = new StringBuilder();
        String[] x = f.split("");
        for (int i = x.length - 1; i >= 0; i--) {
            res.append(Integer.parseInt(x[i]) ^ 1);
        }
        return res.toString();
    }

    static String ddnf(String f) {
        StringBuilder res = new StringBuilder();
        String[] table = truthTable(f);
        List<Integer> packs = new ArrayList<>();
        for (int i = 1; i < table.length; i++) {
            if (table[i].charAt(7) == '1') packs.add(i);
        }
        for(Integer i: packs){
            res.append(tryToDeny(Character.digit(table[i].charAt(0), 10), 'x') +
                                  tryToDeny(Character.digit(table[i].charAt(2), 10), 'y') +
                                      tryToDeny(Character.digit(table[i].charAt(4), 10), 'z') +" ");
            res.append("v ");
        }
        res.deleteCharAt(res.length()-2);
        return res.toString();
    }

    private static String tryToDeny(int i, Character x){
        if(i == 1) return x.toString();
        if(i == 0) return x.toString() + "`";
        else return "Не бинарное значение?";
    }
    static String dcnf(String f) {
        StringBuilder res = new StringBuilder();
        String[] table = truthTable(f);
        List<Integer> packs = new ArrayList<>();
        for (int i = 1; i < table.length; i++) {
            if (table[i].charAt(7) == '0') packs.add(i);
        }
        for(Integer i: packs){
           res.append(tryToDeny(Character.digit(table[i].charAt(0), 10)^1, 'x') + "v" +
                   tryToDeny(Character.digit(table[i].charAt(2), 10)^1, 'y') + "v" +
                   tryToDeny(Character.digit(table[i].charAt(4), 10)^1, 'z') +" ");
            res.append("^ ");
        }
        res.deleteCharAt(res.length()-1);
        res.deleteCharAt(res.length()-2);
        return res.toString();
    }
    static String jigalkin(String f){
        StringBuilder res = new StringBuilder();
        String[][] triangle = new String[8][8];
        triangle[0] = f.split("");
        String[] base = {"z", "y", "yz", "x", "xz", "xy", "xyz"};
        for(int i = 1; i < triangle.length; i++){
            for(int k = 0; k < triangle[i].length - i; k++){
                triangle[i][k] = String.valueOf(Integer.parseInt(triangle[i-1][k]) ^ Integer.parseInt(triangle[i-1][k+1]));
            }
        }

        for(int i = 1; i < triangle.length; i++){
            if (triangle[i][0].equals("1")) res.append(base[i-1] + " + ");
        }
        res.deleteCharAt(res.length()-2);
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }
    static boolean zeroConst(String f){
        if (f.split("")[0].equals("0")) return true;
        return false;
    }
    static boolean oneConst(String f){
        if (f.split("")[f.length()-1].equals("1")) return true;
        return true;
    }
    static boolean selfDvoista(String f){
        return dvoista(f).equals(f);
    }
    static boolean monotonna(String f){
        String[] x = f.split("");
        return x[0].compareTo(x[1]) <= 0 && x[0].compareTo(x[2]) <= 0 && x[0].compareTo(x[4]) <= 0
                && x[1].compareTo(x[3]) <= 0 && x[1].compareTo(x[5]) <= 0 &&
                x[2].compareTo(x[3]) <= 0 && x[2].compareTo(x[6]) <= 0 &&
                x[4].compareTo(x[5]) <= 0 && x[4].compareTo(x[6]) <= 0 &&
                x[4].compareTo(x[7]) <= 0 && x[5].compareTo(x[7]) <= 0 && x[6].compareTo(x[7]) <= 0;
    }
    static boolean liniear(String f){
        String polinom = jigalkin(f);
        String[] x = polinom.split(" \\+ ");
        for(String y:x){
            y = y.trim();
            if(y.length() > 1) return false;
        }
        return true;
    }

}

