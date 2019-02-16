
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FloatB;

@Component
@Transactional
public class FloatBToStringConverter implements Converter<FloatB, String> {

	@Override
	public String convert(final FloatB floatB) {
		String result;

		if (floatB == null)
			result = null;
		else
			result = String.valueOf(floatB.getId());
		return result;
	}

}
