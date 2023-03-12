package recruitment.hashapi.ui;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Component
public class UserInterface {

    private final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    public String messageBundle(String key, Object... arg) {
        return MessageFormat.format(getString(key), arg);
    }

    private String getString(String key) {
        return bundle.getString(key);
    }
}
