
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.LanguagePositionRepository;
import domain.LanguagePosition;

@Component
@Transactional
public class StringToLanguagePositionConverter implements Converter<String, LanguagePosition> {

	@Autowired
	LanguagePositionRepository	languagePositionRepository;


	@Override
	public LanguagePosition convert(final String text) {
		LanguagePosition result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.languagePositionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
