package entities;

import java.util.Date;
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
    private Date seeding_date;

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
    private Date spraying_date;

    /**
     * The class to hold a chemical and date record
     */
    private static class Chemical_record{
        Chemical chemical;
        Date date;

        private Chemical_record(Chemical _chemical, Date _date){
            chemical = _chemical;
            date = _date;
        }
        private Chemical getChemical(){ return this.chemical; }
        private Date getDate(){ return this.date; }
    }

    /**
     * The list of chemicals that have been sprayed on the
     * field over the year and their dates
     */
    private final LinkedList<Chemical_record> chemical_records;

    /**
     * The class to hold a chemical and date record
     */
    private static class Task_record{
        Task task;
        Date date;

        private Task_record(Task _task, Date _date){
            task = _task;
            date = _date;
        }
        private Task getTask(){ return this.task; }
        private Date getDate(){ return this.date; }
    }

    /**
     * The list of tasks that have been done over the year and
     * their dates
     */
    private final LinkedList<Task_record> task_records;

    /**
     * The date the field was harvested
     */
    private Date harvest_date;

    /**
     * The date the field year ended
     */
    private Date end_of_year;

    /**
     * The date the field year begun
     */
    private final Date new_year;


    /**
     * Initializes a year
     * @param _year the current year
     */
    public Year( int _year, Date new_year_date){
        year = _year;
        this.new_year = new_year_date;
        this.chemical_records = new LinkedList<Chemical_record>();
        this.task_records = new LinkedList<Task_record>();
    }

    /**
     * Return the current year
     * @return the current year as an int
     */
    public int getYear() {
        return this.year;
    }

    public Date getNewYearDate() {
        return this.new_year;
    }

    /**
     * The crop being grown that year
     * @return the crop grown that year
     */
    public Crop getCrop() {
        return this.crop;
    }

    public Date getSeeding_date(){
        return this.seeding_date;
    }

    public void setSeeding_date( Date newSeedingDate){
        this.seeding_date = newSeedingDate;
    }

    public double getSeeding_rate(){
        return this.seeding_rate;
    }

    public void setSeeding_rate(double newSeedingRate){
        this.seeding_rate = newSeedingRate;
    }

    public double getFertilizer_rate(){
        return this.fertilizer_rate;
    }

    public void setFertilizer_rate(double newFertilizerRate){
        this.fertilizer_rate = newFertilizerRate;
    }

    public Date getSpraying_date(){
        return this.spraying_date;
    }

    public Chemical getChemical_sprayed(){
        return this.chemical_sprayed;
    }

    public void sprayChemical(Chemical newChemical, Date newSprayingDate){
        this.chemical_sprayed = newChemical;
        this.spraying_date = newSprayingDate;
        Chemical_record newRecord = new Chemical_record(newChemical, newSprayingDate);
        this.chemical_records.add(newRecord);

    }

    public void doTask(Task newTask, Date newTaskDate){
        Task_record newRecord = new Task_record(newTask, newTaskDate);
        this.task_records.add(newRecord);
    }

    public void harvest( Date harvestDate){
        this.harvest_date = harvestDate;
    }

    public Date getHarvest_date() {
        return harvest_date;
    }

    public Date getEnd_of_year(){
        return this.end_of_year;
    }

    public void endOfYear( Date endOfYearDate){
        this.end_of_year = endOfYearDate;
    }

    @Override
    public String toString() {
        return "Year{ " +
                "year = " + year +
                ",\n new year starting date = " + new_year +
                ",\n crop = " + crop +
                ",\n seeding_date = " + seeding_date +
                ",\n seeding_rate = " + seeding_rate +
                ",\n fertilizer_rate = " + fertilizer_rate +
                ",\n spraying_date = " + spraying_date +
                ",\n chemical_records = " + chemical_records +
                ",\n task_records = " + task_records +
                ",\n harvest_date = " + harvest_date +
                ",\n end_of_year = " + end_of_year +
                '}';
    }
}
