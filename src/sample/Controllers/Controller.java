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
import java.nio.file.NoSuchFileException;
import java.util.*;

public class Controller {

    private static Properties prop = new Properties();
    public static LinkedList<Lesson> lessons = new LinkedList<Lesson>();
    public static HashSet<String> auditories = new HashSet<>();
    public static HashSet<String> teachers = new HashSet<>();


    static void initProps() throws IOException {
        prop.load(new FileInputStream("src/resources/kompas.properties"));
    }

    @FXML
    private Button btn;

   @FXML
 private TextField txt;
@FXML
private TableView<Lesson> tableView;
    void getAllLessons() throws NoSuchFileException {
        for (File excel : Objects.requireNonNull(new File("src/resources/lessons/").listFiles())) {
            XSSFWorkbook wb = ExcelReader.readWorkbook(excel);
            XSSFSheet sheet = wb.getSheetAt(0);

        }

    }

    @FXML
    void parseSheet(ActionEvent event) throws IOException {

        if(txt.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Введите именование группы!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else {
            initProps();
            String s = "";
            Integer[] reses = new Integer[2];
            reses = findGroup(txt.getText());
            if (reses[0] != null) {
                s += reses[1].toString();
                tableView.getItems().clear();
                ObservableList<Lesson> ls = tableView.getItems();
                String[]    days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};


                File[] excel = new File("src/resources/lessons/").listFiles();
                XSSFWorkbook wb = ExcelReader.readWorkbook(excel[reses[0]]);
                XSSFSheet sheet = wb.getSheetAt(0);

                for (int i = 0; i < 6; i++) {
                  Lesson les =new Lesson();
                  les.setDay(days[i]);


                  ls.add(les);
                    for (int j = 0; j < 6; j++) {

                        ls.add(getLesson(sheet, Integer.parseInt(String.valueOf(prop.get("ROW_PADDING"))) + Integer.parseInt(String.valueOf(prop.get("DAY_SHIFT"))) * i + Integer.parseInt(String.valueOf(prop.get("LESSON_SHIFT"))) * j, getGroupShift(reses[1]), j, 1,true,i));
                        ls.add(getLesson(sheet, Integer.parseInt(String.valueOf(prop.get("ROW_PADDING"))) + Integer.parseInt(String.valueOf(prop.get("DAY_SHIFT"))) * i + Integer.parseInt(String.valueOf(prop.get("LESSON_SHIFT"))) * j + 1, getGroupShift(reses[1]), j, 1,false,i));
                    }
                    Lesson empty = new Lesson();
                    ls.add(empty);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Введенная вами группа не существует!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });

            }
        }
    }


    void groupName() throws NoSuchFileException {
        File excel = new File("src/resources/lessons/IIT_3k_19_20_osen.xlsx");
        XSSFWorkbook wb = ExcelReader.readWorkbook(excel);
        XSSFSheet sheet = wb.getSheetAt(0);

    }

    public static String getGroupName(XSSFSheet sheet, int group_index) {
        XSSFRow row = sheet.getRow(1);
        return getCellString(row.getCell(getGroupShift(group_index)));
    }

    public static int getGroupShift(int group_index) {

        int result = Integer.parseInt(prop.get("GROUP_PADDING").toString());
        for (int i = 0; i < group_index; i++) {
            result += Integer.parseInt(prop.get("GROUP_SHIFT").toString().split(",")[i % 3]);
        }
        return result;
    }


    void nullCell() throws NoSuchFileException {
        File excel = new File("src/resources/IIT_1k_19_20_osen.xlsx");
        XSSFWorkbook wb = ExcelReader.readWorkbook(excel);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row_t = sheet.getRow(43);
        System.out.println("'" + getCellString(row_t.getCell(24)) + "'");
    }

    static String getCellString(XSSFCell cell) {
        try {
            return cell.getStringCellValue();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static Lesson getLesson(XSSFSheet sheet, int row, int cell, int lesson_number, int group_index,boolean flag, int day) {
        XSSFRow row_t = sheet.getRow(row);
        Lesson lesson = new Lesson();
        try {
            lesson.setSubject(getCellString(row_t.getCell(cell)));
            lesson.setType(getCellString(row_t.getCell(cell + 1)));
            lesson.setTeacher(getCellString(row_t.getCell(cell + 2)));
            //teachers.add(lesson.getTeacher());
            lesson.setAuditory(getCellString(row_t.getCell(cell + 3)));
         //   auditories.add(lesson.getAuditory());
            lesson.setLessonNumber(String.valueOf(lesson_number+1));
            lesson.setDay("");
            if(flag==true)
            {
                lesson.setWeek("I");
            }
            else  lesson.setWeek("II");
        } catch (IllegalStateException e) {
            System.out.println("Cannot read cells at " + getGroupName(sheet, group_index) + " " + row + " " + cell);
            e.printStackTrace();
            throw e;
        }
        if (!lesson.getSubject().equals("")) {
            lessons.add(lesson);

            return lesson;
        } else return null;

    }

    Integer[] findGroup(String s) throws NoSuchFileException {
        File[] excel = new File("src/resources/lessons/").listFiles();
        Integer[] res = new Integer[2];
        XSSFRow row;
        boolean flag =false;
        for (int i=0;i<excel.length;i++) {
            XSSFWorkbook wb = ExcelReader.readWorkbook(excel[i]);
            XSSFSheet sheet = wb.getSheetAt(0);

int k=0;
int groupnum=0;

while(groupnum<sheet.getRow(1).getLastCellNum())
{
    groupnum=getGroupShift(k);
    if(getCellString(sheet.getRow(1).getCell(groupnum)).equals(s))
    {
        res[0] = i;
        res[1]=k;
        flag=true;
        break;
    }
    k++;
}
      if(flag==true)
      {
          break;
      }
        }
return res;
    }

}
