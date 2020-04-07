package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;

public class Charon extends God{

    public Charon(){
        this.name = "Charon";
        this.description = "Before your worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if the space is unoccupied";
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y){
        //bozza manca setuppare la condizione pregressa alla move, la move dovrebbe restare invece invariata
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                int debuff = (selectedCell.getWorker().isDebuff()) ? 0: 1;
                if ((selectedCell.getBuilding() == board.getCell(x, y).getBuilding() + debuff) || (selectedCell.getBuilding() >= board.getCell(x, y).getBuilding())) {
                    if (!board.getCell(x, y).getDome() && board.getCell(x, y).getWorker() == null) {
                        board.getCell(x, y).setWorker(selectedCell.getWorker());
                        selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - selectedCell.getBuilding());
                        selectedCell.setWorker(null);
                    } else throw new RuntimeException("Target cell is occupied");
                } else throw new RuntimeException("Target cell is too high/low");
            } else throw new RuntimeException("Target cell is too far");
        } else throw new RuntimeException("Target cell out of board");
        return 1;
    }
}
