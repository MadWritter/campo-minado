package git.jeanvictor.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import git.jeanvictor.cm.excecao.ExplosaoException;

class CampoTeste {

	private Campo campo;
	
	/*
	 * Essa anotation abaixo significa que antes de cada teste
	 * será executado o método que toma essa anotação
	 */
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia1Emcima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia1Embaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2CimaEsquerda() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia2CimaDireita() {
		Campo vizinho = new Campo(2, 4);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia2BaixoEsquerda() {
		Campo vizinho = new Campo(4, 2);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia2BaixoDireita() {
		Campo vizinho = new Campo(4, 4);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoFalsoDistancia1() {
		Campo vizinho = new Campo(3, 1);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeVizinhoFalsoDistancia2() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = 
				campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeAbrirNaoMarcadoNaoMinado() {
		campo.abrir();
		assertTrue(campo.isAberto());
	}
	
	@Test
	void testeAbrirMarcadoNaoMinado() {
		campo.alternarMarcacao();
		campo.abrir();
		assertFalse(campo.isAberto());
	}
	
	@Test
	void testeAbrirMarcadoMinado() {
		campo.alternarMarcacao();
		campo.minar();
		campo.abrir();
		assertFalse(campo.isAberto());
	}
	
	@Test
	void testeAbrirNaoMarcadoMinado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAberturaVizinho1() {
		Campo campo22 = new Campo(2, 2);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo.isAberto() && campo22.isAberto());
	}
	
	@Test
	void testeAberturaVizinho2() {
		Campo campo22 = new Campo(2, 2);
		Campo campo11 = new Campo(1, 1);
		
		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo.isAberto() && campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAberturaVizinho3() {
		Campo campo22 = new Campo(2, 2);
		Campo campo12 = new Campo(1, 2);
		
		campo12.minar();
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo.isAberto() && campo12.isFechado());
	}
	@Test
	void testeLinhaeColuna() {
		boolean linha = campo.getLinha() == 3;
		boolean coluna = campo.getColuna() == 3;
		
		assertTrue(linha && coluna);
	}
	
	@Test
	void testeObjetivoMarcado() {
		campo.minar();
		campo.alternarMarcacao();
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoAbrirCampoVazio() {
		campo.abrir();
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeCampoMarcado() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test 
	void testeMinasEmVolta() {
		Campo c2 = new Campo(2, 3);
		Campo c3 = new Campo(4, 3);
		
		c2.minar();
		campo.adicionarVizinho(c2);
		campo.adicionarVizinho(c3);
		
		boolean minasEmVolta = campo.minasNaVizinhanca() == 1;
		
		assertTrue(minasEmVolta);
	}
	
	@Test
	void reiniciarCampoMarcado() {
		campo.alternarMarcacao();
		campo.reiniciar();
		
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void reiniciarCampoAberto() {
		campo.abrir();
		campo.reiniciar();
		
		assertFalse(campo.isAberto());
	}
	
	@Test
	
	void reiniciarCampoMinado() {
		campo.minar();
		campo.reiniciar();
		
		assertFalse(campo.isMinado());
	}
	
	@Test
	void testarStringMarcado() {
		campo.alternarMarcacao();
		boolean string = campo.toString() == "x";
		
		assertTrue(string);
	}
	
	@Test
	void testarStringAbertoMinado() {
		campo.abrir();
		campo.minar();
		boolean string = campo.toString() == "*";
		
		assertTrue(string);
	}
	
	@Test
	void testarStringAbertoComMinasEmVolta() {
		Campo c1 = new Campo(2, 3);
		Campo c2 = new Campo(4, 3);
		c1.minar();
		c2.minar();
		campo.adicionarVizinho(c1);
		campo.adicionarVizinho(c2);
		
		campo.abrir();
		// quando mostrar o campo, deve mostrar a quantidade
		// de minas em volta
		System.out.println(campo);
	}
	
	@Test
	void testarStringAbertoSemMinasEmVolta() {		
		campo.abrir();
		// tem que mostrar um campo vazio " "
		System.out.println(campo);
	}
	
	@Test
	void testarStringCampoInicial() {		
		// tem que mostrar um campo "?"
		System.out.println(campo);
	}
	
	@Test
	void testeReiniciarCampos() {
		campo.reiniciar();
		
		assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
	}
	
	@Test
	void testeSetAberto() {
		campo.setAberto(true);
		
		assertTrue(campo.isAberto());
	}
}