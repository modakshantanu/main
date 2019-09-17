package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.services.ui.Terminal;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TaskList {

    /**
     * Container for tasks.
     */
    private ArrayList<Task> tasksList;
    private ArrayList<Task> overdueList;
    private ArrayList<Task> todayList;
    private ArrayList<Task> undefinedList;
    private ArrayList<Task> eventualList;

    /**
     * Create list of tasks.
     */
    public TaskList() {
        tasksList = new ArrayList<>();
        overdueList = new ArrayList<>();
        todayList = new ArrayList<>();
        undefinedList = new ArrayList<>();
        eventualList = new ArrayList<>();
    }

    /**
     * Get the list of tasks.
     * @return list of tasks.
     */
    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    /**
     * Add task.
     * @param task task.
     */
    public void addTask(Task task) {
        tasksList.add(task);
    }

    /**
     * Remove task.
     * @param taskIndex index of task.
     */
    public void removeTask(int taskIndex) {
        tasksList.get(taskIndex).removeTaskString(this.numberOfTasks());
        tasksList.remove(taskIndex);

    }

    /**
     * Set task as done.
     * @param taskIndex index of task.
     */
    public void markDone(int taskIndex) {
        tasksList.get(taskIndex).setDoneStatus(true);
    }

    /**
     * Get number of tasks.
     * @return number of tasks.
     */
    public int numberOfTasks() {
        return tasksList.size();
    }

    /**
     * Get task.
     * @param taskIndex task index.
     * @return task.
     */
    public Task getTask(int taskIndex) {
        return tasksList.get(taskIndex);
    }

    /**
     * Get tasks by keywords.
     * @param keyword keyword by input.
     */
    public void searchTask(String keyword) {
        Terminal.showSearchTask(this, keyword);
    }

    /**
     * Show all tasks.
     */
    public void showAll() {
        Terminal.showTasksList(this);
    }

    /**
     * Shows the reminders in sorted order.
     */
    public void showSortedReminders() {
        undefinedList.clear();
        todayList.clear();
        eventualList.clear();
        overdueList.clear();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date currDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        for (Task i : tasksList) {
            Date currTaskDate = i.getDate();
            if (currTaskDate == null) {
                undefinedList.add(i);
            } else {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                boolean sameDay = fmt.format(currDate).equals(fmt.format(currTaskDate));
                int val = currDate.compareTo(currTaskDate);
                if (sameDay) {
                    todayList.add(i);
                } else if (val == 1) {
                    overdueList.add(i);
                } else if (val == -1) {
                    eventualList.add(i);
                }
            }
        }
        Collections.sort(overdueList, new CustomSort());
        Collections.sort(todayList, new CustomSort());
        Collections.sort(eventualList, new CustomSort());
        Collections.sort(undefinedList, new CustomSort());
        Terminal.showSortedRemindersList(overdueList, todayList, eventualList, undefinedList);
    }

    /**
     * Custom Comparator to sort the Tasks according to date.
     */
    public class CustomSort implements Comparator<Task> {

        public int compare(Task t1, Task t2) {
            return t1.getDate().compareTo(t2.getDate());
        }

    }
}
