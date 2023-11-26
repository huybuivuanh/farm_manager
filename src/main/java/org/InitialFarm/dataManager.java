package org.InitialFarm;

import org.bson.BsonArray;
import org.entities.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.entities.DatabaseInterface;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.InitialFarm.DataFetch.*;

/**
 *   data Manager is supposed to:
 *   1) grab instance of object from database for other classes to work with
 *      a) use the data fetch to get data from the database
 *      b) create an object of a  class matching the type of data that got pulled
 *      c) return that object for other classes to work on/with
 *   2) sync changes in fields of object that got changed in database
 */
public class dataManager {

    /**
     * Used to fetch object by:
     * 1) grabbing object details => parse through;
     * 2) build it using constructor;
     * 3) return it;
     */


    public  <T extends DatabaseInterface<T>> T updateClass(T test){

        if ( test instanceof Employee) {

            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "employee_list");
        }
        else if (test instanceof Field) {

            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "field_list");
            }
        else if (test instanceof Task) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "task_list");
            }
        else if (test instanceof Chemical) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "chemical_list");
            }
        else if (test instanceof ChemicalRecord) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "chemical_record_list");
            }
        else if (test instanceof TaskRecord) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "task_record_list");
            }
        else if (test instanceof Crop) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "crop_list");
            }
        else if (test instanceof Year) {
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "year_list");
            }
        else if (test instanceof GrainBin){
            replaceDoc(test.getDbId(),test.classToDoc(), "FarmData", "grain_bin_list");
        }
        else {
                System.out.println("not of type Employee, owner, field, or task");
            }
        return null;
    }


    public <T extends DatabaseInterface<T>>  void removeClass (T test){
        if ( test instanceof Employee) {

            remove(test.getDbId(), "FarmData", "employee_list");
        }
        else if (test instanceof Field) {

            remove(test.getDbId(), "FarmData", "field_list");
        }
        else if (test instanceof Task) {
            remove(test.getDbId(), "FarmData", "task_list");
        }
        else if (test instanceof Chemical) {
            remove(test.getDbId(), "FarmData", "chemical_list");
        }
        else if (test instanceof ChemicalRecord) {
            remove(test.getDbId(), "FarmData", "chemical_record_list");
        }
        else if (test instanceof TaskRecord) {
            remove(test.getDbId(), "FarmData", "task_record_list");
        }
        else if (test instanceof Crop) {
            remove(test.getDbId(), "FarmData", "crop_list");
        }
        else if (test instanceof Year) {
            remove(test.getDbId(), "FarmData", "year_list");
        }
        else if (test instanceof GrainBin){
            remove(test.getDbId(), "FarmData", "grain_bin_list");
        }
        else {
            System.out.println("Could not be removed: not of type Employee, owner, field, or task");
        }

    }
    // Initialize a class  Does this have an ID yes or no?
    // No it doesn't. Okay re-initalize this class inside the database. Pull out what the database Makes as a new class
    // construct of same and return back to the developer. They should set their class as this new class.
    // IF it does exist (ID is not null). THen we need update that ID position with whatever information it has.
    //
    public <T extends DatabaseInterface<T>> T saveClass(T test){
        ObjectId newID = null;
        Document newDoc= null;
        // Option below represents type.
        //1 is employee, 2 is owner,3 is field, 4 is task
        String classType = null;

        // if the object isn't already in the database
        if (test.getDbId() == null){
            Document doc=  test.classToDoc();


            if ( test instanceof Employee){
                if (test.getDbId() == null){

                }
                newID= insertDoc(doc, "FarmData", "employee_list");
                newDoc= grabByID("FarmData", "employee_list", newID);
                classType= "Employee";}


            // Was getting an error that this ill always be wrong, that's because owner inherits from employee,
            // so if it is an owner, it will never get here. Left comment. not sure how you want to handle this

//            else if ( test instanceof Owner){
//              newID=   insertDoc(doc, "FarmData", "owner_list");
//              newDoc= grabByID("FarmData", "employee_list", newID);
//              classType= "Owner";}

            else if ( test instanceof Field){
                newID=  insertDoc(doc, "FarmData","field_list");
                newDoc= grabByID("FarmData", "field_list", newID);
                classType = "Field";}

            else if (test instanceof GrainBin){
                newID = insertDoc(doc,"FarmData","grain_bin_list");
                newDoc = grabByID("FarmData","grain_bin_list",newID);
                classType = "GrainBin";
            }

            else if ( test instanceof Task){


                newID=  insertDoc(doc, "FarmData","task_list");
                newDoc= grabByID("FarmData", "task_list", newID);
                classType = "Task";
            }
            else if (test instanceof Chemical){
                newID = insertDoc(doc,"FarmData","chemical_list");
                newDoc = grabByID("FarmData","chemical_list",newID);
                classType = "Chemical";
            }
            else if (test instanceof ChemicalRecord){
                newID = insertDoc(doc,"FarmData","chemical_record_list");
                newDoc = grabByID("FarmData","chemical_record_list",newID);
                classType = "ChemicalRecord";
            }
            else if (test instanceof TaskRecord){
                newID = insertDoc(doc,"FarmData","task_record_list");
                newDoc = grabByID("FarmData","task_record_list",newID);
                classType = "TaskRecord";
            }
            else if (test instanceof Crop){
                newID = insertDoc(doc,"FarmData","crop_list");
                newDoc = grabByID("FarmData","crop_list",newID);
                classType = "Crop";
            }
            else if (test instanceof Year){
                newID = insertDoc(doc,"FarmData","year_list");
                newDoc = grabByID("FarmData","year_list",newID);
                classType = "Year";
            }

            else {
                System.out.println("not of type Employee, owner, field, or task");
            }
        }
        else{

        }
        assert classType != null;
        return fetchObject(classType, newDoc);

    }
    // didnt get to work on this
    public static <T extends DatabaseInterface<T>> Boolean adderMethod(T test){

        if (test instanceof Employee){
            Document toDatabase = test.classToDoc();
        }
        return null;

    }



    // old fetch object
