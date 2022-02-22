package com.tekion.cricket;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.services.MatchService;
import com.tekion.cricket.enums.MatchType;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(StringUtils.MATCH_TYPE);
        choice = sc.nextInt();
        MatchType matchType = (choice == 1 ? MatchType.SINGLE : MatchType.SERIES);
        MatchService matchService = new MatchService();
        switch (matchType) {
            case SINGLE:
                matchService.playSingleMatch();
                break;
            case SERIES:
                matchService.playSeries();
                break;
            default:
                System.out.println(StringUtils.MAIN_SWITCH_DEFAULT_MESSAGE);

        }
    }
}
