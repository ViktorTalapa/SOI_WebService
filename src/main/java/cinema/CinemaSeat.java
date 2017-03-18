package cinema;

import icinema.Seat;
import icinema.SeatStatus;

class CinemaSeat extends Seat {

    private SeatStatus status;

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus seatStatus) {
        status = seatStatus;
    }

}