//    public static dummy fetchObject(String classType, String classInfo1, String classInfo2) throws NoSuchFieldException {
//        Document doc=  grab("FarmData", "farm_list", classType, classInfo1);
//        //ObjectId id = new ObjectId(doc.getString("fieldName"), doc.get("_id", Document.class).getString("$oid"));
//        // the id is a special case in mongodb and needs to be put into an ObjectId, it cant be cast to string right away
//        ObjectId id = doc.getObjectId("_id");
//        return new dummy( 31, doc.getString("fieldName"),  id);
//    }

    public <T extends DatabaseInterface<T>> T fetchObjectById(String classType, ObjectId id){

        Object newObj = null;

        if (classType.equals("Field")){
            Document test = grabByID("FarmData","field_list",id);
            Field newfield = fetchObject("Field",test);
            return (T) newfield;
        }
        if (classType.equals("Year")){
            Document newDoc = grabByID("FarmData","year_list",id);
            Year newYear = fetchObject("Year",newDoc);

            return (T) newYear;
        }

        if (classType.equals("GrainBin")){
            Document newDoc = grabByID("FarmData","grain_bin_list",id);
            GrainBin newGrainBin = fetchObject("GrainBin",newDoc);
            return (T) newGrainBin;
        }
        if (classType.equals("Employee")){
            Document newDoc = grabByID("FarmData","employee_list",id);
            Employee newEmployee =  new Employee(newDoc.getObjectId("_id"),newDoc.getString("employeeId"), newDoc.getString("email"),
                    newDoc.getString("password"), newDoc.getString("firstname"),
                    newDoc.getString("lastname"), newDoc.getDate("dob").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), newDoc.getBoolean("isOwner"));
            return (T) newEmployee;
        }
        if (classType.equals("Task")){
            Document newDoc = grabByID("FarmData", "task_list", id);
            Task newTask= new Task(newDoc.getObjectId("_id"), newDoc.getString("taskID"),
                    newDoc.getString("task_name"), newDoc.getString("task_description"),
                    newDoc.getDate("task_dueDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            return (T)newTask;
        }
        if (classType.equals("TaskRecord")){
            Document newDoc = grabByID("FarmData","task_record_list",id);
            TaskRecord newTaskRecord = new TaskRecord(newDoc.getObjectId("_id"),fetchObjectById("Task",newDoc.getObjectId("task")),newDoc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            return (T)newTaskRecord;
        }
        if (classType.equals("ChemicalRecord")){
            Document newDoc = grabByID("FarmData","chemical_record_list",id);
            ChemicalRecord newChemRecord = new ChemicalRecord(newDoc.getObjectId("_id"),fetchObjectById("Chemical",newDoc.getObjectId("chemical")),newDoc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            return (T)newChemRecord;
        }
        if (classType.equals("Chemical")){
            Document newDoc = grabByID("FarmData","chemical_list",id);
            Chemical newChemical = new Chemical(newDoc.getObjectId("_id"),newDoc.getString("chemicalName"),newDoc.getList("chemicalGroup",String.class));
            return (T)newChemical;
        }
        if (classType.equals("Crop")){
            Document newDoc = grabByID("FarmData","crop_list",id);
            Crop newCrop = new Crop(newDoc.getObjectId("_id"),newDoc.getString("cropType"),newDoc.getString("cropVariety"),newDoc.getDouble("bushelWeight"));
            return (T)newCrop;
        }



//        newDoc.append("taskID",this.getID());
//        newDoc.append("task_name",this.getTaskName());
//        newDoc.append("task_description",this.getDescription());
//        newDoc.append("task_dueDate",this.getDueDate());
//        newDoc.append("task_date",this.getDate());
//        newDoc.append("stafflist",staffList);
//        return newDoc;
//    }



        return null;
    }


    /**
     * Given the type of class, the object document, and its id, return the object itself.
     */
    public  <T extends DatabaseInterface<T>> T fetchObject(String classType, Document objectDoc){

        Object newObj = null;
        System.out.println("inside fetch test" + objectDoc.getObjectId("_id"));

        if (classType.equals("Employee"))
        {
            Employee newEmployee =  new Employee(objectDoc.getObjectId("_id"),objectDoc.getString("employeeId"), objectDoc.getString("email"),
                    objectDoc.getString("password"), objectDoc.getString("firstname"),
                    objectDoc.getString("lastname"), objectDoc.getDate("dob").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), objectDoc.getBoolean("isOwner"));

            BsonArray test = objectDoc.toBsonDocument().getArray("tasklist");
            for (int i = 0; i < test.size();i++){
                ObjectId output = test.get(0).asObjectId().getValue();
                Task newTask = (Task) fetchObjectById("Task",output);
                System.out.println(newTask);
                newEmployee.addTask(newTask);
            }
            newObj = newEmployee;

        }
        else if (classType.equals("GrainBin")){
            GrainBin newGrainBin = new GrainBin(objectDoc.getObjectId("_id"),objectDoc.getString("binName"),objectDoc.getString("binLocation"),objectDoc.getInteger("binSize"),objectDoc.getBoolean("hopper"),objectDoc.getBoolean("fan"));
            if (objectDoc.getObjectId("currentCrop") != null) {
                newGrainBin.setCurrentCrop(fetchObjectById("Crop", objectDoc.getObjectId("currentCrop")));
            }
            else{
                newGrainBin.setCurrentCrop(null);
            }
            if (objectDoc.getObjectId("lastCrop") != null) {
                newGrainBin.setLastCrop(fetchObjectById("Crop", objectDoc.getObjectId("lastCrop")));
            }
            else{
                newGrainBin.setLastCrop(null);
            }
            if (objectDoc.getString("currentCropType") != null){
                newGrainBin.setCurrentCropType(objectDoc.getString("currentCropType"));
            }
            else{
                newGrainBin.setCurrentCropType("");
            }

            if (objectDoc.getDouble("cropBushels") != null) {
                newGrainBin.setCropBushels(objectDoc.getDouble("cropBushels"));
            }
            else{
                newGrainBin.setCropBushels(0.0);
            }
            if (objectDoc.getDouble("cropLbs") != null) {
                newGrainBin.setCropLbs(objectDoc.getDouble("cropLbs"));
            }
            else{
                newGrainBin.setCropLbs(0.0);
            }
            if (objectDoc.getBoolean("tough") != null) {
                newGrainBin.setTough(objectDoc.getBoolean("tough"));
            }
            if (objectDoc.getBoolean("clean") != null) {
                newGrainBin.setClean(objectDoc.getBoolean("clean"));
            }
            newObj = newGrainBin;
        }
        else if (classType.equals("Year")){
            Year newYear = new Year(objectDoc.getObjectId("_id"),objectDoc.getInteger("year"),objectDoc.getDate("new_year").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            BsonArray test = objectDoc.toBsonDocument().getArray("chemical_records");
            for (int i = 0; i < test.size();i++){
                ObjectId output = test.get(0).asObjectId().getValue();
                ChemicalRecord newChemRecord =  fetchObjectById("ChemicalRecord",output);
                System.out.println(newChemRecord);
                newYear.addChemicalRecord(newChemRecord);
            }
            BsonArray test2 = objectDoc.toBsonDocument().getArray("task_records");
            for (int i = 0; i < test2.size();i++){
                ObjectId outputTask = test2.get(0).asObjectId().getValue();
                TaskRecord newTaskRecord =  fetchObjectById("TaskRecord",outputTask);
                System.out.println(newTaskRecord);
                newYear.addTaskRecord(newTaskRecord);
            }
            if (objectDoc.getObjectId("crop") != null) {
                newYear.setCrop(fetchObjectById("Crop", objectDoc.getObjectId("crop")));
            }
            else{
                newYear.setCrop(null);
            }
            if (objectDoc.getDate("seedDate") != null) {
                newYear.setSeeding_date(objectDoc.getDate("seedDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            else{
                newYear.setSeeding_date(null);
            }

            if (objectDoc.getDouble("seedRate") != null) {
                newYear.setSeeding_rate(objectDoc.getDouble("seedRate"));
            }
           else{
                newYear.setSeeding_rate(0);
            }
            if (objectDoc.getDouble("fertilizerRate") != null) {
                newYear.setFertilizer_rate(objectDoc.getDouble("fertilizerRate"));
            }
            else{
                newYear.setFertilizer_rate(0);
            }
            if (objectDoc.getObjectId("ChemicalSprayed")!= null){
                newYear.setChemical_sprayed(fetchObjectById("Chemical",objectDoc.getObjectId("chemicalSprayed")));
            }
            else{
                newYear.setChemical_sprayed(null);
            }
            if (objectDoc.getDate("sprayDate") != null) {
                newYear.setSpraying_date(objectDoc.getDate("sprayDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            else{
                newYear.setSpraying_date(null);
            }
            if (objectDoc.getDate("harvestDate") != null){
                newYear.setHarvest_date(objectDoc.getDate("harvestDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            else{
                newYear.setHarvest_date(null);
            }
            if (objectDoc.getDate("endOfYear") != null){
                newYear.setEnd_of_year(objectDoc.getDate("endOfYear").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            else{
                newYear.setEnd_of_year(null);
            }

            //TODO ADD MORE OF THE YEAR STUFF HERE
            newObj = newYear;
        }

        else if (classType.equals("Owner"))
        {
            //TODO get rid of this garbage
          //  newObj =  new Owner(objectDoc.getString("_id"), objectDoc.getString("user_email"),
          //          objectDoc.getString("user_password"), objectDoc.getString("first_name"),
          //  objectDoc.getString("last_name"), objectDoc.getDate("dob").toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
        }

        /**
         * newDoc.append("fieldId", field_id);
         *         newDoc.append("fieldName", field_name);
         *         newDoc.append("fieldSize", field_size);
         *         newDoc.append("fieldLocation", field_location);
         *         //not sure how to include years below
         *         newDoc.append("field_years", this.years);
         */
        else if (classType.equals("Field"))
        {
            Field newField = new Field(objectDoc.getObjectId("_id"),objectDoc.getString("fieldId"),objectDoc.getString("fieldName"),objectDoc.getDouble("fieldSize"),objectDoc.getString("fieldLocation"));
            BsonArray test = objectDoc.toBsonDocument().getArray("field_years");
            if (objectDoc.getObjectId("currentYear") != null) {
                newField.setCurrentYear(fetchObjectById("Year", objectDoc.getObjectId("currentYear")));
            }
            else{
                newField.setCurrentYear(null);
            }
            for (int i = 0; i < test.size();i++){
                ObjectId output = test.get(0).asObjectId().getValue();
                Year newYear = fetchObjectById("Year",output);
                System.out.println(newYear);
                newField.addYear(newYear);
            }
            newObj = newField;
        }
        else if (classType.equals("Chemical"))
        {
            Chemical newchem = new Chemical(objectDoc.getObjectId("_id"),objectDoc.getString("chemicalName"),objectDoc.getList("chemicalGroup",String.class));
            newObj = newchem;
        }
        else if (classType.equals("ChemicalRecord"))
        {
            ChemicalRecord newChemRecord = new ChemicalRecord(objectDoc.getObjectId("_id"),fetchObjectById("Chemical",objectDoc.getObjectId("chemical")),objectDoc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            newObj = newChemRecord;
        }
        else if (classType.equals("TaskRecord")){
            TaskRecord newTaskRecord = new TaskRecord(objectDoc.getObjectId("_id"),fetchObjectById("Task",objectDoc.getObjectId("task")),objectDoc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            newObj = newTaskRecord;
        }
        else if (classType.equals("Crop")){
            Crop newCrop = new Crop(objectDoc.getObjectId("_id"),objectDoc.getString("cropType"),objectDoc.getString("cropVariety"),objectDoc.getDouble("bushelWeight"));
            newObj = newCrop;
        }

        else if (classType.equals("Task")){

            // below is another property that we saved into db, but isn't part of constructor ?? idk
            // "task_date"

           Task newTask =   new Task(objectDoc.getObjectId("_id"),objectDoc.getString("taskID"), objectDoc.getString("task_name"),
                    objectDoc.getString("task_description"), objectDoc.getDate("task_dueDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            BsonArray test = objectDoc.toBsonDocument().getArray("stafflist");
            for (int i = 0; i < test.size();i++){
                ObjectId output = test.get(0).asObjectId().getValue();
                Employee newEmployee = (Employee) fetchObjectById("Employee",output);
                System.out.println(newEmployee);
                newTask.addStaff(newEmployee);
            }
            newObj = newTask;

            System.out.println("array of staff: " + test);
        }
        return (T)newObj;
    }


    /**
    * Translates an object into a JSON Document representation of itself.
    */
    public static Document translateToDoc ( dummy dum)
    {
        Document newDoc = new Document();
        int dumsInt = dum.dummyHeight;
        String dumsName = dum.dummyName;
        ObjectId dumsId= dum.dummyId;

        newDoc.append("_id", dumsId);
        newDoc.append("fieldName", dumsName);
        newDoc.append("acres", dumsInt);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

        return newDoc;
    }

    // method2:
    public static void sync (dummy dum){
        Document synced = translateToDoc(dum);
        ObjectId id = synced.getObjectId("_id");
        System.out.println("this is whats in synced: " + id);
        System.out.println( existsID(synced.getObjectId("_id"),"FarmData", "farm_list"));
        if (existsID(synced.getObjectId("_id"),"FarmData", "farm_list"))
        {
            //Document old = grabByID("FarmData", "farmer_list",  synced.getObjectId("_id"));
            modifyID(synced, synced.getObjectId("_id") ,"FarmData", "farm_list");
        }
        else {
            insertDoc(synced, "FarmData", "farm_list");
        }

    }


    public static void main(String[] args) throws NoSuchFieldException {


        //System.out.println(grab("FarmData","farm_list","fieldName","FieldGerald"));

        //TODO: test adding employees to a task's list of employees
        // todo note: for this to work, the task to be added has to be the fetched version after being saved so that its DBID isnt null !!!
        //create employee and data manager
        Employee tester1 = new Employee(null,"1133", "ziy271", "strong", "kim", "zrein", LocalDate.of(2002, Calendar.FEBRUARY,2), false);
        dataManager manager1 = new dataManager();

        // save employee to the database
        Employee outputEmployee1 = (Employee) manager1.saveClass(tester1);

        // find the employee data from the database
        Document testDoc = grab("FarmData", "employee_list", "firstname", "kim");
        System.out.println(testDoc);

        //translate the data into an employee object
        System.out.println(String.valueOf(manager1.fetchObject("Employee",testDoc )));

        // add tasks to employee
        Task task1a = new Task(null,"1", "task 1", "task 1 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        task1a.addStaff(outputEmployee1);
        Task databaseTask1 = manager1.saveClass(task1a);
        Task task1b = new Task(null,"2", "task 2", "task 2 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));

        tester1.addTask(task1a);
        tester1.addTask(task1b);

        //TODO: test adding tasks to an employee's list of tasks
        // todo note: for this to work, the task to be added has to be the fetched version after being saved so that its DBID isnt null !!!
        //create employee and data manager
        dataManager manager2 = new dataManager();

        Employee tester2 = new Employee(null,"1133", "ziy271", "strong", "kim", "zrein", LocalDate.of(2002, Calendar.FEBRUARY,2), false);
        //Employee outputEmployee = (Employee) manager.saveClass(tester);

        Task task2a = new Task(null,"1", "task 1", "task 1 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task outputTask2a = manager2.saveClass(task2a);

        Task task2b = new Task(null,"1", "task 1", "task 1 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task outputTask2b = manager2.saveClass(task2a);


        tester2.addTask(outputTask2a);
        tester2.addTask(outputTask2b);
        Employee dataBaseEmployee2= (Employee) manager2.saveClass(tester2);

        // TODO: NOTE THAT FOR ALL DB OPERATIONS,
        //  IF YOU CREATE OBJECT(A) THAT YOU INTEND TO ADD TO ANOTHER OBJECT(B) AND SAVE BOTH IN DB
        //  OBJECT(A) NEEDS TO BE SAVED IN DB BEFORE YOU ATTEMPT TO SAVE OBJECT(B) INTO DB
        //  THIS IS BECAUSE SAVING OBJECT(B) IN DB REQUIRES WHATEVER IS INSIDE IT TO ALREADY EXIST IN THE DB

        // Todo: create a data manager
        //  Status: (works)
        dataManager manager3 = new dataManager();

        // Todo: create a chemical
        //  Status: (works)
        ArrayList<String> test = new ArrayList<>();
        test.add("clorox");
        test.add("bath salts");
        Chemical testChem = new Chemical(null,"weird chemical", test);

        // Todo: save the chemical to the database
        //  Status: (works)
        Chemical outputChem = manager3.saveClass(testChem);

        // Todo: create a new crop and save it to database
        //  Status: (works)
        Crop newCrop1 = new Crop(null,"corn","beautiful corn", 50.0);
        Crop outputCrop1 = manager3.saveClass(newCrop1);

        Crop newCrop2 = new Crop(null,"wheat","beautiful wheat", 34.0);
        Crop outputCrop2 = manager3.saveClass(newCrop2);

        // Todo: create a new grain bin, add crops to it, then save the grain bin to the DB
        //  status: mostly works, 2nd crop isnt saved in bin bcz bin doesnt have the logic for that. ( not sure if db does)
        GrainBin bin = new GrainBin(null,"I am bin 1","Saskatoon", 150, true, true);
        bin.addCrop(outputCrop1, 50, true, true, true);
        //  adding a 2nd crop different to the first has not been implemented by shawn yet so right now it gets ignored.
        bin.addCrop(outputCrop2, 50, true, true, true);
        manager3.saveClass(bin);


        // Todo: create a chemical record and save it to the database
        //  Status: (works)
        ChemicalRecord newChemRecord = new ChemicalRecord(null,outputChem,LocalDate.of(2020,Month.JANUARY,1));
        ChemicalRecord outputChemRecord = manager3.saveClass(newChemRecord);

        // Todo: create a new task and save it to the database
        //  Status: (works)
        Task newTask = new Task(null,"1", "task 1", "task 1 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task outputTask = manager3.saveClass(newTask);

        // Todo: create a new task record and save it to the database
        //  Status: (works)
        TaskRecord newTaskRecord = new TaskRecord(null,outputTask,LocalDate.of(2020,Month.JANUARY,1));
        TaskRecord outputTaskRecord = manager3.saveClass(newTaskRecord);

        // Todo: create a year
        //  Status: (works)
        Year newYear1 = new Year(null,2020,LocalDate.of(2020,Month.MAY,15));
        Year newYear2 = new Year(null,2024,LocalDate.of(2024,Month.JANUARY,1));

        // Todo: add chemical and task records to year (both have to be already in db)
        //  Status: (works)
        newYear1.addChemicalRecord(outputChemRecord);
        newYear1.addTaskRecord(outputTaskRecord);

        // Todo: save year to the database
        //  Status: (works, a lot of values are null though, probably didn't have tie to instantiate them)
        Year outputYear1 = manager3.saveClass(newYear1);
        Year outputYear2 = manager3.saveClass(newYear2);


        // Todo: updating the value a year that is already in Database
        //  status: works ( need to test if updateClass works for other classes too)

        // setting dates
        outputYear1.setSeeding_date(LocalDate.of(2021, 2, 26));
        outputYear1.setSpraying_date(LocalDate.of(2021, 4, 28));  // this still uses localdate time for somereason
        outputYear1.setHarvest_date(LocalDate.of(2021, 7, 13));
        outputYear1.setEnd_of_year(LocalDate.of(2021, 11, 29));

        // setting other year details
        outputYear1.setCrop(outputCrop1);
        outputYear1.setSeeding_rate(15);
        outputYear1.setFertilizer_rate(12);
        outputYear1.setChemical_sprayed(outputChem);
        outputYear2.setCrop(outputCrop2);

        manager3.updateClass(outputYear1);
        manager3.updateClass(outputYear2);

        // Todo: create a new field, add year, save current year, and save to database
        //  status: works
        Field newField = new Field(null,"field5","FieldGerald", 51, "location");
        newField.addYear(outputYear1);
        newField.setCurrentYear(outputYear2);

        Field outputField2 = manager3.saveClass(newField);



//
//        Field getFieldTest = manager3.fetchObjectById("Field",outputField2.getDbId());
//
//        System.out.println("output chem:" + outputChem);
//

        // check if tasks are reflected in document.

        // old version of fetch object run only once
         //  dummy test =  fetchObject("fieldName", "FieldGerald", "randominfo");
          //System.out.println(test);
          //System.out.println(translateToDoc(test));
         // sync(test);


//        ObjectId tester = new ObjectId("652c789b68062b52deac5427");
//
//
//        remove(tester,"FarmData","farm_list");



//        Document newDoc = new Document();
//        newDoc.append("fieldName", "kamal's Field");
//        newDoc.append("acres",51);
//        Date added = new Date();
//        newDoc.append("Date Added:",added.getTime());
//
//        insertDoc(newDoc,"FarmData","farm_list");
//
//        addCollection("FarmData","farm_bins");
//
//        System.out.println(getCollectionList("FarmData"));
//        System.out.println(getDatabaseList());
    }
}
