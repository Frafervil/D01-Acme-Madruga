package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	private String systemName;
	private String welcomeBanner;
	private String welcomeMessage;
	private String countryCode;

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getWelcomeBanner() {
		return this.welcomeBanner;
	}

	public void setWelcomeBanner(final String welcomeBanner) {
		this.welcomeBanner = welcomeBanner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@Pattern(regexp = "^\\+?\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

}
