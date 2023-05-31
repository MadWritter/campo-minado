package git.jeanvictor.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import git.jeanvictor.cm.excecao.ExplosaoException;

public class Campo {
	/*
	 * atributos do campo
	 */
	private final int linha;
	private final int coluna;
	
	// propriedades do campo
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	// lista que irá armazenas os campos vizinhos do campo atual
	private List<Campo> vizinhos = new ArrayList<>();
	
	// o construtor diz em qual linha e coluna esse campo está
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		// critérios para diagonal
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - vizinho.linha);
		int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
		// se o resultado der 1, significa que está na mesma LINHA, e 1 de distância
		// se der dois, está na DIAGONAL, e a 1 de distância
		// esses são os únicos resultados possíveis para um vizinho
		int deltaGeral = deltaColuna + deltaLinha;
		
		// lógica para adicionar o campo vizinho
		
		// caso delta 1 e não estiver na diagonal, é  vizinho
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} 
		// caso delta 2 e estiver na diagonal, é vizinho
			else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	void alternarMarcacao() {
		// só podemos alternar a marcação de um campo fechado
		if (aberto == false) {
			// se estiver marcado, ele desmarca
			// se estiver desmarcado, ele marca.
			marcado = !marcado;
		}
	}
	
	// método para abrir um campo
	boolean abrir() {
		// se não estiver aberto e não estiver marcado
		if (!aberto && !marcado) {
			// o campo é aberto
			aberto = true;
			
			// se estiver minado
			if (minado) {
				// lança uma explosão
				throw new ExplosaoException();
			}
			
			// se a vizinhança do campo aberto for segura
			if (vizinhancaSegura()) {
				// abra todos os vizinhos com chamada recursiva
				// até que vizinhancaSegura seja falso
				vizinhos.forEach(v -> v.abrir());
			}
			// e retorne true para informar que o campo foi aberto
			return true;
		} else {
		// caso não obedeça o primeiro if, retorne false
		return false;
		}
	}
	
	// método para determinar se os vizinhos do campo são seguros
	boolean vizinhancaSegura() {
		// simplesmente nenhum campo dentro dos vizinhos pode estar minado
		return vizinhos.stream()
				.noneMatch(v -> v.minado);
	}
	
	boolean isMarcado() {
		return marcado;
	}
	
	boolean isAberto() {
		return aberto;
	}
	
	boolean isMinado() {
		return minado;
	}
	
	boolean isFechado() {
		return !isAberto();
	}
	
	void setAberto(boolean valor) {
		aberto = valor;
	}
	
	void minar() {
		minado = true;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	/*
	 * O objetivo é alcançado quando o campo minado é marcado
	 * ou o campo sem mina é aberto
	 */
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	/*
	 * Vai retornar o número com a quantidade de bombas
	 * em volta daquele campo
	 */
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	// reiniciando os campos para default
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	/*
	 * Os casos possíveis do campo:
	 * 1 - foi marcado: x
	 * 2 - foi aberto e estava minado: *
	 * 3 - foi aberto e tinha minas na vizinhança: retorna o número de minas em string
	 * 4 - foi aberto e não tinha mina: (String vazia)
	 * 5 - não foi aberto: ?
	 */
	@Override
	public String toString() {
		if (marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
}
