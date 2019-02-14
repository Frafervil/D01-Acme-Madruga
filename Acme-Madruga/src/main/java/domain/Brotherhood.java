
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
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@NotNull
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}


	// Relationships----------------------------------------------

	private Collection<Procession>	processions;
	private Collection<FloatB>		floatBs;
	private Collection<Enrolment>	enrolments;
	private Collection<DropOut>		dropOuts;


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
		return this.floatBs;
	}

	public void setFloatBs(final Collection<FloatB> floatBs) {
		this.floatBs = floatBs;
	}

	@NotNull
	@Valid
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	@NotNull
	@Valid
	public Collection<DropOut> getDropOuts() {
		return this.dropOuts;
	}

	public void setDropOuts(final Collection<DropOut> dropOuts) {
		this.dropOuts = dropOuts;
	}
}
