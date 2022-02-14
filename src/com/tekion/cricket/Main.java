package com.tekion.cricket;


import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        int choice;
        System.out.println("Press 1 to start a Match , Press 2 to start a series");
        Scanner sc =  new Scanner(System.in);
        choice = sc.nextInt();

        switch(choice){
            case 1:
                Match match = new Match();
                match.startMatch();
                match.printScoreCard();
                break;
            case 2:
                break;
            default:

        }


    }
}
