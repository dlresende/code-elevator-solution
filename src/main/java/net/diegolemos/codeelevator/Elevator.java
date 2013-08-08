package net.diegolemos.codeelevator;

import static net.diegolemos.codeelevator.Elevator.Direction.DOWN;
import static net.diegolemos.codeelevator.Elevator.Direction.UP;

public class Elevator {
    private static final int NUMBER_OF_FLOORS = 6;

    private final boolean[] calls = new boolean[NUMBER_OF_FLOORS];

    private boolean areDoorsOpen = false;
    private Direction direction = UP;
    private int floor = 0;

    public Elevator() {
        this(0, UP);
    }

    Elevator(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public boolean[] getCalls() {
        return calls;
    }

    public int getFloor() {
        return floor;
    }

    public Elevator goTo(int callingFloor) {
        if(callingFloor < NUMBER_OF_FLOORS)
            calls[callingFloor] = true;

        return this;
    }

    public String nextCommand() {
        if(areDoorsOpen) {
            areDoorsOpen = false;
            return "CLOSE";
        }
        else if(hasSomeoneCalledFrom(floor)) {
            calls[floor] = false;
            areDoorsOpen = true;
            return "OPEN";
        }
        else if(getNextFloor() > floor)
        {
            floor++;
            return "UP";
        }
        else if(getNextFloor() < floor)
        {
            floor--;
            return "DOWN";
        }
        else
            return "NOTHING";
    }

    int getNextFloor() {
        if(direction == UP) {
            for(int newFloor = floor; newFloor < NUMBER_OF_FLOORS; newFloor++) {
                if(hasSomeoneCalledFrom(newFloor))
                    return newFloor;
            }

            direction = DOWN;
        }
        else if(direction == DOWN) {
            for(int newFloor = floor; newFloor >= 0; newFloor--) {
                if(hasSomeoneCalledFrom(newFloor))
                    return newFloor;
            }

            direction = UP;
        }

        return floor;
    }

    private boolean hasSomeoneCalledFrom(int floor) {
        return calls[floor];
    }

    enum Direction {UP, DOWN}
}
