package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) { // enquanto n�o estiver em checkMate, roda o programa normal. Acontenceu um checkMate, o while vai falar e vai limpar a tela, mostrando a partida finalizada
			try {
				UI.clearScreen(); // limpa a tela a cada vez que voltar no in�cio do while, ou seja, a cada movimento de posi��es de pe�as
				UI.printMatch(chessMatch, captured); // UI = User Interface, imprime o tabuleiro na tela
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc); // usu�rio insere a posi��o de origem
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // usu�rio insere a posi��o de destino
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // sempre que efetuar um movimento e esse movimento resultar em uma pe�a capturada...
				if (capturedPiece != null) { // se a pe�a capturada for diferente de nulo, significa que algum pe�a foi capturada
					captured.add(capturedPiece); // e adiciona ela na lista de pe�as capturadas
				}
				
				if (chessMatch.getPromoted() != null) { // se essa partida.getPromoted for diferente de nulo, significa que uma pe�a foi promovida
					System.out.print("Enter piece for promotion (B/N/R/Q): "); // e pede para o usu�rio uma pe�a que ele quer que seja promovida
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen(); // Acontenceu um checkMate, o while vai falar e vai limpar a tela, mostrando a partida finalizada
		UI.printMatch(chessMatch, captured);
	}
}
