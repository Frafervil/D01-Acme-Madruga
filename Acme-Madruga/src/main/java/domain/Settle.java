
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Settle extends DomainEntity {

	private String				area;
	private Collection<String>	pictures;


	public String getArea() {
		return this.area;
	}
	public void setArea(final String area) {
		this.area = area;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}
	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
}
