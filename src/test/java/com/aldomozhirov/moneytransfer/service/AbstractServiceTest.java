package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import org.junit.BeforeClass;

public abstract class AbstractServiceTest {

    @BeforeClass
    public static void setUp() throws Exception {
        RepositoryFactory.create();
    }

}
