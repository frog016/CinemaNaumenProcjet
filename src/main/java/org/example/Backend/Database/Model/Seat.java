package org.example.Backend.Database.Model;

public class Seat {
    private int number;
    private int status;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        switch (status) {
            case None:
                this.status = 0;
                break;
            case Reserved:
                this.status = 1;
                break;
            case Selected:
                this.status = 2;
                break;
        }
    }
}

