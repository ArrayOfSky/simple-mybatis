package com.arrayofsky.arrayofskymybatissimple.test;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Double> score = new ArrayList<>();
        score.add(2.0);
        score.add(2.0);
        score.add(3.5);
        score.add(1.0);
        score.add(3.0);
        score.add(3.0);
        score.add(3.0);
        score.add(3.0);
        score.add(0.3);
        score.add(3.0);
        score.add(2.0);
        score.add(2.0);
        score.add(3.0);
        score.add(2.0);
        score.add(2.0);
        score.add(1.0);
        score.add(1.0);
        score.add(2.0);
        score.add(1.0);
        score.add(2.0);
        score.add(1.0);
        score.add(2.5);
        score.add(1.0);
        score.add(0.3);
        ArrayList<Integer> grade = new ArrayList<>();
        grade.add(88);
        grade.add(90);
        grade.add(65);
        grade.add(87);
        grade.add(85);
        grade.add(86);
        grade.add(81);
        grade.add(64);
        grade.add(90);
        grade.add(82);
        grade.add(86);
        grade.add(74);
        grade.add(82);
        grade.add(88);
        grade.add(80);
        grade.add(87);
        grade.add(92);
        grade.add(91);
        grade.add(82);
        grade.add(85);
        grade.add(80);
        grade.add(85);
        grade.add(94);
        grade.add(90);

        double num = 0;

        for(int i = 0;i<score.size();i++){
            double tempNum = score.get(i) * grade.get(i);
            num += tempNum;
        }

        double scoreCount = 0;
        for(int i = 0;i<score.size();i++){
            scoreCount += score.get(i);
        }
        System.out.println(scoreCount);

        scoreCount = 46.6;

        System.out.println(num);
        System.out.println(scoreCount);

        double temp = num/scoreCount;
        System.out.println(temp);

        double result = 0.7 * temp;
        System.out.println(result);

    }
}
