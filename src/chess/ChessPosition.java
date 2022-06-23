package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		// PROGRAMAÇÃO DEFENSIVA:
		// O tabuleiro vai de 'a' até 'h' e de 1 até 8, somente
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition: Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	// métodos setColumn e setRow excluídos
	// a coluna e a linha não podem ser livremente alteradas
	
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; 
		// imprime letra e número da posição da peça: "a1", "b2", etc...
		// o string vazio "" é um macete pra concatenar automático. Se tirar esse string vazio, o compilador não vai aceitar
		// se coloca o string vazio pro compilador entender que isso é uma concatenação de strings
	}
}
