package studio.startapps.pandemona.stats;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class EndpointTrackingAspect {

    private final EndpointCounterService endpointCounterService;

    @After("@annotation(studio.startapps.pandemona.stats.internal.TrackEndpointUsage)")
    public void afterTrackedEndpoint(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String fullPath = extractFullPath(method);
        if (!fullPath.isBlank()) {
            endpointCounterService.logRequest(fullPath);
        }
    }

    private String extractFullPath(Method method) {
        String basePath = "";
        Class<?> controllerClass = method.getDeclaringClass();

        if (controllerClass.isAnnotationPresent(org.springframework.web.bind.annotation.RequestMapping.class)) {
            String[] basePaths = controllerClass
                    .getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class)
                    .value();
            if (basePaths.length > 0) basePath = basePaths[0];
        }

        String methodPath = extractMethodPath(method);
        if (methodPath == null) methodPath = "";

        return basePath + methodPath;
    }

    private String extractMethodPath(Method method) {
        if (method.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class)) {
            return getFirst(method.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class).value());
        } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.PostMapping.class)) {
            return getFirst(method.getAnnotation(org.springframework.web.bind.annotation.PostMapping.class).value());
        } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.PutMapping.class)) {
            return getFirst(method.getAnnotation(org.springframework.web.bind.annotation.PutMapping.class).value());
        } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.DeleteMapping.class)) {
            return getFirst(method.getAnnotation(org.springframework.web.bind.annotation.DeleteMapping.class).value());
        } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.RequestMapping.class)) {
            return getFirst(method.getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class).value());
        }
        return null;
    }

    private String getFirst(String[] arr) {
        return (arr != null && arr.length > 0) ? arr[0] : "";
    }
}
