
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LanguagePosition;

@Component
@Transactional
public class LanguagePositionToStringConverter implements Converter<LanguagePosition, String> {

	@Override
	public String convert(final LanguagePosition languagePosition) {
		String result;

		if (languagePosition == null)
			result = null;
		else
			result = String.valueOf(languagePosition.getId());
		return result;
	}

}
