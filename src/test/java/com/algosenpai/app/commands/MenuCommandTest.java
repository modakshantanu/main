
package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.components.DialogBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class MenuCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testMenuMousePress() {
        clickOn("#userInput").write("menu");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuKeyPress() {
        clickOn("#userInput").write("menu").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuWithSpace() {
        clickOn("#userInput").write(" menu ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" menu ", actualText);
    }

    @Test
    void testMenuOutput() {
        clickOn("#userInput").write("menu").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Senpai will teach you! Try these commands :\n"
                + "Critical : \n"
                + "*\tlecture\n"
                + "*\tquiz\n"
                + "*\tarcade\n"
                + "*\treset\n"
                + "*\texit\n"
                +  "\n"
                + "Utility : \n"
                + "*\tmenu\n"
                + "*\tstart\n"
                + "*\treview\n"
                + "*\thistory\n"
                + "*\tundo\n"
                + "*\thelp\n"
                + "*\tprint\n"
                + "*\tarchive\n"
                + "*\tsave\n"
                + "\n"
                + "Misc : \n"
                + "*\tchapters\n"
                + "*\tclear\n"
                + "*\tvolume\n"
                + "*\tresult\n"
                + "*\tstats\n"
                + "Type `menu <command> to see how to use certain commands.\n", actualText);
    }

    @Test
    void testMenuLectureCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu lecture");
        String actualText = command.execute();
        Assertions.assertEquals("`lecture` to pick a chapter. Type `start` to begin the lecture.", actualText);
    }

    @Test
    void testMenuChaptersCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu chapters");
        String actualText = command.execute();
        Assertions.assertEquals("`chapters` to view the list of chapters", actualText);
    }

    @Test
    void testMenuHelpCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu help");
        String actualText = command.execute();
        Assertions.assertEquals("`help <chapter>` to view the list of problems on kattis "
                                        + "that you may wish to solve for "
                                        + "the corresponding chapter", actualText);
    }

    @Test
    void testMenuQuizCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu quiz");
        String actualText = command.execute();
        Assertions.assertEquals("`quiz <chapter>` to select a chapter, enter `menu quizcommands` "
                + "to find out the commands available during a quiz", actualText);
    }

    @Test
    void testMenuQuizCommandsOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu quizcommands");
        String actualText = command.execute();
        Assertions.assertEquals("`next` to go to the next question || `back` to return to the previous question "
                + "|| `end` to end the quiz || <input your answer separated with commas> e.g. 30, 40, 50\n"
                + "you may enter `menu <quiz command>` as well", actualText);
    }

    @Test
    void testMenuStartCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu start");
        String actualText = command.execute();
        Assertions.assertEquals("`start` to start a quiz after selecting a chapter", actualText);
    }

    @Test
    void testMenuNextCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu next");
        String actualText = command.execute();
        Assertions.assertEquals("`next` to go to the next question during a quiz", actualText);
    }

    @Test
    void testMenuBackCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu back");
        String actualText = command.execute();
        Assertions.assertEquals("`back` to go to the previous question during a quiz", actualText);
    }

    @Test
    void testMenuEndCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu end");
        String actualText = command.execute();
        Assertions.assertEquals("`end` to be used if you want to end the quiz before completing it", actualText);
    }

    @Test
    void testMenuResultCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu result");
        String actualText = command.execute();
        Assertions.assertEquals("`result` to view your results of the "
                                          + "past quizzes you've attempted before", actualText);
    }

    @Test
    void testHistoryCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu history");
        String actualText = command.execute();
        Assertions.assertEquals("`history <number of commands you'd like to view>` "
                                          + "to view your past commands", actualText);
    }

    @Test
    void testUndoCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu undo");
        String actualText = command.execute();
        Assertions.assertEquals("`undo` to reverse a single action or undo <number of steps you'd like to undo> "
                + "to reverse multiple actions", actualText);
    }

    @Test
    void testClearCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu clear");
        String actualText = command.execute();
        Assertions.assertEquals("`clear` to clear all chats", actualText);
    }

    @Test
    void testSaveCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu save");
        String actualText = command.execute();
        Assertions.assertEquals("`save` to save all your current data", actualText);
    }

    @Test
    void testExitCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu exit");
        String actualText = command.execute();
        Assertions.assertEquals("`exit` to exit the programme", actualText);
    }

    @Test
    void testMenuPrintCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu print");
        String actualText = command.execute();
        Assertions.assertEquals("`print <archive | quiz | user> <filename>.pdf` "
                                          + "to print the selected pdf file", actualText);
    }

    @Test
    void testArchiveCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu archive");
        String actualText = command.execute();
        Assertions.assertEquals("`archive <question number>` to archive a question", actualText);
    }

    @Test
    void testReviewCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu review");
        String actualText = command.execute();
        Assertions.assertEquals("`review <question number>` used after a quiz to find out the "
                + "correct answer and method for the selected question in the quiz", actualText);
    }

    @Test
    void testVolumeCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu volume");
        String actualText = command.execute();
        Assertions.assertEquals("`volume <sound level>` to adjust the volume level", actualText);
    }

    @Test
    void testInvalidCommandMenuOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu select");
        String actualText = command.execute();
        Assertions.assertEquals("Error there is no such command, enter `menu` "
                                          + "to get the list of available commands.", actualText);
    }

    @Test
    void testInvalidMenuCommandOutput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("menu quiz commands");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. Please enter the command which you'd "
                                          + "like to view in the following format: menu print\n"
                                          + "Otherwise, enter `menu` to view "
                                          + "the list of commands that we have", actualText);
    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}