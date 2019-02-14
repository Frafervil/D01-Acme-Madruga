
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	private String				title;
	private Date				establishmentDate;
	private Collection<String>	pictures;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	public Date getEstablishmentDatet() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}


	// Relationships----------------------------------------------

	private Collection<Procession>	processions;
	private Collection<FloatB>		floatBs;


	@NotNull
	@Valid
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	@NotNull
	@Valid
	public Collection<FloatB> getFloatBs() {
		return this.processions;
	}

	public void setFloatBs(final Collection<FloatB> floatBs) {
		this.floatBs = floatBs;
	}
}
