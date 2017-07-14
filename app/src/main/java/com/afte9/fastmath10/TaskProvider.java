package com.afte9.fastmath10;



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

    private int randS() {
        return r.nextInt(10);
    }

    public void getNextTask() {
        int a,b,c;
        switch (task_level) {
            case ONE:
                a = randS()+1;
                while (a>7) a = randS()+1;
                b = randS()+1;
                while ((a+b>10) || (a+b == previous_task_result)) b = randS()+1;
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <= 0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case TWO:
                a = randS()+3;
                while (a>9) a = randS()+3;
                b = randS();
                while ((b == 0) || (b == a) || (b>a) || (a-b == previous_task_result)) b = randS();
                task_result = a - b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case THREE:
                a = randS()+3;
                while (a>9) a = randS()+3;
                b = randS()+1;
                while ((a + b < 11) || (a+b == previous_task_result) || (b>9)) b = randS()+1;
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case FOUR:
                a = randS()+11;
                while (a>19) a = randS()+11;
                b = randS();
                while ((b == 0) || (a == b) || (a-b == previous_task_result)) b = randS();
                task_result = a - b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case FIVE:
                a = randS()+1;
                while (a>7) a = randS()+1;
                b = randS()+11;
                while ((a+b>19) || (a+b == previous_task_result)) b = randS()+11;
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case SIX:
                a = randS()+1;
                while (a>8) a = randS()+1;
                b = randS()+1;
                while (b>8) b = randS()+1;
                c = randS()+1;
                while ((a+b+c > 19) || (a+b+c == previous_task_result)) c = randS()+1;
                task_result = a + b + c;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d + %d =", a, b, c);
                break;
            case SEVEN:
                a = randS()+1;
                while (a>9) a = randS()+1;
                b = randS()+1;
                while (b>9) b = randS()+1;
                c = randS()+1;
                while ((a+b-c < 0) || (a+b-c == previous_task_result)) c = randS()+1;
                task_result = a + b - c;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d - %d =", a, b, c);
                break;
            case EIGHT:
                a = randS()+2;
                while (a>4) a = randS()+2;
                b = randS()+2;
                while ((a*b>18) || (a*b == previous_task_result)) b = randS()+2;
                task_result = a * b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d * %d =", a, b);
                break;
            case NINE:
                a = randS()+2;
                while ((a==3) || (a == 5) || (a == 7) || (a == 11)) a = randS()+2;
                b = randS()+1;
                while (((a % b != 0)) || (a == b) || (a/b == previous_task_result) || (b == 1)) b = randS()+1;
                task_result = a / b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                while (task_choices[0] <=0) task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                while (task_choices[1] ==0) task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d / %d =", a, b);
                break;
            default:
                break;
        }
        previous_task_result = task_result; //Save for the next round
        //Shuffle the position of correct result in between the buttons
        /*
        int seed = r.nextInt(3);
        a = task_choices[seed];
        task_choices[seed] = task_choices[2]; //the current correct answer swapped to new random position
        task_choices[2] = a;
        task_choices[3] = seed+1; //save the new correct position
*/
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
