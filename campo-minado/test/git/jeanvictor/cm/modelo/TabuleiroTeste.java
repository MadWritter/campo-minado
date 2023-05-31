package git.jeanvictor.cm.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuleiroTeste {

	Tabuleiro tabuleiro;
	@BeforeEach
	void criarTabuleiro() {
		tabuleiro = new Tabuleiro(6, 6, 6);
	}
	
	@Test
	void testeAbrirCampo() {
		tabuleiro.abrir(3, 2);
		System.out.println(tabuleiro);
	}
	
	@Test
	void testeMarcarCampo() {
		tabuleiro.alternarMarcacao(2, 2);
		System.out.println(tabuleiro);
	}

	@Test
	void reiniciarJogo() {
		tabuleiro.reiniciar();
		System.out.println(tabuleiro);
	}
}
