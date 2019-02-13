package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Place extends DomainEntity {

	private int row;
	private int column;

	@NotBlank
	@Range(min = 1)
	public int getRow() {
		return this.row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	@NotBlank
	@Range(min = 1)
	public int getColumn() {
		return this.column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}

}
