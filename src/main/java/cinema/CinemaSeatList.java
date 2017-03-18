package cinema;

import icinema.ArrayOfSeat;
import icinema.Seat;
import icinema.SeatStatus;

import java.util.ArrayList;
import java.util.List;

class CinemaSeatList {

    private List<List<CinemaSeat>> seats;

    public static int getSeatRowId(Seat seat) {
        return seat.getRow().charAt(0) - 65;
    }

    public static int getSeatColumnId(Seat seat) {
        return Integer.parseInt(seat.getColumn()) - 1;
    }

    public CinemaSeatList() {
        seats = new ArrayList<>();
    }

    public CinemaSeat getSeat(int row, int column) {
        return seats.get(row).get(column);
    }

    public void init(int rows, int columns) {
        seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            seats.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                CinemaSeat seat = new CinemaSeat();
                seat.setRow(Character.toString((char) (65 + i)));
                seat.setColumn(Integer.toString(j + 1));
                seat.setStatus(SeatStatus.FREE);
                seats.get(i).add(seat);
            }
        }
    }

    public List<CinemaSeat> getRowSeats(int row, int start, int count) {
        if (row < 0 || row >= seats.size())
            return null;
        if (start < 0 || count < 1 || start + count > seats.get(row).size())
            return null;
        List<CinemaSeat> someSeats = new ArrayList<>();
        for (int i = start; i < start + count; i++)
            someSeats.add(seats.get(row).get(i));
        return someSeats;
    }

    public ArrayOfSeat toArrayOfSeat() {
        ArrayOfSeat arrayOfSeat = new ArrayOfSeat();
        for (List<CinemaSeat> row : seats)
            for (Seat seat : row)
                arrayOfSeat.getSeat().add(seat);
        return arrayOfSeat;
    }

    public CinemaSeat findSeatByValue(Seat value) {
        int row = getSeatRowId(value);
        if (row < 0 || row > seats.size())
            return null;
        int column = getSeatColumnId(value);
        if (column < 0 || column > seats.get(row).size())
            return null;
        return getSeat(row, column);
    }
}
