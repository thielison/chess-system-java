package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean isThereOpponentPiece(Position position) { // acessível somente pelo mesmo pacote e pelas subclasses que são as peças
		ChessPiece p = (ChessPiece)getBoard().piece(position); // downcasting para ChessPiece
		return p != null && p.getColor() != color; // se a cor da peça dessa posição é diferente da cor da minha peça, ou seja, se é uma peça adversária
 	}
}
