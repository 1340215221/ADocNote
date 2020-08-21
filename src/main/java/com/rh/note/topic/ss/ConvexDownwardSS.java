package com.rh.note.topic.ss;


import com.rh.note.topic.BaseStreetStall;
import com.rh.note.topic.IStreetStall;
import com.rh.note.topic.Square;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Stream;

public class ConvexDownwardSS extends BaseStreetStall implements IStreetStall {
    public static Integer getNumber() {
        return 4;
    }

    @Override
    public Square.Cell getCell2() {
        if (cell2 == null) {
            cell2 = new Square.Cell(cell.getX() + 1, cell.getY());
        }
        return cell2;
    }

    @Override
    public Square.Cell getCell3() {
        if (cell3 == null) {
            cell3 = new Square.Cell(cell.getX() + 2, cell.getY());
        }
        return cell3;
    }

    @Override
    public Square.Cell getCell4() {
        if (cell4 == null) {
            cell4 = new Square.Cell(cell.getX() + 1, cell.getY() + 1);
        }
        return cell4;
    }

    /**
     * 通过数字匹配元素
     * 根据匹配数量生成地摊
     */
    public static Stream<BaseStreetStall> match(List<Integer> numbers) {
        if (CollectionUtils.isEmpty(numbers)) {
            return Stream.empty();
        }
        final long count = numbers.stream()
                .filter(n -> getNumber().equals(n))
                .count();
        if (count < 1) {
            return Stream.empty();
        }
        return Stream.generate(() -> (BaseStreetStall) new ConvexDownwardSS()).limit(count);
    }
}
