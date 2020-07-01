package it.polimi.ingsw.model;

/**
 * Class MultipleActionGod for the gods that can do more times the same action
 */
public class MultipleActionGod extends God {

    /**
     * The Use.
     */
    protected int use = 0;
    /**
     * The Use limit.
     */
    protected int useLimit = 0;

    /**
     * Method CheckUse checks if is reached the useLimit
     *
     * @return int int
     */
    public int CheckUse() {
        if (this.use == this.useLimit) {
            this.use = 0;
            return 1;
        }
        return 2;
    }

    /** @see God#ResetGod() */
    @Override
    public void ResetGod() {
        use = 0;
    }
}
