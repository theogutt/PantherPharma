package dal.DTO.MaybeUseless;

import dal.DTO.Indholdsstof;

public interface IRåvareBatch {
    @Override
    String toString();

    int getId() ;

    int getIndholdsstof() ;

    double getMængde();

    String getProducent() ;

    void setId(int id) ;

    void setIndholdsstof(int indholdsstof);

    void setMængde(double mængde);

    void setProducent(String producent);
}
