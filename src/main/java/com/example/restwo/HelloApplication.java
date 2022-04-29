package com.example.restwo;

import com.example.restwo.controller.Controller;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@ApplicationPath("/api")
public class HelloApplication extends Application {


    public static File file = new File(System.getProperty("user.home") +
                        File.separator + "Downloads" + File.separator + "ASD123");


    static {
        try {

            String line = "\n\n---------------------------------------\n\n";
            System.out.println(line + "Fetching..." + line);

            LocalDate date = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();
            int days = Period.between(date, LocalDate.now()).getDays();

            if( !file.exists() || days > 7 || true) {
                Controller.start();
            }

            System.out.println(line + "Done..." + line);

        } catch (Exception e) {
            System.out.print("");
        }
    }



}

