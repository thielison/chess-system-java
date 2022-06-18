package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) { 
		// para criar uma peça, precisa informar o Tabuleiro
		
		this.board = board;
		position = null; // a posição de uma peça recém criada será Null
	}

	protected Board getBoard() { 	
		// protected: somente classes dentro do mesmo pacote e subclasses que vão poder acessar o Tabuleiro onde estão as peças
		return board;
	}

	// setBoard não é necessário, não será permitido que o Tabuleiro da peça seja alterado
	
}
