package git.jeanvictor.cm;

import git.jeanvictor.cm.modelo.Tabuleiro;
import git.jeanvictor.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}

}
