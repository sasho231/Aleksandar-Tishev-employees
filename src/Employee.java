import java.util.Date;

public class Employee {
    private String id;
    private Date start;
    private Date end;

    public Employee(String id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Long getTotalTime(){
        return this.end.getTime() - this.start.getTime();
    }

}
