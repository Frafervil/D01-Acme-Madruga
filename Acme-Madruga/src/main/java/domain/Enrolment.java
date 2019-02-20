
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Enrolment extends DomainEntity {

	private Date	enrolmentMoment;
	private Date	dropOutMoment;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEnrolmentMoment() {
		return this.enrolmentMoment;
	}

	public void setEnrolmentMoment(final Date enrolmentMoment) {
		this.enrolmentMoment = enrolmentMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDropOutMoment() {
		return this.dropOutMoment;
	}

	public void setDropOutMoment(final Date dropOutMoment) {
		this.dropOutMoment = dropOutMoment;
	}


	// Relationships----------------------------------------------

	private Position	position;
	private Member		member;
	private Brotherhood	brotherhood;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
