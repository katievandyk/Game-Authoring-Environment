package gameplayer.panel;

import frontend.PromptReader;
import gameplayer.screen.GameScreen;
import frontend.PropertiesReader;
import frontend.UIFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BuyPanel extends Panel {

    private final GameScreen GAME_SCREEN;
    private final UIFactory UI_FACTORY;
    private PropertiesReader PROP_READ;
    private PromptReader PROMPTS;

    public BuyPanel(GameScreen gameScreen, PromptReader promptReader) {
        GAME_SCREEN = gameScreen;
        PROMPTS = promptReader;
        UI_FACTORY = new UIFactory();
        PROP_READ = new PropertiesReader();
    }

    @Override
    public void makePanel() {

        Label TowerInfo = new Label("return value from getUpgradeInfoFromClick method");
        TowerInfo.setWrapText(true);
        Button buyUpgrade = UI_FACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("BuyTowerUpgrade"));
        //buyUpgrade.setOnMouseClicked((arg0) -> /**BACKENDUPGRADETOWERMETHOD**/);

        VBox panelRoot = new VBox(TowerInfo, buyUpgrade);
        VBox.setVgrow(TowerInfo, Priority.ALWAYS);
        panelRoot.setAlignment(Pos.CENTER);
        panelRoot.setId("buyUpgradePanel");
        PANEL = panelRoot;
    }

    public String getUpgradeInfoFromClick() {
        //TODO Once backend is linked, write method to take information from button click on upgradePanel
        //TODO and send it over to the buyPanel
        return null;
    }


}