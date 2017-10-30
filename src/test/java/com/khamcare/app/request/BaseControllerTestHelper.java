package com.khamcare.app.request;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("integrationtest")
public class BaseControllerTestHelper {

    @BeforeClass
    public static void init() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.khamcare.app.fixture");
    }
}
