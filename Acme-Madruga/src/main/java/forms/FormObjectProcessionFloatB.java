package forms;

import java.util.Collection;
import java.util.Date;

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

	// FloatBs que se quieren editar en el formulario

	private Collection<FloatB> floatBs;

}
