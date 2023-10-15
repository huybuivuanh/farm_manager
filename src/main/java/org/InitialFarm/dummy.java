package org.InitialFarm;

public class dummy {

    String dummyId;
    int dummyHeight ;
    String dummyName;

    public dummy( int dH1, String dN, String dId){
        dummyHeight= dH1;
        dummyName = dN;
        dummyId=dId;

    }


    public int getDummyHeight() {
        return dummyHeight;
    }

    public void setDummyHeight(int h) {
        dummyHeight = h ;
    }


    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String n) {
        dummyName= n ;
    }

    @Override
    public String toString() {
        return "dummy{" +
                "dummyId='" + dummyId + '\'' +
                ", dummyHeight=" + dummyHeight +
                ", dummyName='" + dummyName + '\'' +
                '}';
    }

    public static void main(String[] args){



    }
}
