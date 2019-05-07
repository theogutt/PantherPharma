package dal.DTO.MaybeUseless;

public interface IIndholdsstof {
    String toString();

    int getId();

    void setId(int id);

    void setName(String name);

    String getName();

    void setGenbestil(Boolean genbestil);

    Boolean getGenbestil();
}
