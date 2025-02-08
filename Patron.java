import java.time.LocalDateTime;

public class Patron {
    private int patronId;
    private int patronPriority;
    private LocalDateTime timeOfReservation;

    // Getter and setter methods
    public int getPatronId(){
        return this.patronId;
    }

    public void setPatronId(int patronId){
         this.patronId = patronId;
    }

    public int getPatronPriority(){
        return this.patronId;
    }

    public void setPatronPriority(int patronPriority){
        this.patronPriority = patronPriority;
    }

    public int getTimeOfReservation(){
        return this.patronId;
    }

    public void setTimeOfReservation(LocalDateTime timeOfReservation){
        this.timeOfReservation = timeOfReservation;
    }

    public Patron(int patronId, int patronPriority, LocalDateTime timeOfReservation) {
        this.patronId = patronId;
        this.patronPriority = patronPriority;
        this.timeOfReservation = timeOfReservation;
    }

    // Method to compare two patrons based on priority and reservation time
    public boolean compare(Patron other) {
        if (this.patronPriority < other.patronPriority) {
            return true;
        } else if (this.patronPriority == other.patronPriority) {
            // return this.timeOfReservation < other.timeOfReservation;
            int comparisonResult = this.timeOfReservation.compareTo(other.timeOfReservation);
            if (comparisonResult < 0) {
                return true;
            }
            return false;
        } 
        else {
            return false;
        }
    }

    // Method to represent the object as a string
    public String toString() {
        return "PatronID: " + this.patronId + ", Priority: " + this.patronPriority+ ", Time of Reservation: " + this.timeOfReservation;
    }

    // Method to check if the current patron has a higher priority than another patron
    public boolean isGreaterThan(Patron other) {
        return this.patronPriority > other.patronPriority;
    }

}