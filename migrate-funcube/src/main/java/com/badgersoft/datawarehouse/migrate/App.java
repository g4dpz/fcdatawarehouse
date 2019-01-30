package com.badgersoft.datawarehouse.migrate;

import com.badgersoft.datawarehouse.migrate.dao.HexFrameDao;
import com.badgersoft.datawarehouse.migrate.domain.HexFrameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private HexFrameDao hexFrameDao;


    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .run(args);
    }

    @Override
    public void run(String... arg0) {
        List<HexFrameEntity> hexFrameEntities = new ArrayList<>();
        Pageable pageable;
        int count = 0;
        do {
            hexFrameEntities = hexFrameDao.findByIdBetweenAndSatelliteIdQuery(count, count+999, 2);
            count+=1000;
            System.out.println(String.format("Count: %d, size: %d", count, hexFrameEntities.size()));
        } while (count < 10e6) ;
    }
}