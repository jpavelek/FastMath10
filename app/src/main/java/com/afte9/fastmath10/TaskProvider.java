package com.afte9.fastmath10;



import java.util.Random;

public final class TaskProvider {
    private static final TaskProvider ourInstance = new TaskProvider();
    private static final int TARGET = 7; //Six out of 10 is target
    public static final int ROUNDS = 10; //Ten tasks per round/level

    private TaskLevels task_level;
    private SpeedLevels speed_level;
    private int task_result;
    private int[] task_choices;
    private String task_visual;
    private int level_target;
    private int level_increment;
    private int task_level_ordinal;
    private Random r;

    public enum TaskLevels { ONE(100), TWO(200), THREE(300), FOUR(400), FIVE(500), SIX(1000), SEVEN(1500);
        private int retval;
        TaskLevels (int newVal) { this.retval = newVal; }
        public TaskLevels getNext() {
            if (this == SEVEN) return SEVEN;
            else return TaskLevels.values()[this.ordinal() +1];
        }
        public int getNumVal()  {
            return retval;
        }
    }
    public enum SpeedLevels { SLOW(20000), NORMAL(10000), FAST(5000);
        private int retval;
        SpeedLevels(int newVal) {
            this.retval = newVal;
        }
        public int getNumVal() {
            return retval;
        }
        public SpeedLevels getNext() {
            if (this == FAST) return FAST;
            else return SpeedLevels.values()[this.ordinal() + 1];
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
        speed_level = SpeedLevels.SLOW;
        level_increment = TaskLevels.ONE.getNumVal();
        level_target = TARGET*level_increment;
        getNextTask(); // to make sure this is not empty
    }

    public int increaseLevel() {
        // Returns new timeout in milliseconds
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
                //Increase speed and go back to ONE
                task_level = TaskLevels.ONE;
                speed_level = speed_level.getNext();
                break;
            default:
                System.out.println("WARN: Unrecognized task level");
                break;
        }
        return speed_level.getNumVal();
    }

    private int randS() {
        return r.nextInt(10);
    }

    public void getNextTask() {
        int a,b,c;
        switch (task_level) {
            case ONE:
                a = randS()+1;
                while (a>9) a = randS()+1;
                b = randS()+1;
                while (a+b>10) b = randS()+1;
                task_result = a + b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d =", a, b);
                break;
            case TWO:
                a = randS()+3;
                while (a>9) a = randS()+3;
                b = randS();
                while ((b == 0) || (b>a)) b = randS();
                task_result = a - b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case THREE:
                a = randS()+1;
                while (a>9) a = randS()+1;
                b = randS()+2;
                while (a + b < 11) b = randS()+2;
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
                while (b == 0) b = randS();
                task_result = a - b;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d - %d =", a, b);
                break;
            case FIVE:
                a = randS()+1;
                while (a>9) a = randS()+1;
                b = randS()+11;
                while (a+b>19) b = randS()+11;
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
                while (a+b+c > 19) c = randS()+1;
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
                while (a+b-c < 0) c = randS()+1;
                task_result = a + b - c;
                task_choices[0] = task_result - (r.nextInt(3)+1);
                task_choices[1] = task_result + (r.nextInt(3)+1);
                task_choices[2] = task_result;
                task_choices[3] = 3;
                task_visual = String.format("%d + %d - %d =", a, b, c);
                break;
            default:
                break;
        }
        int seed = r.nextInt(3);
        a = task_choices[seed];
        task_choices[seed] = task_choices[2]; //the current correct answer swapped to new random position
        task_choices[2] = a;
        task_choices[3] = seed+1; //save the new correct position
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
        return speed_level.getNumVal();
    }

    public int getTaskLevel() { return task_level_ordinal; }
}
