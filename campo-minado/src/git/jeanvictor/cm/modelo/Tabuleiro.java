package git.jeanvictor.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import git.jeanvictor.cm.excecao.ExplosaoException;

public class Tabuleiro {
	/*
	 * Atributos do campo:
	 * Quantidade de linhas
	 * Quantidade de colunas
	 * Quantidade de minas
	 * 
	 * e uma lista de Campos
	 */
	private int linhas;
	private int colunas;
	private int minas;
	private final List<Campo> campos = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	// filtra o campo com a linha e a coluna passada e abre
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
				  .filter(c -> c.getLinha() == linha &&
						  c.getColuna() == coluna)
				  .findFirst() // pegue o primeiro
				  .ifPresent(c -> c.abrir()); // se estiver presente, abra
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	// filtra o campo com a linha e a coluna passada e marca
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha &&
		c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	// gera os campos com as linhas e colunas informadas no construtor
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}
	}
	// esse método fará com que todos os campos tentem se fazer vizinhos
	// as regras que determinam vizinhos está no método adicionarVizinho
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	/*
	 * Método que sorteia as minas aleatoriamente
	 * baseado na quantidade que foi informada no construtor
	 */
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while(minasArmadas < minas);
	}
	
	// se todos os campos tem o objetivo alcançado, o jogador vence
	public boolean objetivoAlcancado() {
		// pra isso, todos os campos tem que passar true para o predicado
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	// reinicia o todos os campos e sorteia as minas novamente
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int linha = 0; linha < linhas; linha++) {
			sb.append(linha);
			sb.append(" ");
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}		
		return sb.toString();
	}
}
