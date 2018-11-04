package com.badgersoft.datawarehouse.eseo.domain;

import javax.persistence.Transient;

/**
 * Created by davidjohnson on 30/10/2016.
 */
abstract class TelemetryEntity {

    protected static double[] SOL_ILLUMINATION = new double[1024];
    protected static double[] PA_TEMPS_256 = new double[256];
    protected static double[] PA_TEMPS_1024 = new double[1024];

    @Transient
    protected int stringPos = 0;

    @Transient
    protected long getBitsAsULong(int length, String binaryString) {

        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + length), 2);
        stringPos += length;
        return value;
    }

    @Transient
    protected boolean getBooleanBit(String binaryString) {
        final boolean value = (binaryString.substring(stringPos, stringPos + 1).equals("1"));
        stringPos += 1;
        return value;
    }

    @Transient
    protected long twosComplement(long value) {
        long channelValue = value;
        if (channelValue >= 128) {
            channelValue = ~channelValue ^ 255;
        }
        return channelValue;
    }

    @Transient
    protected static void setupSunSensors() {

        final double[][] tempToAdc = new double[][] {{5, 0}, {9, 2.158},
                {19, 2.477}, {23, 2.64}, {33, 2.8}, {50, 2.881},
                {56, 2.889}, {100, 3.04}, {133, 3.14}, {200, 3.25},
                {265, 3.346}, {333, 3.475}, {400, 3.58}, {467, 3.69},
                {533, 3.81}, {600, 3.92}, {666, 4.03}, {700, 4.079},
                {732, 4.13}, {800, 4.245}, {866, 4.325}, {933, 4.38},
                {984, 4.42}, {992, 4.5319}, {999, 4.6438},
                {1007, 4.7557}, {1015, 4.8676}, {1023, 4.9795}};

        for (int i = 0; i < 1024; ++i) {
            if (i < 984) {
                // calc values for all possible 8bit values
                for (int j = 0; j < tempToAdc.length; j++) {
                    if (i < tempToAdc[j][0]) {
                        // get this current value
                        final double[] currentPair = tempToAdc[j];
                        // get the previous value
                        double[] previousPair = new double[] {0, 0};
                        if (j != 0) {
                            previousPair = tempToAdc[j - 1];
                        }
                        // get the adc difference
                        final double adcDiff = currentPair[0] - previousPair[0];
                        // get the value difference
                        final double valueDiff = currentPair[1] - previousPair[1];
                        // scale per unit
                        final double increment = valueDiff / adcDiff;
                        // calculate the sol value for this adc value
                        final double value = previousPair[1] + (i - previousPair[0])
                                * increment;
                        // save it in the array
                        SOL_ILLUMINATION[i] = value;
                        // break;
                        break;
                    }
                }
            }
            else {
                SOL_ILLUMINATION[i] = 4.420;
            }
        }
    }

    @Transient
    static protected double[] buildLookupTable(int tableSize)
    {
        // Data from adc 30% to 98% measured, 0 to 30% continues using
        // gradient of last three values, 98 to 100% likewise
           double[][] tempToAdc = {{87.983, Double.MIN_VALUE}, {87.983, 0},
            { 55.3,  30.859}, /*first measured value*/
            { 49.6,  35.547},
            { 45.3,  40.234},
            { 41.1,  44.922},
            { 37.6,  48.828},
            { 35.7,  50.391},
            { 33.6,  53.516},
            { 30.6,  56.641},
            { 27.6,  60.156},
            { 25.1,  62.891},
            { 22.6,  66.016},
            {   20,  68.75},
            { 17.6,  71.484},
            { 15.1,  73.828},
            { 12.6,  76.563},
            {   10,  79.297},
            {  7.5,  81.25},
            {    5,  83.594},
            {  2.4,  85.938},
            {    0,  87.5},
            { -2.9,  89.844},
            {   -5,  91.016},
            { -7.5,  92.578},
            {  -10,  94.141},
            {-12.3,  95.313},
            {  -15,  96.484},
            {  -20,  98.438}, /*last measured value*/
            {-22.846,100 }, {-22.846,Double.MAX_VALUE}};

        double[] result = new double[tableSize];
        // calc values for all possible 12bit values
        for (int adc = 0; adc < tableSize; ++adc)
        {
            double adc_percent = (100.0 / (double)tableSize) * adc;
            for (int j = 0; j < tempToAdc.length; j++)
            {
                if (adc_percent < tempToAdc[j][1] && j != 0)
                {
                    double t1 = tempToAdc[j][0];
                    double a1 = tempToAdc[j][1];
                    double diffa = tempToAdc[j - 1][1] - a1;
                    double difft = tempToAdc[j - 1][0] - t1;
                    result[adc] = ((adc_percent - a1) * (difft / diffa))
                            + t1;
                    break;
                }
            }
        }
        return result;
    }

    @Transient
    protected double scale(final Long adc, final Double multiplier, final Double offset) {
        return (adc * multiplier) + offset;
    }
}
