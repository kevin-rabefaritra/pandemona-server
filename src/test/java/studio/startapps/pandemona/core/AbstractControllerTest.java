package studio.startapps.pandemona.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;

public abstract class AbstractControllerTest {

    @MockitoBean
    protected AuthenticationService authenticationService;

    @Autowired
    protected MockMvc mockMvc;
}
