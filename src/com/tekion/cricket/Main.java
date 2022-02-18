package com.tekion.cricket;
import com.tekion.cricket.controllers.MatchController;
import com.tekion.cricket.enums.MatchType;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Press 1 to start a Match , Press 2 to start a series");
        choice = sc.nextInt();
        MatchType matchType = (choice == 1 ? MatchType.SINGLE : MatchType.SERIES);
        MatchController matchController = new MatchController();
        switch (matchType) {
            case SINGLE:
                matchController.playSingleMatch();
                break;
            case SERIES:
                matchController.playSeries();
                break;
            default:
                System.out.println("Please enter either 1 or 2 !!");

        }
    }
}
