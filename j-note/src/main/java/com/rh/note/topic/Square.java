package com.rh.note.topic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 广场
 */
@Data
public class Square {

    private List<FreeSpace> freeSpaces = new ArrayList<>();

    /**
     * 使用情况
     */
    private List<Cell> space = Arrays.asList(
            new Cell(0,0), new Cell(0,1), new Cell(0,2), new Cell(0,3), new Cell(0,4), new Cell(0,5), new Cell(0,6), new Cell(0,7),
            new Cell(1,0), new Cell(1,1), new Cell(1,2), new Cell(1,3), new Cell(1,4), new Cell(1,5), new Cell(1,6), new Cell(1,7),
            new Cell(2,0), new Cell(2,1), new Cell(2,2), new Cell(2,3), new Cell(2,4), new Cell(2,5), new Cell(2,6), new Cell(2,7),
            new Cell(3,0), new Cell(3,1), new Cell(3,2), new Cell(3,3), new Cell(3,4), new Cell(3,5), new Cell(3,6), new Cell(3,7)
    );

    /**
     * 添加地摊
     */
    public void add(IStreetStall streetStall) {
        if (freeSpaces.isEmpty()) {
            return;
        }
        // 遍历空间空闲节点
        space.forEach(cell -> {
            if (cell.value == 0) {
                return;
            }
            if (!streetStall.continuous(cell)) {
                return;
            }
            streetStall.add();
        });
        this.updateSpace(streetStall);
        this.removeForFreeSpace(streetStall);
        this.removeUnavailableSpace();
        // 判断地摊形状的位置是否有被占用
        // 可以放下地摊时, 修改空间值
        // 过滤剩余空间可以放下的地摊类型
    }

    private void updateSpace(IStreetStall streetStall) {
        if (streetStall.isFails()) {
            return;
        }
        streetStall.removeCellFrom(space);
    }

    /**
     * 移除地摊,从空闲空间中
     */
    private void removeForFreeSpace(IStreetStall streetStall) {
        freeSpaces = freeSpaces.stream()
                .peek(freeSpace -> freeSpace.remove(streetStall))
                .filter(freeSpace -> !freeSpace.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 删除小于4格的空闲空间
     */
    private void removeUnavailableSpace() {
        if (CollectionUtils.isEmpty(freeSpaces)) {
            return;
        }
        freeSpaces = freeSpaces.stream()
                .filter(FreeSpace::adequate)
                .collect(Collectors.toList());
    }

    /**
     * 打印空间占用情况
     */
    public void printSpace() {
        space.stream().sorted(Comparator.comparing(Cell::getX).thenComparing(Cell::getY)).reduce((a, b) -> {
            System.out.print(a.getValue() + "  ");
            if (a.getX() != b.getX()) {
                System.out.print("\n");
            }
            return b;
        });
    }

    /**
     * 空闲空间
     */
    @Data
    private class FreeSpace {
        private List<Cell> cells = new ArrayList<>();

        /**
         * 删除地摊占用的位置
         */
        public void remove(IStreetStall streetStall) {
            if (CollectionUtils.isEmpty(cells)) {
                return;
            }
            cells = cells.stream()
                    .filter(cell -> !streetStall.occupy(cell))
                    .collect(Collectors.toList());
        }

        public boolean isEmpty() {
            return CollectionUtils.isEmpty(cells);
        }

        /**
         * 空闲空间是否小于4格
         */
        public boolean adequate() {
            return CollectionUtils.isNotEmpty(cells) && cells.size() >= 4;
        }
    }

    /**
     * 单元格
     */
    @Data
    @NoArgsConstructor
    public static class Cell {
        private int x;
        private int y;
        private int value = 0;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object cell) {
            if (!(cell instanceof Cell)) {
                return super.equals(cell);
            }
            return this.x == ((Cell) cell).x && this.y == ((Cell) cell).y;
        }

        /**
         * 是否连续
         */
        public boolean continuous(Cell cell) {
            return this.x == cell.x && this.y == cell.y + 1 // 上面连续
                    || this.x == cell.x && this.y == cell.y - 1 // 下面连续
                    || this.x == cell.x - 1 && this.y == cell.y // 左边连续
                    || this.x == cell.x + 1 && this.y == cell.y; // 右边连续
        }
    }
}
