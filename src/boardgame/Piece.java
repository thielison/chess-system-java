package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) { 
		// para criar uma peça, precisa informar o tabuleiro
		
		this.board = board;
		position = null; // a posição de uma peça recém criada será Null
	}

	protected Board getBoard() { 	
		// protected: somente classes dentro do mesmo pacote e subclasses que vão poder acessar o tabuleiro onde estão as peças
		return board;
	}

	// setBoard não é necessário, não será permitido que o tabuleiro da peça seja alterado
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) { // testa se a peça pode mover para uma determinada posição
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) { // se a matriz na linha i coluna j for verdadeiro, existe movimento possível e retorna true
					return true;
				}
			}
		}
		return false; // se a varredura da matriz esgotar e em nenhum lugar retornar true, então nenhuma posição era verdadeira
	}
}
