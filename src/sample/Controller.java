package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.application.Platform;
import javafx.event.EventHandler;

public class Controller {
    @FXML
    private CheckBox short_check;
    @FXML
    private CheckBox up_check;
    @FXML
    private Label time_count;
    @FXML
    private Label main_label;

    @FXML
    public void initialize() {

        new JThread("JThread",time_count,main_label,short_check,up_check).start();
    }
}

class JThread extends Thread {

    @FXML
    private CheckBox short_check;
    @FXML
    private CheckBox up_check;
    @FXML
    private Label time_count;
    @FXML
    private Label main_label;

    private int end_time;
    private int find_hour;
    private int find_minute;
    private int find_second;
    private String time_str;
    private String day;
    JThread(String name,Label time_count,Label main_label, CheckBox short_check,  CheckBox up_check){
        super(name);
        this.main_label=main_label;
        this.time_count=time_count;
        this.short_check=short_check;
        this.up_check=up_check;
        day = LocalDate.now().getDayOfWeek().name();
        if (day == "FRIDAY")
        {
            end_time=59400; //16(hours)*60(minutes)*60(seconds)+30(minutes)*60(seconds)
        }
        else
        {
            end_time=63000;//17(hours)*60(minutes)*60(seconds)+30(minutes)*60(seconds)
        }
        short_check.setOnAction(event);
        up_check.setOnAction(event1);

    }


    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent e)
        {
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();
            int curr_time=hour*3600+minute*60+second;
            day = LocalDate.now().getDayOfWeek().name();
            if ((short_check.isSelected()) &&(day != "FRIDAY"))
                end_time=59400;
            else if ((!short_check.isSelected()) &&(day != "FRIDAY")) {
                end_time=63000;
                if (curr_time>59400) {
                    new JThread("JThread",time_count,main_label,short_check,up_check).start();
                    Thread.currentThread().interrupt();
                }
            }
        }

    };

    EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent e)
        {
            Stage stage = (Stage) up_check.getScene().getWindow();
            if (up_check.isSelected())
                stage.setAlwaysOnTop(true);
            else
                stage.setAlwaysOnTop(false);
        }

    };



    public void run(){
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int curr_time=hour*3600+minute*60+second;
        while((curr_time<end_time)&&(!Thread.currentThread().isInterrupted()))
        {
            final int find_time = end_time-curr_time;
            find_hour = find_time/3600;
            find_minute = find_time%3600/60;
            find_second = find_time%3600%60;
            time_str = "";
            if (find_hour<10)
                {
                    time_str+="0"+find_hour+":";
                }
            else
                {
                    time_str+=find_hour+":";
                }
            if (find_minute<10)
                {
                    time_str+="0"+find_minute+":";
                }
            else
                {
                    time_str+=find_minute+":";
                }
            if (find_second<10)
                {
                    time_str+="0"+find_second+"";
                }
            else
                {
                    time_str+=find_second+"";
                }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    main_label.setText("До конца рабочего дня осталось:");
                    time_count.setText(time_str);
                }
            });
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            now = LocalDateTime.now();
            hour = now.getHour();
            minute = now.getMinute();
            second = now.getSecond();
            curr_time=hour*3600+minute*60+second;
        }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    main_label.setText("Рабочее время закончилось.");
                    time_count.setText("Можно идти домой!!!");
                }
            });
}
}