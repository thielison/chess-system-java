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
		
		while (true) {
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
