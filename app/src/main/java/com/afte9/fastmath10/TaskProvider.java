package com.afte9.fastmath10;



import android.util.Log;

import java.util.Random;

public final class TaskProvider {
    private static final TaskProvider ourInstance = new TaskProvider();
    private static final int TARGET = 7; //Six out of 10 is target
    public static final int ROUNDS = 10; //Ten tasks per round/level
    public static final int TIMEOUT = 20000; //milliseconds

    private TaskLevels task_level;
    private int task_result;
    private int previous_task_result;
    private int[] task_choices;
    private String task_visual;
    private int level_target;
    private int level_increment;
    private int task_level_ordinal;
    private Random r;

    public enum TaskLevels { ONE(100), TWO(200), THREE(300), FOUR(400), FIVE(500), SIX(1000), SEVEN(1500), EIGHT(1500), NINE(1500);
        private int retval;
        TaskLevels (int newVal) { this.retval = newVal; }
        public TaskLevels getNext() {
            if (this == NINE) return NINE;
            else return TaskLevels.values()[this.ordinal() +1];
        }
        public int getNumVal()  {
            return retval;
        }
    }

    public static TaskProvider getInstance() {
        return ourInstance;
    }

    private TaskProvider() {
        r = new Random();
        task_choices = new int[4];
        reset();
    }

    public void reset() {
        //Init to initial speed and task levels
        task_level_ordinal = 1;
        task_level = TaskLevels.ONE;
        level_increment = TaskLevels.ONE.getNumVal();
        level_target = TARGET*level_increment;
        getNextTask(); // to make sure this is not empty
    }

    public void increaseLevel() {
        // Increases level targets and rewards.

        task_level_ordinal++;
        switch (task_level) {
            case ONE:
                level_increment = level_increment + TaskLevels.ONE.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case TWO:
                level_increment = level_increment + TaskLevels.TWO.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case THREE:
                level_increment = level_increment + TaskLevels.THREE.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case FOUR:
                level_increment = level_increment + TaskLevels.FOUR.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case FIVE:
                level_increment = level_increment + TaskLevels.FIVE.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case SIX:
                level_increment = level_increment + TaskLevels.SIX.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case SEVEN:
                level_increment = level_increment + TaskLevels.SEVEN.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case EIGHT:
                level_increment = level_increment + TaskLevels.EIGHT.getNumVal();
                level_target = TARGET*level_increment;
                task_level = task_level.getNext();
                break;
            case NINE:
                System.out.println("ERR: We should not be here going over NINE");
                break;
            default:
                System.out.println("WARN: Unrecognized task level");
                break;
        }
    }

    private int rand10() {
        return r.nextInt(10)+1;
    }

