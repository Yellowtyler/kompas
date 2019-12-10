package sample.Controllers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.Model.ExcelReader;
import sample.Model.Lesson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
public class Teach {

    public static LinkedList<Lesson> lessons = new LinkedList<>();

    @FXML
    TextField textField;
    @FXML
         TableView<Lesson> tableView;
    ObservableList<Lesson>  teacherSchedule;

    @FXML
    void GetTeacher(ActionEvent actionEvent) throws IOException {

        getAllLessons();





    }


    private static Properties prop = new Properties();
    static void initProps() throws IOException {
        prop.load(new FileInputStream("src/resources/kompas.properties"));
    }


    void getAllLessons() throws IOException {
        if(textField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
           alert.setHeaderText("Данные не введены");
            alert.setContentText("Введите ФИО!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });


        }
        else {
            initProps();
            tableView.getItems().clear();
            teacherSchedule = tableView.getItems();
            for (File excel : Objects.requireNonNull(new File("src/resources/lessons/").listFiles())) {
                XSSFWorkbook wb = ExcelReader.readWorkbook(excel);
                XSSFSheet sheet = wb.getSheetAt(0);
                parseSheet(sheet);
            }
            if (teacherSchedule.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Введенные ФИО не существуют!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });

            } else {
                Collections.sort(teacherSchedule, new Lesson.SortByDay());
            }
        }
    }

   void parseSheet(XSSFSheet sheet){
        for(int group_index = 0; !getGroupName(sheet, group_index).isEmpty(); group_index++){
            for(int i = 0; i < Integer.parseInt(String.valueOf(prop.get("DAYS"))); i++){
                for(int j = 0; j < Integer.parseInt(String.valueOf(prop.get("LESSONS"))); j++) {
                    getLesson(sheet,Integer.parseInt(String.valueOf(prop.get("ROW_PADDING")))
                            + Integer.parseInt(String.valueOf(prop.get("DAY_SHIFT"))) * i
                            + Integer.parseInt(String.valueOf(prop.get("LESSON_SHIFT"))) * j, getGroupShift(group_index), j, group_index,true,i);
                    getLesson(sheet,Integer.parseInt(String.valueOf(prop.get("ROW_PADDING")))
                            + Integer.parseInt(String.valueOf(prop.get("DAY_SHIFT"))) * i
                            + Integer.parseInt(String.valueOf(prop.get("LESSON_SHIFT"))) * j + 1, getGroupShift(group_index), j, group_index,false,i);
                }
            }
        }
    }
    public  void getLesson(XSSFSheet sheet, int row, int cell, int lesson_number, int group_index,boolean flag, int day){
        XSSFRow row_t = sheet.getRow(row);

        String[]    days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        try {


        if(getCellString(row_t.getCell(cell + 2)).equals(textField.getText()))
        {
            Lesson lesson = new Lesson();
            lesson.setSubject(getCellString(row_t.getCell(cell)));
            lesson.setType(getCellString(row_t.getCell(cell + 1)));
            lesson.setTeacher(getCellString(row_t.getCell(cell + 2)));
            lesson.setAuditory(getCellString(row_t.getCell(cell + 3)));
            lesson.setLessonNumber(String.valueOf(lesson_number+1));
            lesson.setDay(days[day]);
            if(flag==true)
            {
                lesson.setWeek("I");
            }
            else  lesson.setWeek("II");
            if(checkSame(lesson)&&checkNums(lesson))
            {
                teacherSchedule.add(lesson);

            }
        }
        }
        catch (IllegalStateException e){
            System.out.println("Cannot read cells at " + getGroupName(sheet, group_index) + " " + row + " " + cell);
            e.printStackTrace();
            throw e;
        }

    }

    private  boolean checkSame(Lesson lesson) {

        for(Lesson ls : teacherSchedule)
        {
            if(ls.equals(lesson))
            {
                return false;
            }


        }
        return true;

    }
    private  boolean checkNums(Lesson lesson)
    {
        String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
        for(String num: nums)
        {
            if(lesson.getSubject().contains(num))
            {
                return false;
            }

        }
        return true;
    }

    public static String getGroupName(XSSFSheet sheet, int group_index){
        XSSFRow row = sheet.getRow(1);
        return getCellString(row.getCell(getGroupShift(group_index)));
    }
    static String getCellString(XSSFCell cell){
        try { return cell.getStringCellValue(); }
        catch (NullPointerException e){ return ""; }
    }

    public static int getGroupShift(int group_index){
        int result = Integer.parseInt(prop.get("GROUP_PADDING").toString());
        for(int i = 0; i < group_index; i++){
            result += Integer.parseInt(prop.get("GROUP_SHIFT").toString().split(",")[i % 3]);
        }
        return result;
    }






}
