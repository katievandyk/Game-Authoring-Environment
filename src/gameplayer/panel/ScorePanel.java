package gameplayer.panel;

import java.io.IOException;


import gameplayer.screen.GameScreen;
import java.util.Map;

import file.DataPointWriter;
import com.sun.javafx.tools.packager.Log;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends ListenerPanel {

    public static final String DEFAULT_SCORE_PATH = "Score/"; 
    public static final String DEFAULT_HEALTH_PATH = "Health/"; 
    public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";

    private final GameScreen GAME_SCREEN;
    private Map<String,String> GAMEPLAYER_PROPERTIES;

    private Label ScoreText;
    private Label LevelText;
    private Label HealthText;
    private Integer initialScore;
    private Integer initialHealth;

    private DataPointWriter myScoreWriter; 
    private DataPointWriter myHealthWriter;

    public ScorePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();

	setupWriters(); 
    }

    private void setupWriters() {
	initialScore = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("defaultScore"));
	initialHealth = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("defaultHealth"));

	try {
	    myScoreWriter = new DataPointWriter(GAME_SCREEN.getGameName(), DEFAULT_SCORE_PATH); 
	} catch (IOException e) {
	    //			Log.error(e);
	    GAME_SCREEN.loadErrorScreen("NoFile");
	}

	try {
	    myHealthWriter = new DataPointWriter(GAME_SCREEN.getGameName(), DEFAULT_HEALTH_PATH); 
	} catch (IOException e) {
	    Log.debug(e);
	    GAME_SCREEN.loadErrorScreen("NoFile");
	}

    }

    @Override
    public void makePanel() {
	//TODO Read words SCORE, LEVEL, and + from properties file
	ScoreText = new Label(GAMEPLAYER_PROPERTIES.get("scoreText") + initialScore);
	LevelText = new Label();
	HealthText = new Label(GAMEPLAYER_PROPERTIES.get("healthText") + initialHealth);

	ScoreText.setMaxWidth(Double.MAX_VALUE);

	ScoreText.setMaxWidth(Double.MAX_VALUE);
	LevelText.setMaxWidth(Double.MAX_VALUE);
	HealthText.setMaxWidth(Double.MAX_VALUE);

	HBox panelRoot = new HBox();

	HBox.setHgrow(ScoreText, Priority.ALWAYS);
	HBox.setHgrow(LevelText, Priority.ALWAYS);
	HBox.setHgrow(HealthText, Priority.ALWAYS);
	panelRoot.getChildren().addAll(ScoreText, LevelText, HealthText);

	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);
	PANEL = panelRoot;
    }


    private void updateScore(Integer newScore) {		
	myScoreWriter.recordDataPoint(newScore);
	ScoreText.setText(GAMEPLAYER_PROPERTIES.get("scoreText") + newScore);
    }

    private void updateHealth(Integer newHealth) {
	myHealthWriter.recordDataPoint(newHealth);
	HealthText.setText(GAMEPLAYER_PROPERTIES.get("healthText")+ newHealth);
    }

    public void updateLevel(Integer newLevel) {
	LevelText.setText(GAMEPLAYER_PROPERTIES.get("levelText")+ newLevel);
    }



    /**
     * Wrapper method on score to reduce order of call dependencies
     * @param score	initial score of the level
     */
    private void setInitalScore(int score) {
	initialScore = setInitalProperty(ScoreText, score);
	if(initialScore == -1) {
	    updateScore(score);
	}
    }

    /**
     * Wrapper method on score to reduce order of call dependencies
     * @param score	initial health of the level
     */
    private void setInitialHealth(int health) {
	initialHealth= setInitalProperty(HealthText, health);
	if(initialHealth == -1) {
	    updateHealth(health);

	}
    }



    public ChangeListener<Number> createScoreListener(int startScore) {
	setInitalScore(startScore);
	return new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		updateScore((Integer)arg0.getValue());
	    }
	};
    }

    public ChangeListener<Number> createHealthListener(int startHealth) {
	setInitialHealth(startHealth);
	return new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		updateHealth((Integer)arg0.getValue());
	    }
	};
    }
}
