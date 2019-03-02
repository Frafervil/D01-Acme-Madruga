
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Settle;

@Component
@Transactional
public class SettleToStringConverter implements Converter<Settle, String> {

	@Override
	public String convert(final Settle settle) {
		String result;

		if (settle == null)
			result = null;
		else
			result = String.valueOf(settle.getId());
		return result;
	}

}
