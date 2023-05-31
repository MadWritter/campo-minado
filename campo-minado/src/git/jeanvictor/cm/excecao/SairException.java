package git.jeanvictor.cm.excecao;

public class SairException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Até mais";
	}
}
