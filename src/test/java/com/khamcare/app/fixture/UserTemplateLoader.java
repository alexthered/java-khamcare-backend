package com.khamcare.app.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.khamcare.app.model.User;
import com.khamcare.app.util.RandomString;

public class UserTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(User.class).addTemplate("valid", new Rule() {{
            add("firstName", firstName());
            add("lastName", lastName());
            add("email", "${firstName}.${lastName}@gmail.com");
            add("password", new RandomString(10).nextString());
            add("passwordConfirmation", "${password}");
        }});
    }
}
