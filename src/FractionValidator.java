import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class FractionValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {

        String msg = "\"" + name + "\": should be a fraction between 0 and 1," +
                     "  \"" + value + "\" given";

        try {
            double d = Double.parseDouble(value);
            if (d <= 0 || d >= 1) {
                throw new ParameterException(msg);
            }
        } catch (Exception e) {
            throw new ParameterException(msg);
        }
    }
}
