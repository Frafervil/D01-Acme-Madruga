package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	// Relationships----------------------------------------------

	private Collection<Request> requests;
	private Collection<Enrolment> enrolments;
	private Collection<DropOut> dropOuts;

	@NotNull
	@OneToMany(mappedBy = "member")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@NotNull
	@OneToMany(mappedBy = "member")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	@NotNull
	@OneToMany(mappedBy = "member")
	public Collection<DropOut> getDropOuts() {
		return this.dropOuts;
	}

	public void setDropOuts(final Collection<DropOut> dropOuts) {
		this.dropOuts = dropOuts;
	}

}
