package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			try {
				UI.clearScreen(); // limpa a tela a cada vez que voltar no in�cio do while, ou seja, a cada movimento de posi��es de pe�as
				UI.printBoard(chessMatch.getPieces()); // UI = User Interface, imprime o tabuleiro na tela
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc); // usu�rio insere a posi��o de origem
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // usu�rio insere a posi��o de destino
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
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
	}
}
