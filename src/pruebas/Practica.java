package pruebas;

public class Practica implements Comparable<Practica> {

	private String nombre;
	private String descripcion;
	private int cntRealizadas;
	private int cntExito;

	public Practica(String n, String d) {
		if (n.length() == 0 || n == null || d.length() == 0 || d == null)
			throw new AppException("Parametros de entrada erroneos");

		nombre = n;
		descripcion = d;
		cntRealizadas = 0;
		cntExito = 0;
	}

	public Practica(String n, String d, int realizadas, int exito) {
		if (n.length() == 0 || n == null || d.length() == 0 || d == null || realizadas < 0 || exito < 0)
			throw new AppException("Parametros de entrada erroneos");

		nombre = n;
		descripcion = d;
		cntRealizadas = realizadas;
		cntExito = exito;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCntRealizadas() {
		return cntRealizadas;
	}

	public int getCntExito() {
		return cntExito;
	}

	public void setCntRealizadas(int realizadas) {
		if (realizadas < 0)
			throw new AppException("Numero de practicas realizadas negativo: " + realizadas);

		cntRealizadas = realizadas;
	}

	public void setCntExito(int exito) {
		if (exito < 0)
			throw new AppException("Numero de practicas exitosas negativo: " + exito);

		cntExito = exito;
	}

	public int getPorcExito() {
		return 100 * (this.cntExito / this.cntRealizadas);
	}

	@Override
	public String toString() {
		return "(" + nombre + ", " + descripcion + ", " + cntRealizadas + ", " + cntExito + ", " + getPorcExito()
				+ "%)";
	}

	@Override
	public boolean equals(Object o) {
		boolean ok = o instanceof Practica;
		Practica p = ok ? (Practica) o : null;
		return ok && this.nombre.equalsIgnoreCase(p.nombre) && this.descripcion.equalsIgnoreCase(p.nombre);
	}

	@Override
	public int hashCode() {
		return nombre.toUpperCase().hashCode() + descripcion.toUpperCase().hashCode();
	}

	@Override
	public int compareTo(Practica p) {
		int c = this.nombre.compareToIgnoreCase(p.nombre);
		return c == 0 ? this.descripcion.compareToIgnoreCase(p.descripcion) : c;
	}

}
