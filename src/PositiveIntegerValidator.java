import com.beust.jcommander.*;

public class PositiveIntegerValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {

        String msg = "Parameter " + name + " should be a positive integer"
            + " (found " + value + ")";

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
