package cinema;

import icinema.*;

import javax.jws.WebService;
import java.util.List;

@WebService(
        name = "Cinema",
        portName = "ICinema_HttpSoap11_Port",
        endpointInterface = "icinema.ICinema",
        targetNamespace = "http://www.iit.bme.hu/soi/hw/SeatReservation",
        wsdlLocation = "WEB-INF/wsdl/SeatReservation.wsdl"
)
public class Cinema implements ICinema {

    private static final CinemaSeatList seatList = new CinemaSeatList();
    private static final CinemaLockList lockList = new CinemaLockList();

    @Override
    public void init(int rows, int columns) throws ICinemaInitCinemaException {
        if (rows < 1 || rows > 26)
            throw new ICinemaInitCinemaException("Invalid row parameter.", new CinemaException());
        if (columns < 1 || columns > 100)
            throw new ICinemaInitCinemaException("Invalid column parameter.", new CinemaException());
        lockList.init();
        seatList.init(rows, columns);
    }

    @Override
    public ArrayOfSeat getAllSeats() throws ICinemaGetAllSeatsCinemaException {
        return seatList.toArrayOfSeat();
    }

    @Override
    public SeatStatus getSeatStatus(Seat seat) throws ICinemaGetSeatStatusCinemaException {
        CinemaSeat cSeat = seatList.findSeatByValue(seat);
        if (cSeat == null)
            throw new ICinemaGetSeatStatusCinemaException("Seat doesn't exist", new CinemaException());
        return cSeat.getStatus();
    }

    @Override
    public String lock(Seat seat, int count) throws ICinemaLockCinemaException {
        int row = CinemaSeatList.getSeatRowId(seat);
        List<CinemaSeat> lockableSeats = seatList.getRowSeats(row, CinemaSeatList.getSeatColumnId(seat), count);
        if (lockableSeats == null)
            throw new ICinemaLockCinemaException("Target seat(s) not exist", new CinemaException());
        for (CinemaSeat cSeat : lockableSeats)
            if (cSeat.getStatus() != SeatStatus.FREE)
                throw new ICinemaLockCinemaException("Target seat(s) not lockable.", new CinemaException());
        return lockList.lock(lockableSeats);
    }

    @Override
    public void unlock(String lockId) throws ICinemaUnlockCinemaException {
        if (!lockList.contains(lockId))
            throw new ICinemaUnlockCinemaException("The specified lock does not exist.", new CinemaException());
        if (lockList.getStatus(lockId) != SeatStatus.LOCKED)
            throw new ICinemaUnlockCinemaException("The specified seats are not locked.", new CinemaException());
        lockList.setStatus(lockId, SeatStatus.FREE);
    }

    @Override
    public void reserve(String lockId) throws ICinemaReserveCinemaException {
        if (!lockList.contains(lockId))
            throw new ICinemaReserveCinemaException("The specified lock does not exist.", new CinemaException());
        if (lockList.getStatus(lockId) != SeatStatus.LOCKED)
            throw new ICinemaReserveCinemaException("The specified seats are not locked.", new CinemaException());
        lockList.setStatus(lockId, SeatStatus.RESERVED);
    }

    @Override
    public void buy(String lockId) throws ICinemaBuyCinemaException {
        if (!lockList.contains(lockId))
            throw new ICinemaBuyCinemaException("The specified lock does not exist.", new CinemaException());
        if (lockList.getStatus(lockId) != SeatStatus.LOCKED)
            throw new ICinemaBuyCinemaException("The specified seats are not locked.", new CinemaException());
        lockList.setStatus(lockId, SeatStatus.SOLD);
    }
}
