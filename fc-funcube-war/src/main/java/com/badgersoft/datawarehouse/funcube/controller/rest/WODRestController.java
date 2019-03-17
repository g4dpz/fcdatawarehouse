package com.badgersoft.datawarehouse.funcube.controller.rest;

import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.dao.WholeOrbitDataDAO;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.funcube.domain.WholeOrbitDataEntity;
import com.badgersoft.datawarehouse.funcube.dto.DataElement;
import com.badgersoft.datawarehouse.funcube.dto.WholeOrbitDataDTO;
import com.badgersoft.datawarehouse.funcube.dto.WodInstantDTO;
import com.badgersoft.datawarehouse.funcube.dto.WodJson;
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

        DataElement channel1 = new DataElement("Black Chassis");
        wodJson.addElement(channel1);
        DataElement channel2 = new DataElement("Silver Chassis");
        wodJson.addElement(channel2);
        DataElement channel3 = new DataElement("Black Panel");
        wodJson.addElement(channel3);
        DataElement channel4 = new DataElement("Silver Panel");
        wodJson.addElement(channel4);
        DataElement channel5 = new DataElement("Solar Panel +X");
        wodJson.addElement(channel5);
        DataElement channel6 = new DataElement("Solar Panel -X");
        wodJson.addElement(channel6);
        DataElement channel7 = new DataElement("Solar Panel +Y");
        wodJson.addElement(channel7);
        DataElement channel8 = new DataElement("Solar Panel -Y");
        wodJson.addElement(channel8);
        DataElement channel9 = new DataElement("Solar Panel Volts X");
        wodJson.addElement(channel9);
        DataElement channel10 = new DataElement("Solar Panel Volts Y");
        wodJson.addElement(channel10);
        DataElement channel11 = new DataElement("Solar Panel Volts Z");
        wodJson.addElement(channel11);
        DataElement channel12 = new DataElement("Tot. Photo Curr.");
        wodJson.addElement(channel12);
        DataElement channel13 = new DataElement("Battery Volts");
        wodJson.addElement(channel13);
        DataElement channel14 = new DataElement("Total Sys. Curr.");
        wodJson.addElement(channel14);


        long minute = -104;

        for (WholeOrbitDataEntity wodEntity : wholeOrbitDataEntities) {

            for (int i = 0; i < 14; i++) {
                switch (i) {
                    case 0:
                        channel1.addDatum(minute, wodEntity.getBlackChassis());
                        break;
                    case 1:
                        channel2.addDatum(minute,wodEntity.getSilverChassis());
                        break;
                    case 2:
                        channel3.addDatum(minute,wodEntity.getBlackPanel());
                        break;
                    case 3:
                        channel4.addDatum(minute,wodEntity.getSilverPanel());
                        break;
                    case 4:
                        channel5.addDatum(minute,wodEntity.getSolPanelXplus());
                        break;
                    case 5:
                        channel6.addDatum(minute,wodEntity.getSolPanelXminus());
                        break;
                    case 6:
                        channel7.addDatum(minute,wodEntity.getSolPanelYplus());
                        break;
                    case 7:
                        channel8.addDatum(minute,wodEntity.getSolPanelYminus());
                        break;
                    case 8:
                        channel9.addDatum(minute,wodEntity.getSolPanelXvolts());
                        break;
                    case 9:
                        channel10.addDatum(minute,wodEntity.getSolPanelYvolts());
                        break;
                    case 10:
                        channel11.addDatum(minute,wodEntity.getSolPanelZvolts());
                        break;
                    case 11:
                        channel12.addDatum(minute,wodEntity.getTotPhotoCurr());
                        break;
                    case 12:
                        channel13.addDatum(minute,wodEntity.getBatteryVolts());
                        break;
                    case 13:
                        channel14.addDatum(minute,wodEntity.getTotSystemCurr());
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
        final long wodFrom = lastWod - (104 * 60 * 1000);

        return wholeOrbitDataDAO.findAfterSatTime(new Timestamp(wodFrom));
    }
}
