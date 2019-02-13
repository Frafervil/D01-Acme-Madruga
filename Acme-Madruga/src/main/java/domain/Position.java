package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	private Collection<LanguagePosition> languagePositions;

	// Relationships----------------------------------------------
	@NotNull
	@OneToMany
	public Collection<LanguagePosition> getLanguagePositions() {
		return this.languagePositions;
	}

	public void setLanguagePositions(
			final Collection<LanguagePosition> languagePositions) {
		this.languagePositions = languagePositions;
	}
}
