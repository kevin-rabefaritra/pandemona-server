package studio.startapps.pandemona.core;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.configuration.SecurityConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public @interface ControllerTest {

    @AliasFor("controllers")
    Class<?>[] value() default {};

    Class<?>[] controllers() default {};
}
