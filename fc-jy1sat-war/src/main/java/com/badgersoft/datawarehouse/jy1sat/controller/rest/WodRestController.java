package com.badgersoft.datawarehouse.jy1sat.controller.rest;

import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.dao.WholeOrbitDataDAO;
import com.badgersoft.datawarehouse.jy1sat.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.jy1sat.domain.WholeOrbitDataEntity;
import com.badgersoft.datawarehouse.jy1sat.dto.DataElement;
import com.badgersoft.datawarehouse.jy1sat.dto.WholeOrbitDataDTO;
import com.badgersoft.datawarehouse.jy1sat.dto.WodInstantDTO;
import com.badgersoft.datawarehouse.jy1sat.dto.WodJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davidjohnson on 30/10/2016.
 */
@RestController
@RequestMapping(value = "/data")
public class WodRestController {

    @Autowired
    WholeOrbitDataDAO wholeOrbitDataDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @RequestMapping(value = "/wod", method = RequestMethod.GET, produces = "application/json")
    public String getSummary(@RequestParam(value = "callback") String callback)
    {
        List<WholeOrbitDataEntity> wholeOrbitDataEntities = getLastOrbitData();

        if (wholeOrbitDataEntities == null || wholeOrbitDataEntities.isEmpty()) {
            return callback + "([error:" + "No data found" + "]);";
        }

        List<WodInstantDTO> wodMinutes = new ArrayList<WodInstantDTO>();

        for (WholeOrbitDataEntity wholeOrbitDataEntity : wholeOrbitDataEntities ) {
            wodMinutes.add(new WodInstantDTO(wholeOrbitDataEntity));
        }

        WholeOrbitDataDTO wholeOrbitDataDTO = new WholeOrbitDataDTO(wodMinutes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("data", wholeOrbitDataDTO);

        try {
            return objectMapper.writeValueAsString(new JSONPObject(callback, map));
        }
        catch (Exception e) {
            return callback + "([error:" + e.getMessage() + "]);";
        }

    }

    @RequestMapping(value = "/wod/graph", method = RequestMethod.GET, produces = "application/json")
    public String getGraphData() {

        List<WholeOrbitDataEntity> wholeOrbitDataEntities = getLastOrbitData();

        if (wholeOrbitDataEntities == null || wholeOrbitDataEntities.isEmpty()) {
            return "([error:" + "No data found" + "]);";
        }

        WodJson wodJson = new WodJson();

        DataElement channel1 = new DataElement("CCT Micro. Temp");
        wodJson.addElement(channel1);
        DataElement channel2 = new DataElement("RF Board Xtal Temp");
        wodJson.addElement(channel2);
        DataElement channel3 = new DataElement("Power Amp. Temp");
        wodJson.addElement(channel3);
        DataElement channel4 = new DataElement("Solar Panel +X");
        wodJson.addElement(channel4);
        DataElement channel5 = new DataElement("Solar Panel -X");
        wodJson.addElement(channel5);
        DataElement channel6 = new DataElement("Solar Panel +Y");
        wodJson.addElement(channel6);
        DataElement channel7 = new DataElement("Solar Panel -Y");
        wodJson.addElement(channel7);
        DataElement channel8 = new DataElement("Solar Panel +Z");
        wodJson.addElement(channel8);
        DataElement channel9 = new DataElement("Solar Panel -Z");
        wodJson.addElement(channel9);
        DataElement channel10 = new DataElement("Sun Sensor +X");
        wodJson.addElement(channel10);
        DataElement channel11 = new DataElement("Sun Sensor -X");
        wodJson.addElement(channel11);
        DataElement channel12 = new DataElement("Sun Sensor +Y");
        wodJson.addElement(channel12);
        DataElement channel13 = new DataElement("Sun Sensor -Y");
        wodJson.addElement(channel13);
        DataElement channel14 = new DataElement("Sun Sensor +Z");
        wodJson.addElement(channel14);
        DataElement channel15 = new DataElement("Sun Sensor -Z");
        wodJson.addElement(channel15);
        DataElement channel16 = new DataElement("Battery Temp");
        wodJson.addElement(channel16);
        DataElement channel17 = new DataElement("Tot. Photo Curr.");
        wodJson.addElement(channel17);
        DataElement channel18 = new DataElement("Battery Volts");
        channel18.setVisible(true);
        wodJson.addElement(channel18);
        DataElement channel19 = new DataElement("Total Sys. Curr");
        wodJson.addElement(channel19);

        double[] solTempOffsets = getSolTempOffsets(wholeOrbitDataEntities);

        long minute = -96;

        for (WholeOrbitDataEntity wodEntity : wholeOrbitDataEntities) {

            for (int i = 0; i < 19; i++) {
                switch (i) {
                    case 0:
                        channel1.addDatum(minute, wodEntity.getC1());
                        break;
                    case 1:
                        channel2.addDatum(minute,wodEntity.getC2());
                        break;
                    case 2:
                        channel3.addDatum(minute,wodEntity.getC3());
                        break;
                    case 3:
                        channel4.addDatum(minute,wodEntity.getC4() + solTempOffsets[0]);
                        break;
                    case 4:
                        channel5.addDatum(minute,wodEntity.getC5() + solTempOffsets[1]);
                        break;
                    case 5:
                        channel6.addDatum(minute,wodEntity.getC6() + solTempOffsets[2]);
                        break;
                    case 6:
                        channel7.addDatum(minute,wodEntity.getC7() + solTempOffsets[3]);
                        break;
                    case 7:
                        channel8.addDatum(minute,wodEntity.getC8() + solTempOffsets[4]);
                        break;
                    case 8:
                        channel9.addDatum(minute,wodEntity.getC9() + solTempOffsets[5]);
                        break;
                    case 9:
                        channel10.addDatum(minute, (double) wodEntity.getC10());
                        break;
                    case 10:
                        channel11.addDatum(minute, (double) wodEntity.getC11());
                        break;
                    case 11:
                        channel12.addDatum(minute, (double) wodEntity.getC12());
                        break;
                    case 12:
                        channel13.addDatum(minute, (double) wodEntity.getC13());
                        break;
                    case 13:
                        channel14.addDatum(minute, (double) wodEntity.getC14());
                        break;
                    case 14:
                        channel15.addDatum(minute, (double) wodEntity.getC15());
                        break;
                    case 15:
                        channel16.addDatum(minute, (double) wodEntity.getC16());
                        break;
                    case 16:
                        channel17.addDatum(minute, (double) wodEntity.getC17());
                        break;
                    case 17:
                        channel18.addDatum(minute, ((double) (wodEntity.getC18()) / 1000.0));
                        break;
                    case 18:
                        channel19.addDatum(minute, (double) wodEntity.getC19());
                        break;
                }
            }

            minute++;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("data", wodJson);


        try {
            return objectMapper.writeValueAsString(map);
        }
        catch (Exception e) {
            return "([error:" + e.getMessage() + "]);";
        }
    }

    private List<WholeOrbitDataEntity> getLastOrbitData() {
        SatelliteStatusEntity satelliteStatusEntity
                = (SatelliteStatusEntity)(satelliteStatusDao.findAll().iterator().next());

        final long lastWod = satelliteStatusEntity.getLastWodTime().getTime();
        final long wodFrom = lastWod - (96 * 60 * 1000);

        return wholeOrbitDataDAO.findAfterSatTime(new Timestamp(wodFrom));
    }

    private double[] getSolTempOffsets(List<WholeOrbitDataEntity> wholeOrbitDataEntities) {

        double[] calcOffset = new double[6];
        double total = 0.0;

        int count = 0;

        for (WholeOrbitDataEntity wodEntity : wholeOrbitDataEntities) {
            double c4 = wodEntity.getC4();
            calcOffset[0] += c4;
            total += c4;
            double c5 = wodEntity.getC5();
            calcOffset[1] += c5;
            total += c5;
            double c6 = wodEntity.getC6();
            calcOffset[2] += c6;
            total += c6;
            double c7 = wodEntity.getC7();
            calcOffset[3] += c7;
            total += c7;
            double c8 = wodEntity.getC8();
            calcOffset[4] += c8;
            total += c8;
            double c9 = wodEntity.getC9();
            calcOffset[5] += c9;
            total += c9;
            count++;
        }

        total /= (count * 6);

        for (int i = 0; i < 6; i++) {
            calcOffset[i] /= count;
            calcOffset[i] = (total - calcOffset[i]);
        }

        return calcOffset;

    }
}
