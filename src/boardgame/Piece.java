package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) { 
		// para criar uma pe�a, precisa informar o tabuleiro
		
		this.board = board;
		position = null; // a posi��o de uma pe�a rec�m criada ser� Null
	}

	protected Board getBoard() { 	
		// protected: somente classes dentro do mesmo pacote e subclasses que v�o poder acessar o tabuleiro onde est�o as pe�as
		return board;
	}

	// setBoard n�o � necess�rio, n�o ser� permitido que o tabuleiro da pe�a seja alterado
}
