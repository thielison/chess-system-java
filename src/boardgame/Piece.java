package boardgame;

public abstract class Piece {

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
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) { // testa se a pe�a pode mover para uma determinada posi��o
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) { // se a matriz na linha i coluna j for verdadeiro, existe movimento poss�vel e retorna true
					return true;
				}
			}
		}
		return false; // se a varredura da matriz esgotar e em nenhum lugar retornar true, ent�o nenhuma posi��o era verdadeira
	}
}
