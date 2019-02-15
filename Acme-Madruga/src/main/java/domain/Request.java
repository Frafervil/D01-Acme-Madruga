
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private String		status;
	private String		rejectionReason;
	private Place		place;
	private Procession	procession;
	private Member		member;


	@NotBlank
	@Pattern(regexp = "^PENDING|APROVED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}

	public void setRejectionReason(final String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	// Relationships----------------------------------------------

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Place getPlace() {
		return this.place;
	}

	public void setPlace(final Place place) {
		this.place = place;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
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
}
