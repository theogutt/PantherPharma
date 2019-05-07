package dal.DAO;

import dal.DTO.MaybeUseless.IIndholdsstof;

public interface IIndholdsstofDAO extends IDAO<IIndholdsstof> {
    IIndholdsstof get(int id) throws DALException;

}
