package entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Calendar;
import java.util.Date;

public class Employee extends User implements DatabaseInterface<Employee>{
    public Employee(String id,String user_email, String user_password, String first_name, String last_name, Date dob){
        super(id, user_email, user_password, first_name, last_name, dob);
    }

    @Override
    public String toString() {
        String userType = this.getClass().getSimpleName();
        return "User type: " + userType + "\n" + super.toString();
    }


    /**
     * Translates an object into a JSON Document representation of itself.
     * @param emp : an employee to be translated into a document
     * @return : a document representation of the employee object passed
     */
    @Override
    public Document classToDoc(Employee emp) {
        Document newDoc = new Document();

        //  ObjectId employee_Id= emp.employeeId; => this is for the database

        String emp_id = emp.getID();
        String emp_first_name= emp.getFirstName();
        String emp_last_name= emp.getLastName() ;
        Date emp_dob = emp.getDOB();
        String emp_user_email= emp.getEmail();
        String emp_user_password= emp.getPassword();

        // might need to add the objectID here still
        newDoc.append("_id", emp_id);
        newDoc.append("first_name", emp_first_name);
        newDoc.append("last_name", emp_last_name);
        newDoc.append("dob", emp_dob);
        newDoc.append("user_email", emp_user_email);
        newDoc.append("user_password", emp_user_password);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        return newDoc;
    }

    /**
     * @return
     */
    @Override
    public Document docToClass() {
        return null;
    }

    /**
     *
     */
    @Override
    public void save() {

    }

    /**
     *
     */
    @Override
    public void sync() {

    }

    /**
     * @return
     */
    @Override
    public ObjectId getDbId() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isDatabase() {
        return false;
    }




    /**
     * testing
     * @param args args
     */
    public static void main(String[] args){
        Date dob = new Date(2002 - 1900, Calendar.FEBRUARY, 2, 2, 2, 2);
        Employee staff = new Employee("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        Task task1 = new Task("1", "task 1", "task 1 description", new Date());
        Task task2 = new Task("2", "task 2", "task 2 description", new Date());
        Task task3 = new Task("3", "task 3", "task 3 description", new Date());
        Date specificDate = new Date(2111 - 1900, Calendar.JANUARY, 1, 1, 1, 1);

        // testing getters method with toString();
        System.out.println(staff + "\n");


        // testing setters method
        staff.setID("new");
        staff.setEmail("new");
        staff.setPassword("new");
        staff.setFirstName("new");
        staff.setLastName("new");
        staff.setDOB(specificDate);
        staff.addTask(task1);
        staff.addTask(task2);
        staff.addTask(task3);
        staff.removeTask(task1.getID());
//        staff.removeAllTasks();
        System.out.println(staff);
    }


}
