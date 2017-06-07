import java.util.Arrays;
import java.util.List;

public class InitialGrids {

    private Grid [][] map;
    private List<Grid> gridList;
    private static final double GRID_LENGTH = 10.0;

//    public InitialGrids() {
//        this(4);
//    }

    InitialGrids(int num_grids_each_line) {

        double half = GRID_LENGTH / 2.0;
        double oneGrid = GRID_LENGTH;
        double twoGrids = GRID_LENGTH * 2;
        double threeGrids = GRID_LENGTH * 3;

        Grid A1 = new Grid("A1", half, half);
        Grid A2 = new Grid("A2", oneGrid+half, half);
        Grid A3 = new Grid("A3", twoGrids+half, half);
        Grid A4 = new Grid("A4", threeGrids+half, half);

        Grid B1 = new Grid("B1", half, oneGrid+half);
        Grid B2 = new Grid("B2", oneGrid+half, oneGrid+half);
        Grid B3 = new Grid("B3", twoGrids+half, oneGrid+half);
        Grid B4 = new Grid("B4", threeGrids+half, oneGrid+half);

        Grid C1 = new Grid("C1", half, twoGrids+half);
        Grid C2 = new Grid("C2", oneGrid+half, twoGrids+half);
        Grid C3 = new Grid("C3", twoGrids+half, twoGrids+half);
        Grid C4 = new Grid("C4", threeGrids+half, twoGrids+half);

        Grid D1 = new Grid("D1", half, threeGrids+half);
        Grid D2 = new Grid("D2", oneGrid+half, threeGrids+half);
        Grid D3 = new Grid("D3", twoGrids+half, threeGrids+half);
        Grid D4 = new Grid("D4", threeGrids+half, threeGrids+half);

        A1.setNeighbors(Arrays.asList(A1, B1, B2, A2));
        A2.setNeighbors(Arrays.asList(A2, A1, B1, B2, B3, A3));
        A3.setNeighbors(Arrays.asList(A3, A2, B2, B3, B4, A4));
        A4.setNeighbors(Arrays.asList(A4, A3, B3, B4));

        B1.setNeighbors(Arrays.asList(B1, C1, C2, B2, A2, A1));
        B2.setNeighbors(Arrays.asList(B2, C1, C2, C3, B1, B3, A1, A2, A3));
        B3.setNeighbors(Arrays.asList(B3, C2, C3, C4, B2, B4, A2, A3, A4));
        B4.setNeighbors(Arrays.asList(B4, C3, C4, B3, A3, A4));

        C1.setNeighbors(Arrays.asList(C1, D1, D2, C2, B1, B2));
        C2.setNeighbors(Arrays.asList(C2, D1, D2, D3, C1, C3, B1, B2, B3));
        C3.setNeighbors(Arrays.asList(C3, D2, D3, D4, C2, C4, B2, B3, B4));
        C4.setNeighbors(Arrays.asList(C4, D3, D4, C3, B3, B4));

        D1.setNeighbors(Arrays.asList(D1, D2, C1, C2));
        D2.setNeighbors(Arrays.asList(D2, D1, D3, C1, C2, C3));
        D3.setNeighbors(Arrays.asList(D3, D2, D4, C2, C3, C4));
        D4.setNeighbors(Arrays.asList(D4, D3, C3, C4));

//        map = new Grid[num_grids_each_line][num_grids_each_line];
        this.map = new Grid[][]{{A1, A2, A3, A4}, {B1, B2, B3, B4}, {C1, C2, C3, C4}, {D1, D2, D3, D4}};
        this.gridList = Arrays.asList(A1, A2, A3, A4, B4, B3, B2, B1, C1, C2, C3, C4, D4, D3, D2, D1);
//        map[0] = {A1, A2, A3, A4};
//        map[1] = {B1, B2, B3, B4};
//        map[2] = {C1, C2, C3, C4};
//        map[3] = {D1, D2, D3, D4};
    }

    public Grid [][] getMap() {
        return map;
    }

    public List<Grid> getGridList() {
        return gridList;
    }
}
