package dal.Objekter.MaybeUseless;

public class Opskrift_indhold {

    private int opskriftID;
    private int stofID;
    private int meangde;
    private boolean aktiv;

    public Opskrift_indhold(int opskriftID, int stofID, int meangde, boolean aktiv){
        this.opskriftID = opskriftID;
        this.stofID = stofID;
        this.meangde = meangde;
        this.aktiv = aktiv;
    }

    public int getOpskriftID(){
        return opskriftID;
    }




}
