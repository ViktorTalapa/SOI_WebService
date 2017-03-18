package cinema;

import icinema.SeatStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CinemaLockList {

    private Map<String, List<CinemaSeat>> locks;

    public CinemaLockList() {
        init();
    }

    public void init() {
        locks = new HashMap<>();
    }

    public boolean contains(String lockId) {
        return locks.containsKey(lockId);
    }

    public SeatStatus getStatus(String lockId) {
        return locks.get(lockId).get(0).getStatus();
    }

    public void setStatus(String lockId, SeatStatus status) {
        List<CinemaSeat> seats = locks.get(lockId);
        for (CinemaSeat seat : seats)
            seat.setStatus(status);
    }

    public String lock(List<CinemaSeat> seats) {
        String lockId = Integer.toString(locks.size() + 1000);
        locks.put(lockId, seats);
        setStatus(lockId, SeatStatus.LOCKED);
        return lockId;
    }
}
