package com.khamcare.app.request;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.khamcare.app.AppApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("integrationtest")
public class BaseControllerTestHelper {

    @BeforeClass
    public static void init() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.khamcare.app.fixture");
    }
}
