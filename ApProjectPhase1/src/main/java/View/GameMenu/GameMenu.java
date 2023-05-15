package View.GameMenu;
import Controller.GameMenuController;
import Model.Buildings.Building;
import Model.Buildings.BuildingHP;
import Model.Game;
import Model.Government;
import View.BuildlingMenu.BuildingMenu;
import View.MainMenu.MainMenuCommands;
import View.ShopMenu.ShopMenu;
import View.TradeMenu.PreTradeMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    private static Game currentGame;

    public static void run(Scanner scanner) {
        GameMenu.setCurrentGame(Game.getCurrentGame());
        GameMenuController.setCurrentGame(Game.getCurrentGame());

        String command;
        Matcher matcher;

        while (true) {
            currentGame.setRounds(currentGame.getRounds() + 1);
            for (Government government : currentGame.getGovernments()) {
                if(government.getLord().getHitPoint() == 0)
                    continue;
                GameMenuController.setCurrentGovernment(government);
                System.out.println(government.getUser().getNickname() + " is playing now");

                while (true) {
                    command = scanner.nextLine();

                    if (GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_SHOP_MENU).matches()) {
                        System.out.println("you entered shop menu");
                        ShopMenu.run(scanner);
                    } else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY_FACTORS).matches())
                        GameMenuController.showPopularityFactors();

                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY).matches())
                        System.out.println(GameMenuController.showPopularity());

                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FOOD_LIST).matches())
                        GameMenuController.showFoodList();

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.FOOD_RATE)).matches())
                        System.out.println(GameMenuController.setFoodRate(matcher));

                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.FOOD_RATE_SHOW).matches())
                        System.out.println(GameMenuController.showFoodRate());

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.TAX_RATE)).matches())
                        System.out.println(GameMenuController.setTaxRate(matcher));

                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.TAX_RATE_SHOW).matches())
                        System.out.println(GameMenuController.showTaxRate());

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.FEAR_RATE)).matches())
                        System.out.println(GameMenuController.setFearRate(matcher));

                    else if(GameMenuCommands.getMatcher(command, GameMenuCommands.END_GAME).matches()) {
                        GameMenuController.endGame();
                        return;
                    }

                    else if(GameMenuCommands.getMatcher(command, GameMenuCommands.DONE).matches())
                        break;

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)).matches())
                        System.out.println(GameMenuController.dropBuilding(matcher));

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)).matches()) {
                        Building building = GameMenuController.selectBuilding(matcher);
                        if(building != null)
                            BuildingMenu.run(scanner, building);
                    }

                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)).matches())
                        GameMenuController.selectUnit(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.MOVE_UNIT)).matches())
                        GameMenuController.moveUnit(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.PATROL_UNIT)).matches())
                        GameMenuController.patrolUnit(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SET)).matches())
                        GameMenuController.setState(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.ATTACK_ENEMY)).matches())
                        GameMenuController.attack(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.AIR_ATTACK)).matches())
                        GameMenuController.airAttack(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.POUR_OIL)).matches())
                        GameMenuController.pourOil(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DIG_TUNNEL)).matches())
                        GameMenuController.digTunnel(matcher);
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.BUILD_EQUIPMENT)).matches())
                        GameMenuController.buildSurroundEquipment(matcher);
                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.DISBAND_UNIT).matches())
                        GameMenuController.disbandUnit();
                    else if (GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_TRADE_MENU).matches()) {
                        System.out.println("you entered trade menu");
                        PreTradeMenu.run(scanner);
                    } else System.out.println("invalid command");
                }
            }
            GameMenuController.nextTurn();
            if(GameMenuController.isGameOver()) {
                GameMenuController.endGame();
                return;
            }
        }
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenu.currentGame = currentGame;
    }
}
