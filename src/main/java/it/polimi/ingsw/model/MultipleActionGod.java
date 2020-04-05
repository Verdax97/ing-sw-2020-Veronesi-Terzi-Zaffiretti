package it.polimi.ingsw.model;

public class MultipleActionGod extends God
{
    protected int use = 0;
    protected int useLimit = 0;

    protected int CheckUse()
    {
        if (this.use == this.useLimit){
            this.use = 0;
            //aggiornare stato a building
            return 1;
        }
        return 2;
    }
}
