package afte9.com.fastmath10;


public final class TaskProvider {
    private static final TaskProvider ourInstance = new TaskProvider();

    public enum TaskLevels { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN;
        public TaskLevels getNext() {
            if (this == SEVEN) return SEVEN;
            else return TaskLevels.values()[this.ordinal() +1];
        }
    }
    public enum SpeedLevels { SLOW(30000), NORMAL(15000), FAST(5000);
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

    public static TaskProvider getInstance() {
        return ourInstance;
    }

    private TaskProvider() {
        //Init to initial speed and task levels
        this.task_level = TaskLevels.ONE;
        this.speed_level = SpeedLevels.SLOW;
    }

    public int increaseLevel() {
        // Returns new timeout in milliseconds

        switch (task_level) {
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
                task_level = task_level.getNext(); //Just increase task level, keep speed.
                break;
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

    public String getTaskVisual() {
        //TODO Implement task visual
        return new String("1 + 2 = ");
    }

    public int getTaskResult() {
        //TODO implement task result
        return 13;
    }

    public int[] getResultChoices() {
        int res[] = new int[4];
        res[0] = 5;
        res[1] = 7;
        res[2] = 9;
        res[3] = 1;
        return res;
    }

    public int getTimeout() {
        return speed_level.getNumVal();
    }
}
