package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import domain.FloatB;

public class FormObjectProcessionFloatB {

	// Atributos de Procession que se quieren editar en el formulario

	private String title;
	private String description;
	private Date moment;
	private String ticker;
	private boolean isDraft;
	private int maxRow;
	private int maxColumn;

	// FloatBs que se quieren añadir o eliminar de la Procession en el
	// formulario

	private Collection<FloatB> floatBs;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|31(?!(?: 0[2469]|11))|30(?!02))-[A-Z0-9]{6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	@NotNull
	@Range(min = 1)
	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	@NotNull
	@Range(min = 1)
	public int getMaxColumn() {
		return maxColumn;
	}

	public void setMaxColumn(int maxColumn) {
		this.maxColumn = maxColumn;
	}

}
