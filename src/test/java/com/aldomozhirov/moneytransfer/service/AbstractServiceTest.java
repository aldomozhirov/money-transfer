package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.AbstractTest;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import org.junit.BeforeClass;

public abstract class AbstractServiceTest extends AbstractTest {

    @BeforeClass
    public static void setUp() throws Exception {
        RepositoryFactory.create();
        addSampleData(RepositoryFactory.getInstance());
    }

}
