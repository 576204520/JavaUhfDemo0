package com.uhf.structures;

public class AntennaPortParams
{
    public int antennaPort;
    public int antennaStatus;
    public int powerLevel;
    public int dwellTime;
    public int numberInventoryCycles;

    public void setAntennaPort(int antennaPort)
    {
        this.antennaPort = antennaPort;
    }

    public void setAntennaStatus(int antennaStatus)
    {
        this.antennaStatus = antennaStatus;
    }

    public void setPowerLevel(int powerLevel)
    {
        this.powerLevel = powerLevel;
    }

    public void setDwellTime(int dwellTime)
    {
        this.dwellTime = dwellTime;
    }

    public void setNumberInventoryCycles(int numberInventoryCycles)
    {
        this.numberInventoryCycles = numberInventoryCycles;
    }

    public int getAntennaPort()
    {
        return antennaPort;
    }

    public int getAntennaStatus()
    {
        return antennaStatus;
    }

    public int getPowerLevel()
    {
        return powerLevel;
    }

    public int getDwellTime()
    {
        return dwellTime;
    }

    public int getNumberInventoryCycles()
    {
        return numberInventoryCycles;
    }

    public void setValue(int antennaPort, int antennaStatus, int powerLevel, 
    		             int dwellTime, int numberInventoryCycles)
    {
        this.antennaPort = antennaPort;
        this.antennaStatus = antennaStatus;
        this.powerLevel = powerLevel;
        this.dwellTime = dwellTime;
        this.numberInventoryCycles = numberInventoryCycles;
    }

    @Override
    public String toString()
    {
        return "AntennaPortParams{" +
                "antennaPort=" + antennaPort +
                ", antennaStatus=" + antennaStatus +
                ", powerLevel=" + powerLevel +
                ", dwellTime=" + dwellTime +
                ", numberInventoryCycles=" + numberInventoryCycles +
                '}';
    }
}
