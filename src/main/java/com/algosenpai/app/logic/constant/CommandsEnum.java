//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandsEnum {
    menu, //0
    lecture, //1
    quiz, //2
    arcade, //3
    chapters, //4
    next, //5
    back, //6
    end, //7
    review, //8
    reset, //9
    history, //10
    undo, //11
    clear, //12
    help, //13
    volume, //14
    print, //15
    archive, //16
    save, //17
    stats, //18
    start,//19
    result,//20
    exit; //21

    private static List<String> enumNames = Stream.of(CommandsEnum.values())
                                                  .map(CommandsEnum::name)
                                                  .collect(Collectors.toList());

    /**
     * Returns the valid commands as strings in a list.
     *
     * @return List of strings
     */
    public static List<String> getNames() {
        return enumNames;
    }

    /**
     * Returns the blocked commands during a quiz.
     * @return list of blocked commands during a quiz.
     */
    public static List<String> getQuizBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(21); //exit
        blockedNames.remove(14); //volume
        blockedNames.remove(2); //quiz
        return blockedNames;
    }

    /**
     * Returns the blocked commands during a lecture.
     * @return list of blocked commands during a lecture.
     */
    public static List<String> getLectureBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(21); //exit
        blockedNames.remove(14); //volume
        blockedNames.remove(1); //lecture
        return blockedNames;
    }

    /**
     * Returns the blocked command during an arcade.
     * @return list of blocked commands during an arcade.
     */
    public static List<String> getArcadeBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(21); //exit
        blockedNames.remove(14); //volume
        return blockedNames;
    }

}