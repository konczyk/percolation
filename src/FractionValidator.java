import com.beust.jcommander.*;

public class FractionValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {

        String msg = "Parameter " + name + " should be a fraction between 0 and 1"
            + " (found " + value + ")";

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
