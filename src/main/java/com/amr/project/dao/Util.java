package com.amr.project.dao;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static <X> X result(TypedQuery<X> typedQuery) {
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            Logger.getLogger("com.amr.project.dao.Util").log(Level.WARNING, e.getClass().getName());
            return null;
        }
    }
}
