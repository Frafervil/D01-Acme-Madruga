
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@NotNull
	@ElementCollection
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
	@OneToMany
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	@NotNull
	@OneToMany
	public Collection<FloatB> getFloatBs() {
		return this.floatBs;
	}

	public void setFloatBs(final Collection<FloatB> floatBs) {
		this.floatBs = floatBs;
	}

	@NotNull
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	@NotNull
	@OneToMany(mappedBy = "brotherhood")
	public Collection<DropOut> getDropOuts() {
		return this.dropOuts;
	}

	public void setDropOuts(final Collection<DropOut> dropOuts) {
		this.dropOuts = dropOuts;
	}
}
