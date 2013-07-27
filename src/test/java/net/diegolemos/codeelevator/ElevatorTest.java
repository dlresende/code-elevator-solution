package net.diegolemos.codeelevator;

import org.junit.Test;

import static net.diegolemos.codeelevator.Elevator.Direction.*;
import static org.fest.assertions.Assertions.assertThat;

public class ElevatorTest {

    @Test
    public void should_register_calls(){
        assertThat(new Elevator(0, UP).callFrom(0).callFrom(2).callFrom(4).getCalls()).isEqualTo(new boolean[]{true, false, true, false, true, false});
        assertThat(new Elevator(0, UP).callFrom(0).callFrom(1).callFrom(2).callFrom(3).callFrom(4).callFrom(5).getCalls()).isEqualTo(new boolean[]{true,true,true,true,true,true});
        assertThat(new Elevator(0, UP).callFrom(2).callFrom(2).getCalls()).isEqualTo(new boolean[]{false,false,true,false,false,false});
        assertThat(new Elevator(0, UP).callFrom(6).getCalls()).isEqualTo(new boolean[]{false,false,false,false,false,false});
    }

    @Test
    public void should_get_the_next_floor_considering_the_current_sense() {
        Elevator elevator = new Elevator(3, UP);

        elevator.callFrom(5).callFrom(2).callFrom(1);

        assertThat(elevator.getNextFloor()).isEqualTo(5);
    }

    @Test
    public void should_do_nothing_when_there_are_no_calls() {
        Elevator elevator = new Elevator(0, UP);

        assertThat(elevator.nextCommand()).isEqualTo("NOTHING");
        assertThat(elevator.nextCommand()).isEqualTo("NOTHING");
    }

    @Test
    public void should_serve_in_scan_mode() {
        Elevator elevator = new Elevator(0, UP);

        elevator.callFrom(1);

        assertThat(elevator.nextCommand()).isEqualTo("UP");
        assertThat(elevator.nextCommand()).isEqualTo("OPEN");
        assertThat(elevator.nextCommand()).isEqualTo("CLOSE");
        assertThat(elevator.nextCommand()).isEqualTo("NOTHING");
        assertThat(elevator.getFloor()).isEqualTo(1);

        elevator.callFrom(3).callFrom(0);

        assertThat(elevator.nextCommand()).isEqualTo("UP");
        assertThat(elevator.nextCommand()).isEqualTo("UP");
        assertThat(elevator.nextCommand()).isEqualTo("OPEN");
        assertThat(elevator.nextCommand()).isEqualTo("CLOSE");
        assertThat(elevator.nextCommand()).isEqualTo("DOWN");
        assertThat(elevator.nextCommand()).isEqualTo("DOWN");
        assertThat(elevator.nextCommand()).isEqualTo("DOWN");
        assertThat(elevator.nextCommand()).isEqualTo("OPEN");
        assertThat(elevator.nextCommand()).isEqualTo("CLOSE");
        assertThat(elevator.nextCommand()).isEqualTo("NOTHING");
        assertThat(elevator.getFloor()).isEqualTo(0);
    }
}
