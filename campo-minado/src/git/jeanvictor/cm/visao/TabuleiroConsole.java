package git.jeanvictor.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import git.jeanvictor.cm.excecao.ExplosaoException;
import git.jeanvictor.cm.excecao.SairException;
import git.jeanvictor.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	/*
	 * Essa classe define a interface do usuário com o jogo
	 */
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}
	
	// aqui vai verificar se o usuário vai continuar jogando
	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar == true) {
				cicloDoJogo();
				
				System.out.println("Outra Partida? (S/n)");
				String resposta = entrada.nextLine();
				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("Até mais!");
		} finally {
			entrada.close();
		}
	}
	
	// a regra do jogo.
	private void cicloDoJogo() {
		try {
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				String digitado = capturarValorDigitado("Digite as coordenadas (x, y)");
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)marcar");
				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!!!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	
	// esse método é somente para facilitar a recepção de dados
	// quando o usuário precisar interagir
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		} else {
		return digitado;
		}
	}
}
