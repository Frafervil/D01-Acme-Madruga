package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private String status;
	private String rejectionReason;
	private Place place;

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
	public Place getPlace() {
		return this.place;
	}

	public void setPlace(final Place place) {
		this.place = place;
	}

}
