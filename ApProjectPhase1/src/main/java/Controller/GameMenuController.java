package Controller;

import Model.Game;
import Model.Government;

import java.util.regex.Matcher;

public class GameMenuController {
    private static Government currentGovernment;
    private Game game;

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GameMenuController.currentGovernment = currentGovernment;
    }

    public static String showPopularityFactors() {
        String factors = "food\ntax\nreligion\nfear";
        return factors;
    }

    public static String showPopularity() {
        String popularityList = "";
        //TODO : add popularity factors and their amount
        return popularityList;
    }

    public static String showFoodList() {
        String list = "";
        //TODO : add foods and their amounts
        return list;
    }

    public static String setFoodRate(Matcher matcher) {
        return "Done!";
    }

    public static Integer showFoodRate() {
         return currentGovernment.getFoodRate();
    }

    public static String setTaxRate(Matcher matcher) {
        return "Done!";
    }

    public static Integer showTaxRate() {
        return currentGovernment.getTaxRate();
    }

    public static String setFearRate(Matcher matcher) {
        return "Done!";
    }

    public static void dropBuilding(Matcher matcher) {
    }

    public static void selectBuilding(Matcher matcher) {
    }

    public static void createUnit(Matcher matcher) {
    }

    public static void repair() {
    }

    public static void selectUnit(Matcher matcher) {
    }

    public static void moveUnit(Matcher matcher) {
    }

    public static void patrolUnit(Matcher matcher) {
    }

    public static void setState(Matcher matcher) {
    }

    public static void attack(Matcher matcher) {
    }

    public static void airAttack(Matcher matcher) {
    }

    public static void pourOil(Matcher matcher) {
    }

    public static void digTunnel(Matcher matcher) {
    }

    public static void buildSurroundEquipment(Matcher matcher) {
    }

    public static void disbandUnit() {
    }
}
