
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Place extends DomainEntity {

	private int	rowP;
	private int	columnP;


	@NotBlank
	@Range(min = 1)
	public int getrowP() {
		return this.rowP;
	}

	public void setrowP(final int rowP) {
		this.rowP = rowP;
	}

	@NotBlank
	@Range(min = 1)
	public int getcolumnP() {
		return this.columnP;
	}

	public void setcolumnP(final int columnP) {
		this.columnP = columnP;
	}

}
