package com.rh.note.topic.ss;

import com.rh.note.topic.BaseStreetStall;
import com.rh.note.topic.IStreetStall;
import com.rh.note.topic.Square;

public class RaisedToTheRightSS extends BaseStreetStall implements IStreetStall {
    public static Integer getNumber() {
        return 7;
    }

    @Override
    public Square.Cell getCell2() {
        if (cell2 == null) {
            cell2 = new Square.Cell(cell.getX(), cell.getY() + 1);
        }
        return cell2;
    }

    @Override
    public Square.Cell getCell3() {
        if (cell3 == null) {
            cell3 = new Square.Cell(cell.getX() + 1, cell.getY() + 1);
        }
        return cell3;
    }

    @Override
    public Square.Cell getCell4() {
        if (cell4 == null) {
            cell4 = new Square.Cell(cell.getX() + 2, cell.getY());
        }
        return cell4;
    }
}