package controller;

import java.util.List;

import authoring.AuthoringModel;
import engine.GameEngine;
import engine.Mediator;
import engine.PlayState;
import engine.level.Level;
import frontend.StageManager;
import gameplayer.ScreenManager;
import xml.AuthoringModelReader;


/**
 * 
 * @author Ben Hodgson 4/5/18
 * @author Katherine Van Dyk 4/6/18
 * 
 * Created by the ChiefController and makes everything necessary to play a game. 
 */
public class PlayController {
    
    private Mediator myMediator;
    private AuthoringModelReader myReader;
    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    

    /**
     * Constructs main parts of play: Engine for backend controls, ScreenManager (top
     * level of game player) and Mediator, which connects the two
     * 
     * @param stage: Stage to mount Game Player on 
     */
    AuthoringModel test;
    public PlayController(StageManager stageManager, String language, AuthoringModel model) {
	myMediator = new Mediator(this);
	myGameEngine = new GameEngine(myMediator);
	myScreenManager = new ScreenManager(stageManager, language, myMediator);
	myReader = new AuthoringModelReader();
	myMediator.setGameEngine(myGameEngine);
	myMediator.setScreenManager(myScreenManager);
	myScreenManager.loadInstructionScreen();
	test = model;
    }
    
    /**
     * Creates a new play based on an XML file and passes authored
     * parameters to Engine
     * 
     * @param pathToXML: Path to game XML file
     */
    public void newPlay(String pathToXML) {
	myReader = new AuthoringModelReader();
	AuthoringModel playModel = myReader.createModel(pathToXML);
	List<Level> levels = playModel.allLevels();
	PlayState play = new PlayState(myMediator, levels, 0, 0, 0);
	play.setInitialObjects();
	myGameEngine.setPlayState(play);
    }
    public void setAuthoring() {
	StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	StackTraceElement element = stackTrace[2];
	System.out.println("Set authoring was called by a method named:" + element.getMethodName());
	System.out.println("That method is in class:" + element.getClassName());
	PlayState tester = new PlayState(myMediator, test.allLevels(), 0, 0, 0);
	myGameEngine.setPlayState(tester);
    }
}