package com.tekion.cricket.constants;

import org.omg.CORBA.PUBLIC_MEMBER;

public class StringUtils {
    public static String[] FIRST_TEAM_PLAYERS = {"Shikhar", "Rohit", "Virat", "SKY", "Rishabh", "Hardik", "Chahal", "Shami", "Bumrah", "Siraj", "Kuldeep"};
    public static String[] SECOND_TEAM_PLAYERS = {"Warner", "Finch", "Steve", "Alex", "Wade", "Maxwell", "Marsh", "Starc", "Mitch", "Josh", "kane"};
    public static String FIRST_TEAM_NAME = "India";
    public static String SECOND_TEAM_NAME = "Australia";
    public static String MYSQL_URL = "jdbc:mysql://localhost:3306/cricket?autoReconnect=true&useSSL=false";
    public static String MYSQL_USER = "root";
    public static String MATCHES_TO_BE_PLAYED = "Enter total Number of Matches to be played: ";
    public static String DOT_LINE = "------------------------------------------------------------------";
    public static String SMALL_DOT_LINE = "-------------------------------";
    public static String BIG_DOT_LINE = "--------------------------------------------------------------------------------------------------------------------------------------------------------------";
    public static String SCORECARD = "---------------------------SCORECARD------------------------------";
    public static String MATCH_TYPE = "Press 1 to start a Match , Press 2 to start a series";
    public static String MAIN_SWITCH_DEFAULT_MESSAGE = "Please enter either 1 or 2 !!";
    public static String NUMBER_OF_OVERS = "Enter Number of Overs in the Match";
    public static String DRAW = "DRAW";
    public static String TIE = "TIE";
    public static String NINE_STRING_INPUT = "%-20.20s  %-20.20s  %-20.20s %-20.20s  %-20.20s  %-20.20s %-20.20s  %-20.20s %-20.20s%n";
    public static String SIX_STRING_INPUT = "%-20.20s  %-20.20s  %-20.20s  %-20.20s %-20.20s  %-20.20s%n";
    public static String FOUR_STRING_INPUT = "%-20.20s  %-20.20s  %-20.20s  %-20.20s %n";
    public static String PLAYERNAME ="PlayerName";
    public static String BOWLERNAME = "BowlerName";
    public static String PLAYERSTATE = "State";
    public static String RUNSGIVEN = "RunsGiven";
    public static String OVERBOWLED="Overs";
    public static String WICKETSTAKEN="Wickets";
    public static String BOWLED_BY = "bowled by";
    public static String WICKET_TYPE = "Wicket type";
    public static String WICKET_HELPER = "Wicket Helper";
    public static String RUNSCORED="Runs";
    public static String FOUR_COUNT="4s";
    public static String SIX_COUNT="6s";
    public static String GOT_OUT_TO = "gotOutTo";
    public static String BALLS_PLAYED="Balls";
    public static String BATTING_STATS = "\n Batting Stats : ----\n";
    public static String BOWLING_STATS = "\n Bowling Stats : ----\n";


    /*
    BatsmanRunsRule
     */
    public static String BT0RULE = "0 run rule";
    public static String BT1RULE = "1 run rule";
    public static String BT2RULE = "2 run rule";
    public static String BT3RULE = "3 run rule";
    public static String BT4RULE = "4 run rule";
    public static String BT5RULE = "5 run rule";
    public static String BT6RULE = "6 run rule";
    public static String BTWRULE = "Wicket rule";
    public static String BT0DESC ="checks if random number is greater than equal to 1 and less than equal to 20";
    public static String BT1DESC = "checks if random number is greater than 20 and less than equal to 45";
    public static String BT2DESC = "checks if random number is greater than 45 and less than equal to 69";
    public static String BT3DESC = "checks if random number is greater than 69 and less than equal to 79";
    public static String BT4DESC="checks if random number is greater 79 and less than equal to 89";
    public static String BT5DESC="checks if random number is equal to 90";
    public static String BT6DESC =  "checks if random number is greater than 90 and less than equal to 95";
    public static String BTWDESC = "checks if random number is greater than 95";


    /*
    BowlerRunsRule
     */
    public static String BW0RULE = "0 run rule";
    public static String BW1RULE = "1 run rule";
    public static String BW2RULE = "2 run rule";
    public static String BW3RULE = "3 run rule";
    public static String BW4RULE = "4 run rule";
    public static String BW5RULE = "5 run rule";
    public static String BW6RULE = "6 run rule";
    public static String BWWRULE = "Wicket rule";
    public static String BW0DESC ="checks if random number is greater than equal to 1 and less than equal to 35";
    public static String BW1DESC = "checks if random number is greater than 35 and less than equal to 60";
    public static String BW2DESC = "checks if random number is greater than 60 and less than equal to 77";
    public static String BW3DESC = "checks if random number is greater than 77 and less than equal to 82";
    public static String BW4DESC= "checks if random number is greater 82 and less than equal to 87";
    public static String BW5DESC= "checks if random number is equal to 88";
    public static String BW6DESC =  "checks if random number is greater than 88 and less than equal to 90";
    public static String BWWDESC = "checks if random number is greater than 90";



    /*
    GameTypeRule
     */
    public static String SINGLERULE = "single match rule";
    public static String SINGLEDESC = "checks if matchType==single and then calls playSingleMatch function";
    public static String SERIESRULE = "series match rule";
    public static String SERIESDESC = "checks if matchType==series and then calls playSeries function";


    /*
    WicketTypeRule
     */
    public static String CATCHRULE ="catch out rule";
    public static String CATCHDESC = "checks if random number is greater than equal to 1 and less than 35";
    public static String BOLDRULE = "bold rule";
    public static String BOLDDESC = "checks if random number is greater than equal to 35 and less than 55";
    public static String LBWRULE = "lbw rule";
    public static String LBWDESC = "checks if random number is greater than equal to 55 and less than 85";
    public static String RUNOUTRULE ="run out rule";
    public static String RUNOUTDESC = "checks if random number is greater than equal to 85 and less than 93";
    public static String HITWICKETRULE = "hit wicket rule";
    public static String HITWICKETDESC = "checks if random number is greater than equal to 93 and less than 95";
    public static String STUMPINGRULE="stumping rule";
    public static String STUMPINGDESC="checks if random number is greater than equal to 95";



}
