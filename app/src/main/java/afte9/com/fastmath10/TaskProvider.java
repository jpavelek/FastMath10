package afte9.com.fastmath10;


public final class TaskProvider {
    private static final TaskProvider ourInstance = new TaskProvider();
    private static final int TARGET = 6; //Six out of 10 is target
    public static final int ROUNDS = 10; //Ten tasks per round/level

    public enum TaskLevels { ONE(100), TWO(200), THREE(300), FOUR(400), FIVE(500), SIX(1000), SEVEN(1500);
        private int retval;
        TaskLevels (int newVal) {this.retval = newVal; }
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

    private TaskLevels task_level;
    private SpeedLevels speed_level;
    private int task_result;
    private int[] task_choices;
    private String task_visual;
    private int level_target;
    private int level_increment;
    private int task_level_ordinal;

    public static TaskProvider getInstance() {
        return ourInstance;
    }

    private TaskProvider() {
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
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
                //Just increase speed and go back to ONE
                task_level = TaskLevels.ONE;
                speed_level = speed_level.getNext();
                break;
            default:
                System.out.println("WARN: Unrecognized task level");
                break;
        }
        return speed_level.getNumVal();
    }

    public void getNextTask() {
        //TODO - this should create tasks visual, choices and correct result based on current level (ignoring speed)
        task_visual = new String("3 + 6 = ");
        task_result = 9;
        task_choices = new int[4];
        task_choices[0] = 3;
        task_choices[1] = 7;
        task_choices[2] = 9;
        task_choices[3] = 3;
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
