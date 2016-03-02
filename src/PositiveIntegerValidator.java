import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PositiveIntegerValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {

        String msg = "\"" + name + "\": should be a positive integer, " +
             "\"" + value + "\" given";

        try {
            int n = Integer.parseInt(value);
            if (n < 0) {
                throw new ParameterException(msg);
            }
        } catch (Exception e) {
            throw new ParameterException(msg);
        }
    }
}
