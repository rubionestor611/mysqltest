import java.sql.Date;

public class Employee {
    public int emp_id;
    public String first_name;
    public String last_name;
    public Date birth_day;
    public String sex;
    public int salary;
    public int super_id;
    public int branch_id;

    public Employee(int emp_id, String first_name, String last_name,
                    Date birth_day, String sex,
                    int salary, int super_id, int branch_id){
        this.emp_id = emp_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_day = birth_day;
        this.sex = sex;
        this.salary = salary;
        this.super_id = super_id;
        this.branch_id = branch_id;
    }
    @Override
    public String toString(){
        return this.emp_id + ": " + this.first_name + ": " + this.last_name + ": " + birth_day + ": " +sex + ": " +salary + ": " +super_id + ": " +branch_id;
    }
}
