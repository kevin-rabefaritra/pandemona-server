package studio.startapps.pandemona.util;

import org.springframework.lang.NonNull;
import org.springframework.util.DigestUtils;

public interface StringUtils {

    static String md5(@NonNull String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }
}