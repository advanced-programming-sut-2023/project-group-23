package View.PreGameMenu;

import Controller.PreGameController;
import Model.Buildings.BuildingType;
import Model.Directions;
import Model.Government;
import Model.GroundType;
import Model.People.TroopType;
import Model.Tree;
import View.GameJFX.GameJFX;
import View.MainMenu.MainMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Initialize extends Application {
    private ArrayList<Node> fields;
    private PreGameMenu preGameMenu;
    private Government currentGovernment;

    public Initialize(Government currentGovernment, PreGameMenu preGameMenu) {
        this.currentGovernment = currentGovernment;
        this.preGameMenu = preGameMenu;
         fields = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setTitle("initialize " + currentGovernment.getUser().getNickname() + " map");
        stage.setResizable(false);

        Button game = new Button("Go to game");
        game.setLayoutX(10);
        game.setLayoutY(560);

        game.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        Button continueInitialize = new Button("Continue");
        continueInitialize.setLayoutX(500);
        continueInitialize.setLayoutY(550);

        MenuButton initializer = new MenuButton("Options");
        initializer.setLayoutX(10);
        initializer.setLayoutY(40);

        MenuItem setTextureSingle = new MenuItem("set single texture");
        setTextureSingle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSetTextureSingle(anchorPane, initializer, setTextureSingle, continueInitialize, game, stage);
            }
        });
        MenuItem setTextureZone = new MenuItem("set multi texture");
        setTextureZone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSetMultiTexture(anchorPane, initializer, setTextureZone, continueInitialize, game, stage);
            }
        });
        MenuItem dropRock = new MenuItem("drop rock");
        dropRock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDropRock(anchorPane, initializer, dropRock, continueInitialize, game, stage);
            }
        });
        MenuItem dropTree = new MenuItem("drop tree");
        dropTree.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDropTree(anchorPane, initializer, dropTree, continueInitialize, game, stage);
            }
        });
        MenuItem dropBuilding = new MenuItem("drop building");
        dropBuilding.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDropBuilding(anchorPane, initializer, dropBuilding, continueInitialize, game, stage);
            }
        });
        MenuItem dropUnit = new MenuItem("drop unit");
        dropUnit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDropUnit(anchorPane, initializer, dropUnit, continueInitialize, game, stage);
            }
        });
        MenuItem clear = new MenuItem("clear");
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleClear(anchorPane, initializer, clear, continueInitialize, game, stage);
            }
        });
        initializer.getItems().addAll(setTextureSingle, setTextureZone, dropBuilding, dropRock, dropUnit, dropTree, clear);

        anchorPane.getChildren().addAll(initializer, game, continueInitialize);
        stage.show();
    }

    private void continueInitialize(Stage stage) {
        stage.close();
        Stage newStage = new Stage();
        newStage.setTitle(stage.getTitle());
        try {
            new Initialize(preGameMenu.getCurrentGovernment(), preGameMenu).start(newStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void goToGame(Stage stage) {
        stage.close();
        if (MainMenu.okPlayers == PreGameMenu.stages.size() - 1) {
            try {
                new GameJFX().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            MainMenu.okPlayers++;
        }
    }

    private void handleSetTextureSingle(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        MenuButton groundType = new MenuButton("Ground Type");
        groundType.setLayoutX(200);
        groundType.setLayoutY(90);
        fields.add(groundType);
        for (GroundType value : GroundType.values()) {
            MenuItem type = new MenuItem(value.getName());
            groundType.getItems().add(type);
            type.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    groundType.setText(type.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.setTextureSingle(xCoordinate.getText()
                        , yCoordinate.getText(), groundType.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.setTextureSingle(xCoordinate.getText()
                        , yCoordinate.getText(), groundType.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });

        anchorPane.getChildren().addAll(fields);
    }

    private void handleSetMultiTexture(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField x1Coordinate = new TextField();
        x1Coordinate.setPromptText("X1 Coordinate");
        x1Coordinate.setLayoutX(200);
        x1Coordinate.setLayoutY(30);
        fields.add(x1Coordinate);

        TextField y1Coordinate = new TextField();
        y1Coordinate.setPromptText("Y1 Coordinate");
        y1Coordinate.setLayoutX(200);
        y1Coordinate.setLayoutY(60);
        fields.add(y1Coordinate);

        TextField x2Coordinate = new TextField();
        x2Coordinate.setPromptText("X2 Coordinate");
        x2Coordinate.setLayoutX(200);
        x2Coordinate.setLayoutY(90);
        fields.add(x2Coordinate);

        TextField y2Coordinate = new TextField();
        y2Coordinate.setPromptText("Y2 Coordinate");
        y2Coordinate.setLayoutX(200);
        y2Coordinate.setLayoutY(120);
        fields.add(y2Coordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(95);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        MenuButton groundType = new MenuButton("Ground Type");
        groundType.setLayoutX(200);
        groundType.setLayoutY(150);
        fields.add(groundType);
        for (GroundType value : GroundType.values()) {
            MenuItem type = new MenuItem(value.getName());
            groundType.getItems().add(type);
            type.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    groundType.setText(type.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!x1Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!y1Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!y2Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!x2Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.setTextureZone(x1Coordinate.getText()
                        , y1Coordinate.getText(), x2Coordinate.getText(), y2Coordinate.getText(), groundType.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!x1Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!y1Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!x2Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!y2Coordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.setTextureZone(x1Coordinate.getText()
                        , y1Coordinate.getText(), x2Coordinate.getText(), y2Coordinate.getText(), groundType.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });

        anchorPane.getChildren().addAll(fields);
    }

    private void handleDropRock(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        MenuButton direction = new MenuButton("Direction");
        direction.setLayoutX(200);
        direction.setLayoutY(90);
        fields.add(direction);
        for (Directions value : Directions.values()) {
            MenuItem dir = new MenuItem(value.getName());
            direction.getItems().add(dir);
            dir.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    direction.setText(dir.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.droprock(xCoordinate.getText()
                        , yCoordinate.getText(), direction.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.droprock(xCoordinate.getText()
                        , yCoordinate.getText(), direction.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });


        anchorPane.getChildren().addAll(fields);
    }

    private void handleDropTree(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        MenuButton type = new MenuButton("Tree Type");
        type.setLayoutX(200);
        type.setLayoutY(90);
        fields.add(type);
        for (Tree value : Tree.values()) {
            MenuItem tree = new MenuItem(value.getName());
            type.getItems().add(tree);
            tree.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    type.setText(tree.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.droptree(xCoordinate.getText()
                        , yCoordinate.getText(), type.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.droptree(xCoordinate.getText()
                        , yCoordinate.getText(), type.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });


        anchorPane.getChildren().addAll(fields);
    }

    private void handleDropBuilding(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        MenuButton buildings = new MenuButton("Building Type");
        buildings.setLayoutX(200);
        buildings.setLayoutY(90);
        fields.add(buildings);
        for (BuildingType value : BuildingType.values()) {
            MenuItem building = new MenuItem(value.getName());
            buildings.getItems().add(building);
            building.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    buildings.setText(building.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.dropbuilding(xCoordinate.getText()
                        , yCoordinate.getText(), buildings.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.dropbuilding(xCoordinate.getText()
                        , yCoordinate.getText(), buildings.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });


        anchorPane.getChildren().addAll(fields);
    }

    private void handleDropUnit(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        TextField count = new TextField();
        count.setPromptText("Units Number");
        count.setLayoutX(200);
        count.setLayoutY(90);
        fields.add(count);

        MenuButton units = new MenuButton("Unit Type");
        units.setLayoutX(200);
        units.setLayoutY(120);
        fields.add(units);
        for (TroopType value : TroopType.values()) {
            if (value.getName().equals("lord")) continue;
            MenuItem unit = new MenuItem(value.getName());
            units.getItems().add(unit);
            unit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    units.setText(unit.getText());
                }
            });
        }

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!count.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.dropunit(xCoordinate.getText()
                        , yCoordinate.getText(), units.getText(), count.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!count.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.dropunit(xCoordinate.getText()
                        , yCoordinate.getText(), units.getText(), count.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });


        anchorPane.getChildren().addAll(fields);
    }

    private void handleClear(AnchorPane anchorPane, MenuButton menuButton, MenuItem menuItem, Button con, Button go, Stage stage) {
        anchorPane.getChildren().removeAll(fields);
        fields.clear();
        menuButton.setText(menuItem.getText());

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        fields.add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        fields.add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        fields.add(wrongCoordinate);

        con.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.clear(xCoordinate.getText()
                        , yCoordinate.getText()));
                if (wrongCoordinate.getText().equals("")) continueInitialize(stage);
            }
        });

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                }
                wrongCoordinate.setText(PreGameController.clear(xCoordinate.getText()
                        , yCoordinate.getText()));
                if (wrongCoordinate.getText().equals("")) goToGame(stage);
            }
        });


        anchorPane.getChildren().addAll(fields);
    }

}
