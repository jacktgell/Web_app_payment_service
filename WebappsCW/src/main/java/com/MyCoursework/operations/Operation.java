package com.MyCoursework.operations;

import com.MyCoursework.ejb.TransactionEJB;

/**
 *
 * @author ianw
 */
public abstract class Operation {

    public abstract String execute(TransactionEJB bean);
}
