
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	private String	email;


	@Pattern(regexp = "^[a-zA-Z0-9 ]*[<]?\\w+[@][a-zA-Z0-9.]+[>]?$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}


	// Relationships----------------------------------------------

	private Finder	finder;


	@NotNull
	@Valid
	@OneToOne(optional = false, mappedBy = "member")
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
