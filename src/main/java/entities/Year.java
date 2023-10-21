package entities;
/*
The Class Year divides each Field by its individual year
A field will have a year attribute that contains everything done on it that year
A field's year gets updated yearly(funny right)
A field has a list of multiples years
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;

public class Year{

    /**
     * The current year e.g. 2013
     */
    private final int year;

    /**
     * The current crop on the field
     */
    private Crop crop;

    /**
     * The date the crop was planted
     */
    private LocalDate seeding_date;

    /**
     * The seeding rate of the crop in lbs/acre
     */
    private double seeding_rate;

    /**
     * The application rate of the fertilizer in lbs/acre
     */
    private double fertilizer_rate;

    /**
     * The chemical that was most recently sprayed on the field
     */
    private Chemical chemical_sprayed;

    /**
     * The date the field was last sprayed
     */
    private LocalDate spraying_date;

    /**
     * The class to hold a chemical and date record
     */
    private static class ChemicalRecord{
        Chemical chemical;
        LocalDate date;

        private ChemicalRecord(Chemical _chemical, LocalDate _date){
            chemical = _chemical;
            date = _date;
        }
        private Chemical getChemical(){ return this.chemical; }
        private LocalDate getDate(){ return this.date; }
    }

    /**
     * The list of chemicals that have been sprayed on the
     * field over the year and their dates
     */
    private final LinkedList<ChemicalRecord> chemical_records;

    /**
     * The class to hold a chemical and date record
     */
    private static class TaskRecord{
        Task task;
        LocalDate completedDate;

        private TaskRecord(Task _task, LocalDate completedDate){
            task = _task;
            this.completedDate = completedDate;
        }
        private Task getTask(){ return this.task; }
        private LocalDate getCompletedDate(){ return this.completedDate; }
    }


    /**
     * The list of tasks that have been done over the year and
     * their dates
     */
    private final LinkedList<TaskRecord> task_records;

    /**
     * The date the field was harvested
     */
    private LocalDate harvest_date;

    /**
     * The date the field year ended
     */
    private LocalDate end_of_year;

    /**
     * The date the field year begun
     */
    private final LocalDate new_year;


    /**
     * Initializes a year
     * @param _year the current year
     * @param new_year_date  the date the new year begins
     */
    public Year( int _year, LocalDate new_year_date){
        year = _year;
        this.new_year = new_year_date;
        this.chemical_records = new LinkedList<>();
        this.task_records = new LinkedList<>();
    }

    /**
     * Return the current year
     * @return the current year as an int
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Return the date the year started
     * @return the Date the year started
     */
    public LocalDate getNewYearDate() {
        return this.new_year;
    }

    /**
     * The crop being grown that year
     * @return the crop grown that year
     */
    public Crop getCrop() {
        return this.crop;
    }

    /**
     * Updates the crop to be planted on the field that year
     * @param crop the crop to be planted that year
     */
    public void setCrop(Crop crop) { this.crop = crop; }

    /**
     * Return the date the crops got seeded/planted
     * @return the Date the crop got planted
     */
    public LocalDate getSeeding_date(){
        return this.seeding_date;
    }

    /**
     * Sets the seeding date
     * @param newSeedingDate the date the crop was/will be seeded
     */
    public void setSeeding_date( LocalDate newSeedingDate){
        this.seeding_date = newSeedingDate;
    }

    /**
     * Returns the rate of seed application
     * @return the seed application rate
     */
    public double getSeeding_rate(){
        return this.seeding_rate;
    }

    /**
     * Sets the seeding rate
     * @param newSeedingRate the rate at which the crop was seeded
     */
    public void setSeeding_rate(double newSeedingRate){
        this.seeding_rate = newSeedingRate;
    }

    /**
     * Returns the rate of fertilizer application
     * @return the fertilizer application rate
     */
    public double getFertilizer_rate(){
        return this.fertilizer_rate;
    }

    /**
     * Sets the fertilizer rate
     * @param newFertilizerRate the rate at which the fertilizer was applied
     */
    public void setFertilizer_rate(double newFertilizerRate){
        this.fertilizer_rate = newFertilizerRate;
    }

    /**
     * Returns the date of the most recent chemical application
     * @return the date of the most recent chemical application
     */
    public LocalDate getSprayingDate(){
        return this.spraying_date;
    }

    /**
     * Returns the Chemical that was most recently sprayed
     * @return a chemical
     */
    public Chemical getChemical_sprayed(){
        return this.chemical_sprayed;
    }

    /**
     * Performs a spraying task by updating the chemical sprayed and updating
     * the list of chemical sprayed
     * @param newChemical The chemical to be added to the field
     * @param newSprayingDate the date the chemical was/will be added to the field
     */
    public void sprayChemical(Chemical newChemical, LocalDate newSprayingDate){
        this.chemical_sprayed = newChemical;
        this.spraying_date = newSprayingDate;
        ChemicalRecord newRecord = new ChemicalRecord(newChemical, newSprayingDate);
        this.chemical_records.add(newRecord);
    }

    /**
     * Returns the chemical history of the field
     * @return the chemical history complete with dates
     */
    public LinkedList<ChemicalRecord> getChemical_records() { return chemical_records; }

    /**
     * Add a task to the list of field tasks that have been done
     * @param newTask The task to be added to the list after it's done
     * @param newTaskDate the date the new task was completed
     */
    public void doTask(Task newTask, LocalDate newTaskDate){
        TaskRecord newRecord = new TaskRecord(newTask, newTaskDate);
        this.task_records.add(newRecord);
    }

    /**
     * Returns the Task history of the field
     * @return the task history of the field complete with dates
     */
    public LinkedList<TaskRecord> getTaskRecords() {
        return task_records;
    }

    /**
     * Sets the harvest date
     * @param harvestDate the date of the harvest
     */
    public void harvest( LocalDate harvestDate){
        this.harvest_date = harvestDate;
    }

    /**
     * Obtains the harvest date
     * @return the date of the harvest
     */
    public LocalDate getHarvestDate() { return harvest_date; }

    /**
     * Obtains the end of year date
     * @return the date the year ends
     */
    public LocalDate getEndOfYear(){ return this.end_of_year; }

    /**
     * Sets the end of the year date
     * @param endOfYearDate the date the year ends
     */
    public void endOfYear( LocalDate endOfYearDate){ this.end_of_year = endOfYearDate; }

    /**
     * Displays all the information on a field for a given year
     * @return the field information for the year
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(year +
                ",\n new year starting date = " + new_year +
                ",\n crop = " + crop +
                ",\n seeding_date = " + seeding_date +
                ",\n seeding_rate = " + seeding_rate + "lbs/acre" +
                ",\n fertilizer_rate = " + fertilizer_rate + "lbs/acre" +
                ",\n spraying_date = " + spraying_date +
                ",\n harvest_date = " + harvest_date +
                ",\n end_of_year = " + end_of_year +
                ",\n chemical_records\n  ");

        for (ChemicalRecord chemical_record : chemical_records) {
            result.append(chemical_record.getChemical()).append("  ").append(chemical_record.getDate()).append("\n");
        }

        result.append("\n task_records\n  ");

        for (TaskRecord task_record : task_records) {
            result.append(task_record.getTask().getTaskName()).append("  ").append(task_record.getCompletedDate()).append("\n");
        }

        return result.toString();
    }

    public static void main(String[] args){

        /* For testing the Field class */

        String reason = "Constructor + getYear()";
        int year = 2013;
        LocalDate new_date = LocalDate.of(2013, Calendar.AUGUST, 23);

        // test all methods with this instance
        Year Y1 = new Year(year, new_date);
        int result = Y1.getYear();
        double result2, expected2;
        int expected = 2013;

        if (result != expected)
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing getYear()";
        result = Y1.getYear();
        if (result != expected)
        {
            System.out.println("Error: Expected: " + expected + " Obtained: " + result
                    + " (" + reason + ")");
        }

        reason = "Testing getNewYearDate()";
        LocalDate dResult = Y1.getNewYearDate();
        if (dResult != new_date)
        {
            System.out.println("Error: Expected: " + new_date + " Obtained: " + dResult
                    + " (" + reason + ")");
        }


        reason = "Testing setCrop() and getCrop()";
        Crop cResult;
        Crop cCrop = null;
        Y1.setCrop(cCrop);
        cResult = Y1.getCrop();
        if (cResult != cCrop)
        {
            System.out.println("Error: Expected: " + cCrop + " Obtained: " + cResult
                    + " (" + reason + ")");
        }

        reason = "Testing setSeedingDate() and getSeedingDate()";
        new_date = LocalDate.of(2013, Calendar.OCTOBER, 5);
        Y1.setSeeding_date(new_date);
        dResult = Y1.getSeeding_date();
        if (dResult != new_date)
        {
            System.out.println("Error: Expected: " + new_date + " Obtained: " + dResult
                    + " (" + reason + ")");
        }

        reason = "Testing setSeedingRate() and getSeedingRate()";
        Y1.setSeeding_rate(170.0);
        expected2 = Y1.getSeeding_rate();
        result2 = 170.0;
        if (result2 != expected2)
        {
            System.out.println("Error: Expected: " + expected2 + " Obtained: " + result2
                    + " (" + reason + ")");
        }

        reason = "Testing setFertilizerRate() and getFertilizerRate()";
        Y1.setFertilizer_rate(55.0);
        expected2 = Y1.getFertilizer_rate();
        result2 = 55.0;
        if (result2 != expected2)
        {
            System.out.println("Error: Expected: " + expected2 + " Obtained: " + result2
                    + " (" + reason + ")");
        }

        reason = "Testing Chemicals list after constructor";
        int sizeResult = Y1.getChemical_records().size();
        if (sizeResult != 0)
        {
            System.out.println("Error: Expected: 0, Obtained: " + sizeResult
                    + " (" + reason + ")");
        }

        reason = "Testing tasks list after constructor";
        sizeResult = Y1.getTaskRecords().size();
        if (sizeResult != 0)
        {
            System.out.println("Error: Expected: 0, Obtained: " + sizeResult
                    + " (" + reason + ")");
        }

        reason = " Testing sprayChemical()  + getChemical()";
        Chemical Chem = null;
        new_date = LocalDate.of(2013, Calendar.OCTOBER, 23);

        Y1.sprayChemical(Chem, new_date);
        Chemical chemResult = Y1.getChemical_sprayed();
        if (chemResult != Chem)
        {
            System.out.println("Error: Expected: " + Chem + " Obtained: " + chemResult
                    + " (" + reason + ")");
        }

        reason = "Testing getChemical()";
        chemResult= Y1.getChemical_sprayed();
        if (chemResult != Chem)
        {
            System.out.println("Error: Expected: " + Chem + " Obtained: " + chemResult
                    + " (" + reason + ")");
        }

        reason = "Testing getSprayingDate()";
        dResult = Y1.getSprayingDate();
        if (dResult != new_date)
        {
            System.out.println("Error: Expected: " + new_date + " Obtained: " + dResult
                    + " (" + reason + ")");
        }

        reason = "Testing Chemicals list after sprayChemical";
        sizeResult = Y1.getChemical_records().size();
        if (sizeResult != 1)
        {
            System.out.println("Error: Expected: 1, Obtained: " + sizeResult
                    + " (" + reason + ")");
        }

        reason = " Testing task list after doTask()";
        Task Task1 = new Task("T123", "Harrowing", "Blah Blah Blah", LocalDateTime.of(2023, Calendar.JULY, 23, 13, 34, 43) );
        new_date = LocalDate.of(2013, Calendar.OCTOBER, 23);
        Y1.doTask(Task1, new_date);

        sizeResult = Y1.getTaskRecords().size();
        if (sizeResult != 1)
        {
            System.out.println("Error: Expected: 1, Obtained: " + sizeResult
                    + " (" + reason + ")");
        }


        reason = "Testing harvest() with endOfHarvestDate()";
        Y1.harvest(new_date);
        dResult = Y1.getHarvestDate();
        if (dResult != new_date)
        {
            System.out.println("Error: Expected: " + new_date +  "Obtained: " + dResult
                    + " (" + reason + ")");
        }

        reason = "Testing endOfYear() with endOfYearDate()";
        Y1.endOfYear(new_date);
        dResult = Y1.getEndOfYear();
        if (dResult != new_date)
        {
            System.out.println("Error: Expected: " + new_date +  "Obtained: " + dResult
                    + " (" + reason + ")");
        }

        reason = "Testing toString()";
        String sResult = Y1.toString();
        String sExpected = """
                2013,
                 new year starting date = 2013-07-23,
                 crop = null,
                 seeding_date = 2013-09-05,
                 seeding_rate = 170.0lbs/acre,
                 fertilizer_rate = 55.0lbs/acre,
                 spraying_date = 2013-09-23,
                 harvest_date = 2013-09-23,
                 end_of_year = 2013-09-23,
                 chemical_records
                  null  2013-09-23

                 task_records
                  Harrowing  2013-09-23
                """;
        if (!sResult.equals(sExpected))
        {
            System.out.println("Error: Expected: \n" + sExpected + " Obtained: \n" + sResult
                    + " (" + reason + ")");
        }



        System.out.println("*** Testing Complete ***");
    }
}
