package dal.DTO.MaybeUseless;

import dal.DTO.Indholdsstof;

public interface IRåvareBatch {
    @Override
    String toString();

    int getId() ;

    boolean isGenbestil() ;

    int getIndholdsstof() ;

    int getMængde();

    String getProducent() ;

    void setId(int id) ;

    void setGenbestil(boolean genbestil);

    void setIndholdsstof(int indholdsstof);

    void setMængde(int mængde);

    void setProducent(String producent);
}