    public void getNextTask() {
        int a,b,c;
        switch (task_level) {
            case ONE:
                a = rand10();
                while (a>7) a = rand10();
                b = rand10()+1;
                while ((a+b>10) || (a+b == previous_task_result)) b = rand10();
                task_result = a + b;

                task_choices[0] = -1;
                if (((task_result<5) && (r.nextBoolean())) || (task_result >=9)) { // If result is small, then randomly do smaller option
                    while ((task_choices[0] <= 0) || (task_choices[0] > 10)) task_choices[0] = task_result - (r.nextInt(1)+1);
                } else { // but usually do the bigger option
                    while ((task_choices[0] <= 0) || (task_choices[0] > 10)) task_choices[0] = task_result + (r.nextInt(1)+1);
                }

                task_choices[1] = -1;
                if (task_result >= 9) {
                    while ((task_choices[1] == task_choices[0]) || (task_choices[1] <= 0) || (task_choices[1] > 10)) {
                        task_choices[1] = task_result - (r.nextInt(2) + 1);
                    }
                } else {
                    while ((task_choices[1] == task_choices[0]) || (task_choices[1] <= 0) || (task_choices[1] > 10)) {
                        task_choices[1] = task_result + (r.nextInt(2) + 1);
                    }
                }

                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case TWO:
                a = 10;
                while (a>9) a = rand10()+2;
                b = rand10();
                while ((b >= a) || (a-b == previous_task_result)) b = rand10();
                task_result = a - b;
                if (task_result == 1) {
                    task_choices[0] = task_result + (r.nextInt(1) + 1);
                } else {
                    task_choices[0] = -1;
                    while (task_choices[0] <= 0) task_choices[0] = task_result - (r.nextInt(1) + 1);
                }
                task_choices[1] = task_choices[0];
                if (task_result == 1) {
                    while (task_choices[1] == task_choices[0])
                        task_choices[1] = task_result + (r.nextInt(2) + 1);
                } else {
                    while (task_choices[1] == task_choices[0])
                        task_choices[1] = task_result + (r.nextInt(1) + 1);
                }
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case THREE:
                a = rand10()+2;
                while (a>9) a = rand10()+2;
                b = rand10()+1;
                while ((a + b < 11) || (a+b == previous_task_result) || (b>9)) b = rand10();
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case FOUR:
                a = rand10()+10;
                while (a>19) a = rand10()+10;
                b = rand10();
                while ((a == b) || (a-b == previous_task_result)) b = rand10();
                task_result = a - b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case FIVE:
                a = rand10();
                while (a>7) a = rand10();
                b = rand10()+10;
                while ((a+b>19) || (a+b == previous_task_result)) b = rand10()+10;
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case SIX:
                a = rand10();
                while (a>8) a = rand10();
                b = rand10();
                while (b>8) b = rand10();
                c = rand10();
                while ((a+b+c > 19) || (a+b+c == previous_task_result)) c = rand10();
                task_result = a + b + c;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d + %d =", a, b, c);
                break;
            case SEVEN:
                a = rand10();
                while (a>9) a = rand10();
                b = rand10();
                while (b>9) b = rand10();
                c = rand10();
                while ((a+b-c < 0) || (a+b-c == previous_task_result)) c = rand10();
                task_result = a + b - c;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d - %d =", a, b, c);
                break;
            case EIGHT:
                a = rand10()+1;
                while (a>4) a = rand10()+1;
                b = rand10()+1;
                while ((a*b>18) || (a*b == previous_task_result)) b = rand10()+1;
                task_result = a * b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d * %d =", a, b);
                break;
            case NINE:
                a = r.nextInt(15)+4;
                while ((a==3) || (a == 5) || (a == 7) || (a == 11) || (a == 13) || (a == 17)) a = r.nextInt(15)+4;
                b = rand10();
                while (((a % b != 0)) || (a == b) || (b == 1)) b = rand10();
                task_result = a / b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                while (task_choices[1] ==0) task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3; // position of the correct result
                task_visual = String.format("%d / %d =", a, b);
                break;
            default:
                break;
        }
        previous_task_result = task_result; //Save for the next round


        // Sort choices, this also randomizes correct result position
        if ((task_choices[0] < task_choices[1]) &&  (task_choices[1] < task_choices[2])) {
            // We are good, all sorted, no point randomizing.
        } else {
            for (int i = 0; i < 2; i++) {
                if (task_choices[i] > task_choices[i+1]) {
                    a = task_choices[i];
                    if (task_choices[3] == i) task_choices[3] = i+1;
                    else if (task_choices[3] == i+1) task_choices[3] = i;
                    task_choices[i] = task_choices[i+1];
                    task_choices[i+1] = a;
                    i = -1; //restart the loop
                }
            }
        }
    }

    public String getTaskVisual() {
        return task_visual;
    }

    public int getTaskResult() {
        return task_result;
    }

    public int[] getResultChoices() {
        return task_choices;
    }

    public int getLevelScoreIncrement() {
        return level_increment;
    }

    public int getLevelScoreTarget() {
        return level_target;
    }

    public int getTimeout() {
        return TIMEOUT;
    } //Ugly

    public int getTaskLevel() { return task_level_ordinal; }
}
